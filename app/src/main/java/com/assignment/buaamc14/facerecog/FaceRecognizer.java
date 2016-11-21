package com.assignment.buaamc14.facerecog;

import android.app.Activity;
import android.content.Context;
import android.content.*;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by zengyutao on 2016/11/21.
 */

public class FaceRecognizer {
    private Mat                     mRgba;
    private Mat                     mGray;
    private Mat                     mRgbaT;

    private CascadeClassifier       cascadeClassifier;

    private float                   mRelativeFaceSize   = 0.2f;
    private int                     mAbsoluteFaceSize   = 0;
    private int                     ProcNumber = -1;
    private int                     ScanSkip = 0;

    public FaceRecognizer(){
//
//        try {
//            InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
//            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
//            File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
//            FileOutputStream os = new FileOutputStream(mCascadeFile);
//
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            is.close();
//            os.close();
//
//            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
//        } catch (Exception e) {
//            Log.e("OpenCVActivity", "Error loading cascade", e);
//        }

    }

}
