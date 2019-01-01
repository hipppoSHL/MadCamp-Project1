package com.example.q.myapplication4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.simple.JSONArray;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddTodo extends Activity {

    private CalendarView mCalendarView;
    private EditText todoEditor;
    private TextView dateText;
    private String year, month, date_month;
    private Integer monthint;
    private String str_date;
    JSONArray dataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_layout);
        setTitle("할 일 추가");

        // 날짜 보여주는 TextView
        dateText = (TextView) findViewById(R.id.text_date);

        // edittext 스트링받기(할일 내용?)
        todoEditor = (EditText) findViewById(R.id.todo_edit);

        // CalendarView 관련 처리, 날짜정보 가져오기(year, month, date)
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i /*year*/, int i1 /*month*/, int i2 /*dayOfMonth*/) {
                year = String.valueOf(i);
                month = String.valueOf(i1);
                date_month = String.valueOf(i2);
                monthint = i1;
                str_date = year + "/" + (i1 + 1) + "/" + date_month;
                dateText.setText(str_date);
            }
        });

        // 추가 버튼 누르면 처리할 것들
        Button addTodoButton = (Button) findViewById(R.id.add_todo_button);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoText = todoEditor.getText().toString();
                if (!todoText.equals("") && str_date != null) {
                    dataSet = JsonUse.dataSet;
                    JsonUse.jsonAdd(dataSet, (String.valueOf(monthint + 1)), date_month, todoText, false);
                    String str = dataSet.toString();
                    Log.d("ddd", str);
                    File file = new File("C:\\Users\\q\\Documents\\development\\MadCamp-Project1\\MyApplication4\\app\\src\\main\\assets\\todo_item_list.json");

                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(file, true);
                        fw.write(str);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "일정이 추가되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "날짜와 내용을 잘 입력해 주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // 취소 버튼
        Button quitTodoButton = (Button) findViewById(R.id.quit_todo_button);
        quitTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
