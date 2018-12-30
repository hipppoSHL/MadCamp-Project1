package com.example.q.myapplication4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contactactivity_layout);

        Intent intent = getIntent();

        TextView contact_name = (TextView) findViewById(R.id.contact_activity_name);
        TextView contact_number = (TextView) findViewById(R.id.contact_activity_number);

        String name = intent.getStringExtra(Fragment1.IntentName);
        if (name != null)
            contact_name.setText(name);

        String number = intent.getStringExtra(Fragment1.IntentNumber);
        if (number != null)
            contact_number.setText(number);

        if (name == null || number == null)
            Toast.makeText(getApplicationContext(), "null!", Toast.LENGTH_LONG).show();
    }
}
