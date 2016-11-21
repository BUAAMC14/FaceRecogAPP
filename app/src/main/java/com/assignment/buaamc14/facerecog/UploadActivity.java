package com.assignment.buaamc14.facerecog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

            // Changed here !
            communicate Com = new communicate(bitmap);
            Com.start();
            String str = "";
            str = Com.getResult();
            try {
                Thread.sleep(100000);
            }catch (Exception e){

            }
            //str举例: Y 99 1234 Y说明成功,相似度99,标识符是1234
            String words[] = str.split(" ");
            TextView result = (TextView) findViewById(R.id.upload_result);
            TextView ID = (TextView) findViewById(R.id.result_id);
            TextView similarity = (TextView) findViewById(R.id.result_grade);
            if(words[0].equals("Y")){
                result.setText("识别成功");
            } else {
                result.setText("识别失败");
            }

            ID.setText("ID:"+words[1]);
            similarity.setText("准确率："+words[2]+"%");

        }
    }

}
