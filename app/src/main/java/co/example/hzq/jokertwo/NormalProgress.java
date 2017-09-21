package co.example.hzq.jokertwo;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import co.example.hzq.jokertwo.facePlusPlus.faceApi;
import co.example.hzq.jokertwo.json.JsonUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Hzq on 2017/9/20.
 */

public class NormalProgress {

    private static final String TAG = "NormalProgress";



    public static void deteleface(){
        faceApi.detectFace("http://139.199.202.227:8000/static/img/photo.jpg", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " );
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.e(TAG, "onResponse: " + " deteleface成功" );
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
                Log.e(TAG, "onFailure: "+"  searchface " );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Map<String, String> search_data = JsonUtil.find_search_data(s);
            }
        });
    }


}
