package com.example.q.myapplication4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodo extends Activity {

    private CalendarView mCalendarView;
    private EditText todoEditor;
    private Integer year, month, date_month;
    private String str_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_layout);
        setTitle("할 일 추가");

        // CalendarView 관련 처리, 날짜정보 가져오기(year, month, date)
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i /*year*/, int i1 /*month*/, int i2 /*dayOfMonth*/) {
                year = i;
                month = i1;
                date_month = i2;
                str_date = year + "/" + (month + 1) + "/" + date_month;
            }
        });

        // 날짜 보여주는 TextView

        // 추가 버튼 누르면 처리할 것들
        Button addTodoButton = (Button) findViewById(R.id.add_todo_button);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoText = todoEditor.getText().toString();
                Toast.makeText(getApplicationContext(), todoText + "\n" + str_date, Toast.LENGTH_LONG).show();
                finish();
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

        todoEditor = (EditText) findViewById(R.id.todo_edit);
        String todoText = todoEditor.getText().toString();

    }
}
