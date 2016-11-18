package com.assignment.buaamc14.facerecog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class GallaryRecog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary_recog);

        Intent intent = getIntent();
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Here should be the gallary");

        ViewGroup layout= (ViewGroup) findViewById(R.id.activity_gallary_recog);
        layout.addView(textView);
    }
}
