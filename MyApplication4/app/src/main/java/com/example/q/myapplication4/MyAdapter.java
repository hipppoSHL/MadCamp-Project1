package com.example.q.myapplication4;
// Fragment2의 갤러리 리스트(RecycleView) 어댑터

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<CreateList> galleryList;
    private Context context;
    private ArrayList<Photo> photoList;

    public MyAdapter(Context context, ArrayList<CreateList> galleryList, ArrayList<Photo> photoList) {
        this.galleryList = galleryList;
        this.photoList = photoList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(photoList.get(i).getThumbnailURI(), bmOptions);

        //viewHolder.

        //viewHolder.title.setText(galleryList.get(i).getImage_title());
        //viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource((photoList.get(i).getThumbnailURI()));
        //Picasso.with(context).load(galleryList.get(i).getImage_ID()).resize(240, 120).into(viewHolder.img);
        viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Image",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //private TextView title;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);

            //title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}