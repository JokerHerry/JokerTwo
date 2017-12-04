package co.example.hzq.jokertwo.NormalProgress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.example.hzq.jokertwo.Activity.BaseActivity;
import co.example.hzq.jokertwo.Activity.ContextUtil;
import co.example.hzq.jokertwo.HttpUtil.HttpUtil;
import co.example.hzq.jokertwo.Media.MediaUtil;
import co.example.hzq.jokertwo.Media.Uri2Path;
import co.example.hzq.jokertwo.Util.TimeUtil.TimeCallback;
import co.example.hzq.jokertwo.Util.TimeUtil.TimeUtil;
import co.example.hzq.jokertwo.facePlusPlus.faceApi;
import co.example.hzq.jokertwo.json.JsonUtil;
import co.example.hzq.jokertwo.staticData.staticData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Hzq on 2017/9/20.
 */

public class NormalProgress {
    private static final String TAG = "NormalProgress";
    public static Handler DetailActivityHandler;
    private static BaseActivity context;

    public NormalProgress(){
    }

    public void start(BaseActivity context, Handler handler){
        this.context = context;
        useCamera(context,handler);
    }

    public void startPhoto(BaseActivity context,Handler handler){
        usePhoto(context,handler);
        this.context = context;
    }

    /**
     * 使用相机的步骤   结束后-》文件上传
     * @param activity
     * @param handler
     */
    public void useCamera(BaseActivity activity, Handler handler){
        DetailActivityHandler = handler;
        MediaUtil.useCamera(activity);
    }

    private void usePhoto(BaseActivity context, Handler handler){
        DetailActivityHandler = handler;
        MediaUtil.usePhoto(context);
    }

    //上传
    public static  void uploadFile(String fileUrl){
        context.showDialog("开始上传照片");
        HttpUtil.upLoadFile(staticData.upload_url, fileUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "上传文件失败，请检查服务端是否正常开启！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "上传成功" );
                deteleface();
            }
        });
    }

    //分析--face
    public static void deteleface(){
        context.showDialog("开始分析图片");
        faceApi.detectFace("http://139.199.202.227:8000/static/img/photo.jpg", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "分析失败: " );
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG," deteleface成功" );
                String s = response.body().string();

                List<Map<String, String>> face_tokens = JsonUtil.find_face_token(s);

                if (face_tokens==null){
                    Log.e(TAG, "onResponse: 不存在人脸" );
                    context.shortToast("人脸分析失败");
                    context.stopDialog();
                    return;
                }

                for (int i = 0; i < face_tokens.size(); i++) {
                    Map<String, String> face_token = face_tokens.get(i);
                    if(face_token.containsKey("face_token")){
                        searchface(face_token.get("face_token"));
                    }else{
                        Log.e(TAG, "onResponse: 异常错误");
                        return;
                    }
                }
            }
        });
    }

    //搜索--face
    private static void searchface(String face_token) {
        context.showDialog("分析结束，正在统计数据");
        TimeUtil.waitFunc(2000, new TimeCallback() {
            @Override
            public void OnTime() {
                context.stopDialog();
            }
        });
        faceApi.searchFace(face_token, staticData.faceset_token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure:   searchface " );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Map<String, String> search_data = JsonUtil.find_search_data(s);

                if(search_data.containsKey("confidence")){
                    Log.e(TAG, "onResponse: "+search_data.toString() );
                    Message msg = new Message();
                    msg.what = 997;
                    Bundle bundle = new Bundle();
                    bundle.putString("name",""+search_data.get("name"));
                    msg.setData(bundle);

                    //返回信息回去，修改UI
                    DetailActivityHandler.sendMessage(msg);
                }else{
                    context.shortToast("发现一张人脸分析失败");
                }
            }
        });
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 998:
                    Log.e(TAG, "拍照成功" );
                    String fileUrl = ContextUtil.getInstance().getFilesDir()+"/pictures/file.jpg";
                    rotatePic(fileUrl);
                    uploadFile(fileUrl);
                    break;
                case 999:
                    Log.e(TAG, "相册成功" );
                    HashMap<String,Uri> obj = (HashMap<String, Uri>) msg.obj;
                    Uri uri = obj.get("uri");
                    String path = Uri2Path.getRealPathFromUri(context, uri);
                    uploadFile(path);
                    break;
            }
        }
    };


    /**
     * 解决拍照后，照片旋转90的情况
     */
    //查看图片旋转了多少度
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    //重新旋转图片
    public static boolean rotatePic(String fileUrl){
        //找到应该旋转的角度
        int i = readPictureDegree(fileUrl);

        Matrix matrix = new Matrix();
        matrix.setRotate(i);
        Bitmap bitmap = BitmapFactory.decodeFile(fileUrl);
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

        //旋转后保存
        File file = new File(fileUrl);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);//将图片压缩到流中
            bos.flush();//输出
            bos.close();//关闭
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
