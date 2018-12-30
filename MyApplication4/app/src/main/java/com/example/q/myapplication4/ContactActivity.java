package com.example.q.myapplication4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends Activity implements View.OnClickListener{
    private String name;
    private String number;
    private Button call;
    private Button message;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contactactivity_layout);

        Intent intent = getIntent();

        TextView contact_name = (TextView) findViewById(R.id.contact_activity_name);
        TextView contact_number = (TextView) findViewById(R.id.contact_activity_number);
        Button call = (Button) findViewById(R.id.call_button);
        Button message = (Button) findViewById(R.id.message_button);

        call.setOnClickListener(this);
        message.setOnClickListener(this);

        name = intent.getStringExtra(Fragment1.IntentName);
        if (name != null)
            contact_name.setText(name);

        number = intent.getStringExtra(Fragment1.IntentNumber);
        if (number != null)
            contact_number.setText(number);
    }

    @Override
    public void onClick(View v) {
        String tel = "tel:" + number;
        switch(v.getId()) {
            case R.id.call_button:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                break;
            case R.id.message_button:
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendIntent.setType("vnd.android-dir/mms-sms");
                sendIntent.setData(Uri.parse("sms:" + number));
                startActivity(sendIntent);
                break;
        }
    }
}
