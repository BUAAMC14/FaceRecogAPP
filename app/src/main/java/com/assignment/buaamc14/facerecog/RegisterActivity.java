package com.assignment.buaamc14.facerecog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {

    private EditText editText;
    private communicate Com;
    private TextView result;
    private String str;
    public static char user_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView imageView = (ImageView) findViewById(R.id.iv_register);
        imageView.setImageBitmap(UploadActivity.bitmap);
        Button btn_register_upload = (Button) findViewById(R.id.btn_register_upload);
        Button btn_register_result = (Button) findViewById(R.id.btn_register_result);
        editText = (EditText) findViewById(R.id.et_register_ID);
        result = (TextView) findViewById(R.id.upload_result);
        btn_register_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = Com.getResult();
                String words[] = str.split(" ");
                if (words[0].equals("Y")) {
                    result.setText("添加成功");
                } else {
                    result.setText("添加失败，图片非人脸");
                }
            }
        });

        btn_register_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Com = new communicate(UploadActivity.bitmap);
                String temp = editText.getText().toString();
                int tempint = Integer.parseInt(temp);
                user_ID = (char) (tempint);
                Com.start();
                str = "";
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                str = Com.getResult();
            }
        });
    }
}
