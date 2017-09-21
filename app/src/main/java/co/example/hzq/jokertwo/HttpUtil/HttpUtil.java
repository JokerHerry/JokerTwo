package co.example.hzq.jokertwo.HttpUtil;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Hzq on 2017/9/11.
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    static OkHttpClient okHttpClient = new OkHttpClient();

    public  static void get(String url){
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " );
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: " );
                String str = response.body().string();
                Log.e(TAG, "onResponse: "+str.toString() );
            }
        });
    }
    public  static void get(String url,Callback callback){
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void upLoadFile(String url,String filepath){
        //检测传来的文件是否存在
        File file = new File(filepath);
        if(!file.exists()){
            Log.e(TAG, "upLoadFile: 文件不存在");
            return;
        }
        RequestBody requestBodyfile = RequestBody.create(MediaType.parse("image/png"),file);
        //构建一个文件类型的requestBody
        MultipartBody.Builder mulitiparBuilder = new MultipartBody.Builder();
        //这里也可以通过键值对的方式实现添加参数
        MultipartBody requestBody = mulitiparBuilder
                .setType(MultipartBody.FORM)
                //addFormDataPart(用于服务器识别的标识，服务器接收后文件的名字，文件-requestBody)
                .addFormDataPart("mPhoto", "photo.jpg", requestBodyfile)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .post(requestBody)
                .build();

        Call call =  new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: 成功" );
                Log.e(TAG, "onResponse: "+response.body().string() );
            }
        });
    }
    public static void upLoadFile(String url,String filepath,Callback callback){
        //检测传来的文件是否存在
        File file = new File(filepath);
        if(!file.exists()){
            Log.e(TAG, "upLoadFile: 文件不存在");
            return;
        }
        RequestBody requestBodyfile = RequestBody.create(MediaType.parse("image/png"),file);
        //构建一个文件类型的requestBody
        MultipartBody.Builder mulitiparBuilder = new MultipartBody.Builder();
        //这里也可以通过键值对的方式实现添加参数
        MultipartBody requestBody = mulitiparBuilder
                .setType(MultipartBody.FORM)
                //addFormDataPart(用于服务器识别的标识，服务器接收后文件的名字，文件-requestBody)
                .addFormDataPart("mPhoto", "photo.jpg", requestBodyfile)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .post(requestBody)
                .build();

        Call call =  new OkHttpClient().newCall(request);
        call.enqueue(callback);
    }

    public static void post(String url, Map<String,Object> theData){
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (String key:theData.keySet()){
            formBuilder.add(key,theData.get(key).toString());
        }
        RequestBody requestBody = formBuilder.build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .post(requestBody)
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " );
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e(TAG, "onResponse: "+str.toString());
            }
        });

    }
    public static void post(String url, Map<String,Object> theData,Callback callback){
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (String key:theData.keySet()){
            formBuilder.add(key,theData.get(key).toString());
        }
        RequestBody requestBody = formBuilder.build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .post(requestBody)
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
