package co.example.hzq.jokertwo.json;

import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.example.hzq.jokertwo.Activity.ContextUtil;

/**
 * Created by Hzq on 2017/7/21.
 */
public class JsonUtil {

    private static final String TAG = "JsonUtil";

    public static JSONObject myJson;
    private static String fileName = "test.json";

    public static List<String> returnStuList(String stuJson){
        List<String> stulist = new ArrayList<String>();

        String json = "{\n" +
                "  \"ifOK\":\"1\",\n" +
                "\n" +
                "  \"stuInformation\":[\n" +
                "    {\n" +
                "      \"level\":\"2014\",\n" +
                "      \"student\":[\n" +
                "        {\n" +
                "          \"id\":\"1\",\n" +
                "          \"name\":\"caomingjun\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\":\"2\",\n" +
                "          \"name\":\"chenyao\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\":\"3\",\n" +
                "          \"name\":\"chenyi\"\n" +
                "        }\n" +
                "      ]\n" +
                "\n" +
                "    },\n" +
                "    {\n" +
                "      \"level\":\"2015\",\n" +
                "      \"student\":[\n" +
                "        {\n" +
                "          \"id\":\"1\",\n" +
                "          \"name\":\"zhangsan\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\":\"2\",\n" +
                "          \"name\":\"lisi\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\":\"3\",\n" +
                "          \"name\":\"wangwu\"\n" +
                "        }\n" +
                "      ]\n" +
                "\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject group = JSON.parseObject(json);
        JSONArray stuInformation = group.getJSONArray("stuInformation");
        JSONObject level = stuInformation.getJSONObject(1);
        return stulist;
    }

    public static String getJson(){

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = ContextUtil.getInstance().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("test.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getYourNeed() {
        String json = getJson();


        JSONObject group = JSON.parseObject(json);
        myJson = group;

        JSONObject stuInformation = group.getJSONObject("stuInformation");

        Set<String> level = stuInformation.keySet();

        List<String> level2 = new ArrayList<String>(level);

        return level2;
    }

    public static List<String> getClassFromLevel(String level){
        JSONObject stuInformation = myJson.getJSONObject("stuInformation");
        JSONObject levelJsonObj = stuInformation.getJSONObject(level);

        Set<String> theClass = levelJsonObj.keySet();

        List<String> theClass2 = new ArrayList<String>(theClass);

        return theClass2;
    }

    public static void refreshJson(){
        myJson = null;
        myJson = JSON.parseObject(getJson());
    }

    public static List<Map<String,String>> getStuFromClass(String level, String theClass) {
        // 应该是返回的一个adapter 通过level和theClass
        // 找到那个班级里面的所有人以及基本信息

        if(myJson==null){
            refreshJson();
        }

        JSONObject stuInformation = myJson.getJSONObject("stuInformation");
        JSONArray stuArray = (stuInformation.getJSONObject(level)).getJSONArray(theClass);

        List<Map<String,String>> stuList = new ArrayList<>();

        for(int i = 0;i<stuArray.size();i++){
            Map<String,String> stu = new HashMap<String, String>();
            stu.put("id",((JSONObject)stuArray.get(i)).getString("id"));
            stu.put("name",((JSONObject)stuArray.get(i)).getString("name"));
            stuList.add(stu);
        }

        return stuList;
    }

    public static List<Map<String, String>> find_face_token(String json) {
        final JSONObject thejson = JSON.parseObject(json);


        JSONArray faces = thejson.getJSONArray("faces");

        if(faces == null){
            Log.e(TAG, "error_message: " + thejson.getString("error_message"));
            return null;
        }

        List<Map<String, String>> facetokenList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < faces.size(); i++) {
            final JSONObject JSONobject = (JSONObject) faces.get(i);
            facetokenList.add(new HashMap<String, String>() {{
                put("face_token", JSONobject.getString("face_token"));
            }});
        }

        return facetokenList;
    }

    //test find face_token and position
    public static List<Map<String, Object>> find_test_face_token(String json) {
        final JSONObject thejson = JSON.parseObject(json);

        JSONArray faces = thejson.getJSONArray("faces");

        if(faces == null){
            Log.e(TAG, "error_message: " + thejson.getString("error_message"));
            return null;
        }

        List<Map<String, Object>> facetokenList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < faces.size(); i++) {
            final JSONObject JSONobject = (JSONObject) faces.get(i);
            facetokenList.add(new HashMap<String, Object>() {{
                put("face_token", JSONobject.getString("face_token"));
                put("width",JSONobject.getJSONObject("face_rectangle").getIntValue("width"));
                put("top",JSONobject.getJSONObject("face_rectangle").getIntValue("top"));
                put("left",JSONobject.getJSONObject("face_rectangle").getIntValue("left"));
                put("height",JSONobject.getJSONObject("face_rectangle").getIntValue("height"));
            }});
        }

        return facetokenList;
    }

    /**
     * 如果存在人脸值，返回List
     * 不存在，返回null
     * @param json
     * @return
     */
    public static Map<String, String> find_search_data(String json) {
        final JSONObject thejson = JSON.parseObject(json);

        JSONArray results = thejson.getJSONArray("results");

        if(results == null){
            Log.e(TAG, "error_message: " + thejson.getString("error_message"));
            return new HashMap<String,String>(){{
                put("error_message",thejson.getString("error_message"));
            }};
        }

        final JSONObject o = (JSONObject)results.get(0);

        final String name = o.getString("user_id");
        final String confidence = o.getString("confidence");


        return new HashMap<String, String>(){{
            put("name",name);
            put("confidence",confidence);
        }};
    }
}
