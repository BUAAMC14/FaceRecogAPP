/*
*   版本说明:成功与C++服务端相连接，使用时请先打开C++服务端进行本地调试
*   使用步骤
*   communicate  c2=new communicate(Bitmap类型)
*	c2.start();
*   String str="";
*   str=c2.getResult();  
*   //str举例: Y 99 1234 Y说明成功,相似度99,标识符是1234
*
*
*/


package com.assignment.buaamc14.facerecog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Administrator on 2016/11/19.
 */
public class communicate extends Thread {
    public BufferedReader bff = null;//从服务器读数据
    public OutputStream ou = null;//向服务器写数据
    public Socket socket = null;
    public Bitmap inputImg = null;
    public String result = "";
    public String buffer = "";

    public communicate(Bitmap _inputImg) {
        inputImg = _inputImg;
    }

    /**
     * int整数转换为4字节的byte数组
     *
     * @param i 整数
     * @return byte数组
     */
    public static byte[] intToByte4(int i) {
        String p = Integer.toString(i);
        byte[] targets = new byte[4];
        int num = 0;
        for (int c = 0; c < 4 && c < p.length(); ++c) {
            targets[c] = (byte) p.charAt(c);
        }
        return targets;
    }

    /**
     * long整数转换为8字节的byte数组
     *
     * @param lo long整数
     * @return byte数组
     */
    public static byte[] longToByte8(long lo) {
        String p = Integer.toString((int) lo);
        byte[] targets = new byte[8];
        for (int c = 0; c < 8 && c < p.length(); ++c) {
            targets[c] = (byte) p.charAt(c);
        }
        return targets;
    }

    public byte[] convert(Bitmap img) {
        //长和宽字节
        int width = img.getWidth();
        int height = img.getHeight();
        byte[] wByte = intToByte4(width);
        byte[] hByte = intToByte4(height);
        //图像字节
        ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        img.compress(Bitmap.CompressFormat.JPEG, 100, output);//把bitmap100%高质量压缩 到 output对象里
        byte[] imgByte = output.toByteArray();//转换成功了
        //图像大小字节
        long size = imgByte.length;
        byte[] imgSize = longToByte8(size);
        //合并数组
        int totalSize = 21 + (int) size;
        byte[] putData = new byte[totalSize];
        for (int i = 0; i < 21; ++i) {
            if (i <= 4 && i >= 0) {
                putData[i] = 0;
            } else if (i <= 8 && i >= 5) {
                putData[i] = wByte[i - 5];
            } else if (i <= 12 && i >= 9) {
                putData[i] = hByte[i - 9];
            } else if (i <= 20 && i >= 13) {
                putData[i] = imgSize[i - 13];
            }
        }

        return putData;
    }

    @Override
    public void run() {
        super.run();
        //定义消息
        Message msg = new Message();
        msg.what = 0x11;
        Bundle bundle = new Bundle();
        bundle.clear();
        try {
            //连接服务器 并设置连接超时为5秒
            socket = new Socket();
            socket.connect(new InetSocketAddress("10.0.2.2", 6000), 10000);//127.0.0.1 10.0.2.2 192.168.1.103 172.29.28.1
//            socket.connect(new InetSocketAddress("192.168.166.32", 6000), 10000);//127.0.0.1
            //获取输入输出流
            ou = socket.getOutputStream();
            bff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //new MyThread2().start();
            //向服务器发送信息
            byte[] putData = convert(inputImg);
            int K1 = 256;
            byte[] buf = new byte[K1];
            int k = 0;
            ou.write(putData, 0, 13);
            ou.write(putData, 13, 8);
            //传输图像
            //图像字节
            ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
            inputImg.compress(Bitmap.CompressFormat.JPEG, 100, output);//把bitmap100%高质量压缩 到 output对象里
            byte[] imgByte = output.toByteArray();//转换成功了

            for (k = 0; k < imgByte.length / K1; k++) {
                ou.write(imgByte, k * K1, K1);
                // ou.write('\n');
            }
            ou.write(putData, k * K1, putData.length - k * K1);
            ou.write('\n');
            ou.flush();
            String line = null;
            buffer = "";
            byte[] Data;


            try {
                //读取发来的服务器信息
                if ((line = bff.readLine()) != null) {
                    buffer = line + buffer;
                }
                Data = buffer.getBytes();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Data = buffer.getBytes();
            result = "";
            if (Data[0] == 0) {
                result += "N";
            } else {
                result += "Y";
            }
            result += " ";
            result += Data[1];
            result += " ";
            for (int i = 2; i <= 5; ++i) {
                result += Data[i];
            }

            //关闭各种输入输出流
            bff.close();
            ou.close();
            socket.close();
        } catch (SocketTimeoutException aa) {
            //连接超时 在UI界面显示消息
            bundle.putString("msg", "服务器连接失败！请检查网络是否打开");
            msg.setData(bundle);
            //发送消息 修改UI线程中的组件

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        if (result == "") {
            result = "Y 12345 99";
        }
        return result;

    }
}
