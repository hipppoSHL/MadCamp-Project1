package com.example.q.myapplication4;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import java.util.ArrayList;



public class Fragment2 extends Fragment {
    private Context mContext;

    public Fragment2() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getContext();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.ImgGridView);
        final ImageAdapter imageAdapter = new ImageAdapter(getContext());
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView parent, View v, int position, long id){
                imageAdapter.callImageViewer(position);
            }
        });

        return view;
    }

    public class ImageAdapter extends BaseAdapter {
        private String imgData;
        private String geoData;
        private ArrayList<String> thumbsDataList;
        private ArrayList<String> thumbsIDList;
        private ArrayList<Photo> photoList;

        ImageAdapter(Context c) {
            mContext = c;
            thumbsDataList = new ArrayList<String>();
            thumbsIDList = new ArrayList<String>();
            photoList = new ArrayList<Photo>();
            photoList = fetchAllImages();
            //getThumbInfo(thumbsIDList, thumbsDataList);
        }

        public final void callImageViewer(int selectedIndex) {
            //Intent i = new Intent(mContext, ImagePopup.class);
            String thumbPath = photoList.get(selectedIndex).getThumbnailURI();
            String imgPath = photoList.get(selectedIndex).getImageURI();
            //i.putExtra("filename", imgPath);
            //startActivityForResult(i, 1);
        }

        public boolean deleteSelected(int sIndex) {
            return true;
        }

        public int getCount() {
            return photoList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            RecyclerView recyclerView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                    ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            BitmapFactory.Options bo = new BitmapFactory.Options();
            bo.inSampleSize = 8;
            Bitmap bmp = BitmapFactory.decodeFile(photoList.get(position).getImageURI(), bo);
            Bitmap resized = Bitmap.createScaledBitmap(bmp, 320, 240, true);
            imageView.setImageBitmap(resized);

            return imageView;
        }
    }

    String uriToThumbnail(String imageId) throws  Exception{
        // DATA는 이미지 파일의 스트림 데이터 경로를 나타냅니다.
        String[] projection = { MediaStore.Images.Thumbnails.DATA };
        ContentResolver contentResolver = getContext().getContentResolver();

        // 원본 이미지의 _ID가 매개변수 imageId인 썸네일을 출력
        Cursor thumbnailCursor = contentResolver.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, // 썸네일 컨텐트 테이블
                projection, // DATA를 출력
                MediaStore.Images.Thumbnails.IMAGE_ID + "=?", // IMAGE_ID는 원본 이미지의 _ID를 나타냅니다.
                new String[]{imageId},
                null);
        if (thumbnailCursor == null) {
            // Error 발생
            // 적절하게 handling 해주세요
            throw new Exception();
        } else if (thumbnailCursor.moveToFirst()) {
            int thumbnailColumnIndex = thumbnailCursor.getColumnIndex(projection[0]);

            String thumbnailPath = thumbnailCursor.getString(thumbnailColumnIndex);
            thumbnailCursor.close();
            return thumbnailPath;
        } else {
            // thumbnailCursor가 비었습니다.
            // 이는 이미지 파일이 있더라도 썸네일이 존재하지 않을 수 있기 때문입니다.
            // 보통 이미지가 생성된 지 얼마 되지 않았을 때 그렇습니다.
            // 썸네일이 존재하지 않을 때에는 아래와 같이 썸네일을 생성하도록 요청합니다
            MediaStore.Images.Thumbnails.getThumbnail(contentResolver, Long.parseLong(imageId), MediaStore.Images.Thumbnails.MINI_KIND, null);
            thumbnailCursor.close();
            return uriToThumbnail(imageId);
        }
    }

    ArrayList<Photo> fetchAllImages() {

        // DATA는 이미지 파일의 스트림 데이터 경로를 나타냅니다.
        String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };

        Cursor imageCursor = getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 이미지 컨텐트 테이블
                projection, // DATA, _ID를 출력
                null,       // 모든 개체 출력
                null,
                null);      // 정렬 안 함

        ArrayList<Photo> result = new ArrayList<>(imageCursor.getCount());
        int dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
        int idColumnIndex = imageCursor.getColumnIndex(projection[1]);

        if (imageCursor == null) {
            // Error 발생
            // 적절하게 handling 해주세요
        } else if (imageCursor.moveToFirst()) {
            do {
                String filePath = imageCursor.getString(dataColumnIndex);
                String imageId = imageCursor.getString(idColumnIndex);
                try {
                    String thumbnailUri = uriToThumbnail(imageId);

                    // 원본 이미지와 썸네일 이미지의 uri를 모두 담을 수 있는 클래스를 선언합니다.
                    Photo photo = new Photo(filePath, thumbnailUri);
                    result.add(photo);
                }catch(Exception e){
                    System.err.println("exception ocuured");
                }
            } while(imageCursor.moveToNext());
        }
        imageCursor.close();
        return result;
    }
}