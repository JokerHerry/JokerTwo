package co.example.hzq.jokertwo.Activity;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.MyMVP.LoginActivity.ui.LoginActivity;

/**
 * Created by Hzq on 2017/10/24.
 */

//单例模式  饿汉式
public class ActivityCollector {

    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    //清空所有的Activity
    public static void finishAll(){
        for(Activity activity:activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
            Intent intent = new Intent(ContextUtil.getInstance(),LoginActivity.class);
            ContextUtil.getInstance().startActivity(intent);
        }
    }
}
