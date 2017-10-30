package co.example.hzq.jokertwo.facePlusPlus;

import java.io.IOException;
import java.util.HashMap;

import co.example.hzq.jokertwo.Activity.ContextUtil;
import co.example.hzq.jokertwo.HttpUtil.HttpUtil;
import co.example.hzq.jokertwo.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Hzq on 2017/9/13.
 */

public class faceApi {
    //detect
    static String detect = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    static String compare = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    static String search = "https://api-cn.faceplusplus.com/facepp/v3/search";

    static String set_faceuserid = "https://api-cn.faceplusplus.com/facepp/v3/face/setuserid";

    static String faceset_create = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
    static String faceset_addface = " https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";
    static String faceset_removeface = " https://api-cn.faceplusplus.com/facepp/v3/faceset/removeface";


    private static String key = ContextUtil.getInstance().getResources().getString(R.string.face_key);
    private static String secret = ContextUtil.getInstance().getResources().getString(R.string.face_secret);

    public static void detectFace(final String photoUrl, Callback callback){
        HttpUtil.post(detect,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("image_url", photoUrl);
        }},callback);
    }

    public static void compareFace(final String photoUrl1,final String photoUrl2){
        HttpUtil.post(compare,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("image_url1", photoUrl1);
            put("image_url2", photoUrl2);
        }});
    }

    public static void searchFace(final String faceToken,final String facesetToken,Callback callback){
        HttpUtil.post(search,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("face_token",faceToken);
            put("faceset_token",facesetToken);
        }},callback);
    }

    public static void faceset_createFace(final String display_name){
        HttpUtil.post(faceset_create,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("display_name", display_name);
        }});
    }

    public static void faceset_addfaceFace(final String faceset_token, final String face_token, final String user_id){
        HttpUtil.post(faceset_addface,
                new HashMap<String, Object>() {{
                    put("api_key", key);
                    put("api_secret", secret);
                    put("faceset_token", faceset_token);
                    put("face_tokens", face_token);
                    }},
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        face_setUserid(face_token,user_id);
                    }
        });
    }

    public static void face_setUserid(final String face_token, final String user_id){
        HttpUtil.post(set_faceuserid,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("face_token", face_token);
            put("user_id", user_id);
        }});
    }

    public static void faceset_removefaceFace(final String faceset_token,final String face_token){
        HttpUtil.post(faceset_removeface,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("faceset_token", faceset_token);
            put("face_tokens", face_token);
        }});
    }

}
