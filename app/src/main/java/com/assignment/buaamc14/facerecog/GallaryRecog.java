package com.assignment.buaamc14.facerecog;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class GallaryRecog extends AppCompatActivity {

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView iv_image;
    private Button btn_upload;
    private Bitmap bitmap;
    private String picturePath;

    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary_recog);
        this.iv_image = (ImageView) findViewById(R.id.iv_preview);
        btn_upload = (Button) findViewById(R.id.btn_upload);
    }

    /*
     * 从相册获取
     */
    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(picturePath);
                    iv_image.setImageBitmap(bitmap);
                    btn_upload.setText("开始上传！");
                    btn_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            upload(btn_upload);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void upload(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        intent.putExtra("string", picturePath);
        startActivity(intent);
    }
}
