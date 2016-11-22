package com.assignment.buaamc14.facerecog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadActivity extends AppCompatActivity {

    //上传的bitmap
    public static Bitmap bitmap;
    //    public static String result;
    private Button btn_refresh;
    private String str;
    private communicate Com;
    private TextView result, ID, similarity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.iv_upload);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = Com.getResult();
                String words[] = str.split(" ");
                if (words[0].equals("Y")) {
                    result.setText("识别成功");
                } else {
                    result.setText("识别失败");
                }

                ID.setText("ID:" + words[1]);
                similarity.setText("准确率：" + words[2] + "%");
            }
        });
        if (intent != null) {
            int key = intent.getIntExtra("key", 0);
            if (key == 1)
                imageView.setImageBitmap(bitmap);

            //为了测试暂时注释，调试网络功能请打开。ctrl+/可以快速注释
            // Changed here !
            Com = new communicate(bitmap);
            Com.start();
            str = "";
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            str = Com.getResult();

            //str举例: Y 99 1234 Y说明成功,相似度99,标识符是1234
            String words[] = str.split(" ");
            result = (TextView) findViewById(R.id.upload_result);
            ID = (TextView) findViewById(R.id.result_id);
            similarity = (TextView) findViewById(R.id.result_grade);
            if (words[0].equals("Y")) {
                result.setText("识别成功");
            } else {
                result.setText("识别失败");
            }

            ID.setText("ID:" + words[1]);
            similarity.setText("准确率：" + words[2] + "%");

        }
    }

}
