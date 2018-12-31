package com.example.q.myapplication4;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Fragment1 extends Fragment {
    public static final String IntentName = "nameText";
    public static final String IntentNumber = "numText";

    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;
    private Bitmap bp;
    private View view;

    public Fragment1() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment1, container, false);

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
            // Log.d("name>>",name+"  "+phoneNumber);
        }
        phones.close();

        customAdapter = new CustomAdapter(this.getActivity(), contactModelArrayList);
        listView.setAdapter(customAdapter);

        // 리스트뷰 클릭이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContactActivity.class);

                TextView name = (TextView) view.findViewById(R.id.name);
                TextView number = (TextView) view.findViewById(R.id.number);

                Log.w(IntentName, name.getText().toString());
                Log.w(IntentNumber, number.getText().toString());

                intent.putExtra(IntentName, name.getText().toString());
                intent.putExtra(IntentNumber, number.getText().toString());

                startActivity(intent);
            }
        });

        // fab 클릭이벤트
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddPhoneContactActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

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
            // Log.d("name>>",name+"  "+phoneNumber);
        }
        phones.close();
        customAdapter = new CustomAdapter(this.getActivity(), contactModelArrayList);
        listView.setAdapter(customAdapter);
    }
}