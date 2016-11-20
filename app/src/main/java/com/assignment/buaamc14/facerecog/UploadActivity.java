package com.assignment.buaamc14.facerecog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class UploadActivity extends AppCompatActivity {

    //上传的bitmap
    private Bitmap bitmap;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.iv_upload);
        if (intent != null){
            String string = intent.getStringExtra("string");
            bitmap = BitmapFactory.decodeFile(string);
            imageView.setImageBitmap(bitmap);
        }

    }


}
