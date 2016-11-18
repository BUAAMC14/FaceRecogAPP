package com.assignment.buaamc14.facerecog;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class CamRecog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_recog);

        Intent intent = getIntent();
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Here should be the camera diaoyong");

        ViewGroup layout= (ViewGroup) findViewById(R.id.activity_cam_recog);
        layout.addView(textView);
    }
}
