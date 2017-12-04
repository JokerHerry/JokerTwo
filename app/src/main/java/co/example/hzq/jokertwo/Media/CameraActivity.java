package co.example.hzq.jokertwo.Media;

import android.Manifest;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.Activity.BaseActivity;
import co.example.hzq.jokertwo.R;
public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = "CameraActivity";
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mSurfaceHolder;
    //对焦的返回函数
    private Camera.AutoFocusCallback autoFocusCallback;
    //拍照的返回函数
    private Camera.PictureCallback mPictureCallback;
    //获取点击的位置
    private Point onTouchPoint;
    Button btn;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initUI();
        initData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    //初始化数据
    private void initData() {
        autoFocusCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
                if(b){
                    Log.e(TAG, "onAutoFocus: 对焦成功" );
                }else{
                    Log.e(TAG, "onAutoFocus: 对焦失败" );
                }
            }
        };

        onTouchPoint = new Point(0,0);

        mPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    onTouchPoint = new Point((int)x,(int)y);
                }
                return false;
            }
        });
        mPictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                String path = getFilesDir().toString();
                File tempFile = new File(path,"newPic.png");
                try {
                    FileOutputStream fos = new FileOutputStream(tempFile);
                    fos.write(bytes);
                    fos.close();
                    Intent intent = new Intent(CameraActivity.this,resultActivity.class);
                    intent.putExtra("picPath",tempFile.getAbsolutePath());
                    startActivity(intent);
                    CameraActivity.this.finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

    }

    //初始化界面UI
    private void initUI() {
        mPreview = (SurfaceView) findViewById(R.id.mySurfaceView);
        mPreview.setOnClickListener(this);
        mSurfaceHolder = mPreview.getHolder();
        mSurfaceHolder.addCallback(this);
        getPermission(this, Manifest.permission.CAMERA);
        image = (ImageView) findViewById(R.id.image);
        btn = (Button) findViewById(R.id.capture);
        btn.setOnClickListener(this);
    }


    public void capture(View view){
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(1920,1080);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
                if (b){
                    mCamera.takePicture(null,null,mPictureCallback);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCamera==null){
            mCamera = getCamera();
            if(mSurfaceHolder!=null){
                setStartPreview(mCamera,mSurfaceHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCaemera();
    }

    /**
     * 获取需要摄像机
     * @return
     */
    private Camera getCamera(){
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            camera = null;
        }
        return camera;
    }

    /**
     * 开始预览
     */
    private void setStartPreview(Camera camera,SurfaceHolder holder){
        try {
            camera.setPreviewDisplay(holder);
            //将系统camera预览角度旋转90
            camera.setDisplayOrientation(90);
            camera.startPreview();
            cameraAutoFocus(camera);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放摄像机
     */
    private void releaseCaemera(){
        if(mCamera!=null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setStartPreview(mCamera,mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mCamera.stopPreview();
        setStartPreview(mCamera,mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCaemera();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mySurfaceView:
                startFocus(onTouchPoint,mCamera);
                break;
            case R.id.capture:
                capture(view);
            default:
                break;
        }

    }

    /**
     * 每次点击屏幕时，
     * 实现点击对焦。
     * 如果不能实现点击对焦，则再次进行自动对焦
     */
    private void startFocus(Point point, Camera camera){

        if(camera == null){
            return;
        }

        Camera.Parameters parameters = camera.getParameters();

        if (parameters.getMaxNumFocusAreas() <= 0) {
            cameraAutoFocus(camera);
            return;
        }

        cameraPointFocus(onTouchPoint,camera,parameters);

    }

    private void cameraPointFocus(Point point, Camera camera, Camera.Parameters parameters){
        camera.cancelAutoFocus();

        //定点对焦
        List<Camera.Area> areas = new ArrayList<Camera.Area>();
        int left = point.x - 100;
        int top = point.y - 100;
        int right = point.x + 100;
        int bottom = point.y + 100;
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        areas.add(new Camera.Area(new Rect(left, top, right, bottom), 1000));
        parameters.setFocusAreas(areas);

        image.setTranslationX(point.x);
        image.setTranslationY(point.y);

        try {
            camera.setParameters(parameters);
            camera.autoFocus(autoFocusCallback);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void cameraAutoFocus(Camera camera){
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusAreas(null);
        camera.setParameters(parameters);
        camera.autoFocus(autoFocusCallback);
    }


}