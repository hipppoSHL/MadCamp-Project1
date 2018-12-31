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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView;
        Fragment3Adapter fragment3Adapter;
        FloatingActionButton fab;
        JSONArray dataSet;

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);

        listView = (ListView)view.findViewById(R.id.listView2);

        dataSet = new JSONArray();
        JsonUse.jsonAdd(dataSet,12,12,"adfasdf", false);
        JsonUse.jsonAdd(dataSet,12,13,"adfasadfdf", false);
        JsonUse.jsonAdd(dataSet,12,14,"adfasfasdadfdf", false);

        Log.d("aaa", String.valueOf(dataSet));


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
}