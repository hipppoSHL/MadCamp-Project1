package com.example.q.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public class Fragment3 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean check = false;
    private ListView listView;
    private JSONArray dataSet;
    private Fragment3Adapter fragment3Adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab;
        Button button;

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

        listView = (ListView)view.findViewById(R.id.listView2);

        dataSet = JsonUse.dataSet;

        String str = loadJSONFromAsset();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = (JSONArray) obj;
        dataSet = jsonArray;

        if (check == false) {
            JsonUse.jsonAdd(dataSet, "1", "1", "안녕하세요!", false);
            check = true;
        }

        Log.d("aaa", String.valueOf(dataSet));


        fragment3Adapter = new Fragment3Adapter(this.getActivity(), dataSet);
        listView.setAdapter(fragment3Adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // fab 클릭이벤트
        fab = (FloatingActionButton) view.findViewById(R.id.fab_todoadd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTodo.class));
            }
        });

        button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                JSONArray dataSetFromAdapter = (JSONArray)fragment3Adapter.getDataSet();
                Log.d("aaa", String.valueOf(dataSetFromAdapter));
                Log.d("aaa", String.valueOf(dataSet));

                int count = fragment3Adapter.getCount();
                //Log.d("aaa", String.valueOf(count));

                for(int i = count-1; i>=0;i--){
                    JSONObject data = (JSONObject)dataSet.get(i);
                    boolean finish = (boolean) data.get("finish");

                   if(finish){
                       dataSetFromAdapter.remove(i);
                   }
                }
                Log.d("aaa", String.valueOf(dataSetFromAdapter));
                Log.d("aaa", String.valueOf(dataSet));

                listView.clearChoices();
                fragment3Adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ddd", String.valueOf(position));
                JSONObject data = (JSONObject)dataSet.get(position);
                boolean finish = (boolean) data.get("finish");
                data.put("finish", !finish);
//                fragment3Adapter.notifyDataSetChanged();
                ((CheckBox)(view.findViewById(R.id.check))).setChecked(!finish);
                Log.d("ddd", String.valueOf(dataSet));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String str = loadJSONFromAsset();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = (JSONArray) obj;
        dataSet = jsonArray;
        fragment3Adapter = new Fragment3Adapter(this.getActivity(), dataSet);
        listView.setAdapter(fragment3Adapter);

    }

    // assets 폴더에서 json 파일 읽어와 스트링 리턴한다
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("todo_item_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}