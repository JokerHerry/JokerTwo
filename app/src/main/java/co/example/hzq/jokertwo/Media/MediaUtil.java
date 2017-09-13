package co.example.hzq.jokertwo.Media;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/9/13.
 */

public class MediaUtil {
    private static final String TAG = "MediaUtil";
    static File currentImageFile;

    //使用摄像机
    public static void useCamera(Activity context){
        //配置存放照片的文件夹
        File dir = new File(context.getFilesDir(),"pictures");
        if(!dir.exists()){
            dir.mkdirs();
            chmod777(dir);
        }

        currentImageFile = new File(dir,System.currentTimeMillis() + ".jpg");
        if(!currentImageFile.exists()){
            try {
                currentImageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        chmod777(currentImageFile);

        //保存图片并返回
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivityForResult(it,context.getResources().getInteger((R.integer.toCamere)));
    }

    public static void usePhoto(Activity context){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, context.getResources().getInteger(R.integer.toPhoto));
    }


    //设置777权限
    private static void chmod777(File file){
        try {
            Runtime.getRuntime().exec("chmod 777 "+file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "use_camera: "+"文件夹权限赋予失败" );
        }
    }
}
