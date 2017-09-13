package co.example.hzq.jokertwo.json;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import co.example.hzq.jokertwo.L;

/**
 * Created by Hzq on 2017/7/21.
 */
public class JsonUtil {

    public static JSONObject myJson;

    public static List<String> returnStuList(String stuJson){
        List<String> stulist = new List<String>() {
            @Override
            public void add(int location, String object) {

            }

            @Override
            public boolean add(String object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean equals(Object object) {
                return false;
            }

            @Override
            public String get(int location) {
                return null;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int location) {
                return null;
            }

            @Override
            public String remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public String set(int location, String object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<String> subList(int start, int end) {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }
        };

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
        L.e(String.valueOf(stuInformation.size()));



        return stulist;
    }

    public static String getJson(Context context){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
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

    public static List<String> getYourNeed(Context context) {
        String json = getJson(context);


        JSONObject group = JSON.parseObject(json);
        myJson = group;

        JSONObject stuInformation = group.getJSONObject("stuInformation");

        L.e(String.valueOf(stuInformation.size()));

        Set<String> level = stuInformation.keySet();

        List<String> level2 = new ArrayList<String>(level);

        return level2;
    }

    public static List<String> getClassFromLevel(String level){
        JSONObject stuInformation = myJson.getJSONObject("stuInformation");
        JSONObject levelJsonObj = stuInformation.getJSONObject(level);
        L.e(String.valueOf(levelJsonObj));

        Set<String> theClass = levelJsonObj.keySet();

        List<String> theClass2 = new ArrayList<String>(theClass);

        return theClass2;
    }

    public static List<String> getStuFromClass(String level,String theClass){
        // 应该是返回的一个adapter 通过level和theClass
        // 找到那个班级里面的所有人以及基本信息
        return null;
    }
}
