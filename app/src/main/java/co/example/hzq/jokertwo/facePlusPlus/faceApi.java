package co.example.hzq.jokertwo.facePlusPlus;

import java.util.HashMap;

import co.example.hzq.jokertwo.HttpUtil.HttpUtil;

/**
 * Created by Hzq on 2017/9/13.
 */

public class faceApi {
    //detect
    static String detect = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    static String compare = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    static String search = "https://api-cn.faceplusplus.com/facepp/v3/search";

    static String faceset_create = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
    static String faceset_addface = " https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";
    static String faceset_removeface = " https://api-cn.faceplusplus.com/facepp/v3/faceset/removeface";


    static String key = "pOJ8dh8Eb1rJ7lOFAb-gbTi1Pjmn-3Ql";
    static String secret = "mvsBFOLh6PvZm_AZs8Q7xfRFdnNdrCTd";

    public static void detectFace(final String photoUrl){
        HttpUtil.post(detect,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("image_url", photoUrl);
        }});
    }

    public static void compareFace(final String photoUrl1,final String photoUrl2){
        HttpUtil.post(compare,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("image_url1", photoUrl1);
            put("image_url2", photoUrl2);
        }});
    }

    public static void searchFace(final String faceToken,final String facesetToken){
        HttpUtil.post(search,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("face_token",faceToken);
            put("faceset_token",facesetToken);
        }});
    }

    public static void faceset_createFace(final String display_name){
        HttpUtil.post(faceset_create,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("display_name", display_name);
        }});
    }

    public static void faceset_addfaceFace(final String faceset_token,final String face_token){
        HttpUtil.post(faceset_addface,new HashMap<String,Object>(){{
            put("api_key",key);
            put("api_secret",secret);
            put("faceset_token", faceset_token);
            put("face_tokens", face_token);
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
