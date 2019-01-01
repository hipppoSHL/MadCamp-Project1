package com.example.q.myapplication4;

// Fragment1의 어댑터입니다.

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ContactModel> contactModelArrayList;

    public CustomAdapter(Context context, ArrayList<ContactModel> contactModelArrayList) {

        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }
    
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return contactModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvnumber = (TextView) convertView.findViewById(R.id.number);
            // 추가함(이미지)
            holder.tvimage = (ImageView) convertView.findViewById(R.id.image);

            // 이미지 동그랗게 하는거라는데 안먹힘
            // holder.tvimage.setBackground(new ShapeDrawable(new OvalShape()));
            // holder.tvimage.setClipToOutline(true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(contactModelArrayList.get(position).getName());
        holder.tvnumber.setText(contactModelArrayList.get(position).getNumber());
        holder.tvimage.setImageBitmap(contactModelArrayList.get(position).getImage());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvnumber;
        protected ImageView tvimage;

    }
}
