package co.example.hzq.jokertwo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import co.example.hzq.jokertwo.Activity.ContextUtil;
import co.example.hzq.jokertwo.HttpUtil.HttpUtil;
import co.example.hzq.jokertwo.Media.MediaUtil;
import co.example.hzq.jokertwo.facePlusPlus.faceApi;
import co.example.hzq.jokertwo.json.JsonUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Hzq on 2017/9/20.
 */

public class NormalProgress {
    /**
     * DetailPage的handler
     */
    public static Handler DetailHandler;

    private static final String TAG = "NormalProgress";

    /**
     * 使用相机的步骤   结束后-》文件上传
     * @param activity
     * @param handler
     */
    public static void useCamera(Activity activity, Handler handler){
        DetailHandler = handler;
        MediaUtil.useCamera(activity);
    }

    public static  void uploadFile(){
        String fileUrl = ContextUtil.getInstance().getFilesDir()+"/pictures/file.jpg";

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

    public static void deteleface(){
        faceApi.detectFace("http://139.199.202.227:8000/static/img/photo.jpg", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "分析失败: " );
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG," deteleface成功" );
                String s = response.body().string();
                Map<String, String> face_token = JsonUtil.find_face_token(s);

                if(face_token.containsKey("face_token")){
                    searchface(face_token.get("face_token"));
                }else   {
                    return;
                }

            }
        });
    }

    private static void searchface(String face_token) {
        //Log.e(TAG, "searchface: "+ face_token );
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
                    DetailHandler.sendMessage(msg);
//                    DetailHandler.sendEmptyMessageAtTime(123,1);
                }else{
                    return ;
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
                    uploadFile();
                    break;
            }
        }
    };


}
