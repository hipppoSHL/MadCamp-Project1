package com.example.q.myapplication4;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Fragment1 extends Fragment {


    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;
    private Bitmap bp;

    public Fragment1() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        listView = (ListView) view.findViewById(R.id.listView);

        contactModelArrayList = new ArrayList<>();

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            /* ContactModel 수정함 */ String imageUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            // 프로필 사진 있으면 프로필사진대로, 없으면 디폴트 사진 로드하게 한다
            // 비트맵으로 했어요
            if (imageUri != null) {
                try {
                    bp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            else {
                bp = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.user);
            }

            ContactModel contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModel.setImage(bp);
            contactModelArrayList.add(contactModel);
            Log.d("name>>",name+"  "+phoneNumber);
        }
        phones.close();

        customAdapter = new CustomAdapter(this.getActivity(), contactModelArrayList);
        listView.setAdapter(customAdapter);

        // 리스트뷰 클릭이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                TextView tv_name = (TextView) view.findViewById(R.id.name);
                TextView tv_number = (TextView) view.findViewById(R.id.number);
                // 토스트 예제
                // Toast.makeText(getActivity(), "item!", Toast.LENGTH_LONG).show();

//                Intent intent = new Intent(getActivity(), ContactActivity.class);
//                startActivity(intent);
                startActivity(new Intent(getActivity(), ContactActivity.class));
            }
        });

        // fab 클릭이벤트
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(getActivity(), AddPhoneContactActivity.class));
            }
        });
        return view;
    }
}