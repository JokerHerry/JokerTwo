package co.example.hzq.jokertwo.Activity;

import android.app.Application;

/**
 * Created by Hzq on 2017/9/15.
 */

public class ContextUtil extends Application {
    private static final String TAG = "ContextUtil";
    private static ContextUtil instance;

    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
