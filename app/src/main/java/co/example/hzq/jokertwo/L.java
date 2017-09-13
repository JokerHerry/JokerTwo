package co.example.hzq.jokertwo;

import android.util.Log;

/**
 * Created by Hzq on 2017/7/21.
 */
public class L {
    private static final String TAG = "Hzq";
    private static boolean debug = true;

    public static void e(String msg){
        if(debug){
            Log.e(TAG, msg);
        }
    }
}
