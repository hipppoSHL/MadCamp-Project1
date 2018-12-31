package com.example.q.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.json.simple.JSONArray;



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

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

        listView = (ListView)view.findViewById(R.id.listView2);

        dataSet = JsonUse.dataSet;
        if (check == false) {
            JsonUse.jsonAdd(dataSet, 1, 1, "안녕하세요!", false);
            check = true;
        }

        // Log.d("aaa", String.valueOf(dataSet));


        fragment3Adapter = new Fragment3Adapter(this.getActivity(), dataSet);
        listView.setAdapter(fragment3Adapter);

        // fab 클릭이벤트
        fab = (FloatingActionButton) view.findViewById(R.id.fab_todoadd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTodo.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragment3Adapter = new Fragment3Adapter(this.getActivity(), dataSet);
        listView.setAdapter(fragment3Adapter);

    }
}