package com.assignment.buaamc14.facerecog;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FaceRecogCore extends AppCompatActivity {

    private static final String TAG = "openCV_Test::Activity";
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);

    private CameraBridgeViewBase mOpenCvCameraView;

    private Mat mRgba;
    private Mat mGray;
    private Mat mRgbaT;

    public CascadeClassifier cascadeClassifier;

    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;
    private int ProcNumber = -1;
    private int ScanSkip = 0;

    private Button btn_capture;
    private Bitmap bitmap;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    try {
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                    } catch (Exception e) {
                        Log.e("OpenCVActivity", "Error loading cascade", e);
                    }

                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public Bitmap FacePicker(String path) {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        // 由Uri得到Bitmap图
//        String picturePath;
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        picturePath = cursor.getString(columnIndex);
//        cursor.close();
//        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        // 由bitmap图得到彩色mat对象
        Mat mRgba = new Mat(bitmap.getHeight() , bitmap.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap,mRgba);

        // 由彩色mat对象得到灰度化的mat对象
        Mat mGray = new Mat();
        Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_BGRA2GRAY, 1);

        // 设置人脸框大小
        int height = mGray.rows();
        if (mAbsoluteFaceSize == 0) {
            mAbsoluteFaceSize = Math.round(mRelativeFaceSize * height);
        }

        MatOfRect faces = new MatOfRect();

        // 得到人脸位置坐标
        if (cascadeClassifier != null) {
            cascadeClassifier.detectMultiScale(mGray, faces, 1.1, 2, 2,
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        }

        // 转换为坐标信息数组
        Rect[] facesArray = faces.toArray();

        // 截取出的人脸部分保存于SubMat
        Mat SubMat;

        // 如果没有识别到人脸，则原样输出
        if(facesArray.length == 0){
            SubMat = mRgba;
        } else {
            SubMat = mRgba.submat(facesArray[0]);
        }

        // 最后将得到的人脸部分转换为bitmaop
        Bitmap resultFace = null;

        Utils.matToBitmap(SubMat,resultFace);

        return resultFace;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recog_core);
    }


}
