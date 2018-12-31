package com.example.q.myapplication4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.w3c.dom.Text;

public class Fragment3Adapter extends BaseAdapter {

    private Context context;
    private JSONArray dataSet;

    public Fragment3Adapter(Context context, JSONArray dataSet){
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount(){
        return dataSet.size();
    }
    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
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
            convertView = inflater.inflate(R.layout.todo_list_item, null, true);

            holder.tvdate = (TextView) convertView.findViewById(R.id.todo_date);
            holder.tvnote = (TextView) convertView.findViewById(R.id.todo_text);
            holder.tvfinish = (TextView) convertView.findViewById(R.id.check);

            // 이미지 동그랗게 하는거라는데 안먹힘
            // holder.tvimage.setBackground(new ShapeDrawable(new OvalShape()));
            // holder.tvimage.setClipToOutline(true);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

            JSONObject data = (JSONObject)dataSet.get(position);
            String date = dateStringCreate((int) data.get("month"), (int) data.get("day"));

            String note = (String) data.get("note");
            boolean finish = (boolean) data.get("finish");
            Log.d("ddd", String.valueOf(data));
            holder.tvdate.setText(date);
            holder.tvnote.setText(note);
            holder.tvfinish.setText("1");

        return convertView;
    }

    private class ViewHolder{

        protected TextView tvdate, tvnote, tvfinish;
    }

    private String dateStringCreate(int month, int day){
        String monthString = String.valueOf(month);
        String dayString = String.valueOf(day);

        return monthString + "/" + dayString;
    }
}
