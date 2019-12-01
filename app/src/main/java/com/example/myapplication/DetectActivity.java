package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DetectActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    JavaCameraView javaCameraView;
    Mat mat1,mat2;
    Button buttonnext;
    Scalar scalarLow,scalarHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        OpenCVLoader.initDebug();

        javaCameraView = (JavaCameraView) findViewById(R.id.cameraView);
        javaCameraView.setCameraIndex(0);
        buttonnext = (Button) findViewById(R.id.button3);
        scalarLow = new Scalar(45,20,10);
        scalarHigh = new Scalar(75,255,255);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.enableView();
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotifyActivity();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        javaCameraView.disableView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        javaCameraView.enableView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        javaCameraView.disableView();
    }
    @Override
    public void onCameraViewStopped() {

    }
    @Override
    public void onCameraViewStarted(int width, int height) {

        mat1 = new Mat(width,height, CvType.CV_16UC4);
        mat2 = new Mat(width,height, CvType.CV_16UC4);
    }
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Imgproc.cvtColor(inputFrame.rgba(),mat1,Imgproc.COLOR_BGR2HSV);
        Core.inRange(mat1,scalarLow,scalarHigh,mat2);
        return mat2;
    }
    public void openNotifyActivity() {

        Intent intent = new Intent(DetectActivity.this,NotifyActivity.class);
        startActivity(intent);
    }
}
