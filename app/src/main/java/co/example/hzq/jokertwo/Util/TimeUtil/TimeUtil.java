package co.example.hzq.jokertwo.Util.TimeUtil;

/**
 * Created by Hzq on 2017/11/21.
 */

public class TimeUtil {
    public static void waitFunc(int waitTime, final TimeCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.OnTime();
            }
        }).start();
    }
}
