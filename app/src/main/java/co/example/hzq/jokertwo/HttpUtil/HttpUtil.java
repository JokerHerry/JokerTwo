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

    public static void upLoadFile(String url,String filepath){
        File file = new File(filepath);
        if(!file.exists()){
            Log.e(TAG, "upLoadFile: 文件不存在");
            return;
        }
        RequestBody requestBodyfile = RequestBody.create(MediaType.parse("application/octet-stream"),file);

        MultipartBody.Builder mulitiparBuilder = new MultipartBody.Builder();

        RequestBody requestBody = mulitiparBuilder
                .setType(MultipartBody.FORM)
                .addFormDataPart("mPhoto", "photo.jpg", requestBodyfile)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();

        Call call =  new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: 成功" );
            }
        });
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
                Log.e(TAG, "onResponse: "+str.toString() );
            }
        });

    }

}
