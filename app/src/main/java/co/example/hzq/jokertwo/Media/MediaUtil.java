package co.example.hzq.jokertwo.Media;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import co.example.hzq.jokertwo.Activity.BaseActivity;
import co.example.hzq.jokertwo.Activity.ContextUtil;
import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/9/13.
 */

public class MediaUtil {
    private static final String TAG = "MediaUtil";
    private static File currentImageFile;

    //使用摄像机
    public static void useCamera(BaseActivity context){
        //配置存放照片的文件夹
        File dir = new File(ContextUtil.getInstance().getFilesDir(),"pictures");

        if(!dir.exists()){
            dir.mkdirs();
            chmod777(dir);
        }


        currentImageFile = new File(dir,"file.jpg");
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
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        //通过FileProvider创建一个content类型的Uri
        Uri imageUri = FileProvider.getUriForFile(context, "co.example.hzq.jokertwo.fileprovider", currentImageFile);
        it.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        context.startActivityForResult(it,Activity.DEFAULT_KEYS_DIALER);
    }

    public static void useCamera(Handler handler) {
        //配置存放照片的文件夹
        File dir = new File(ContextUtil.getInstance().getFilesDir(),"pictures");
        if(!dir.exists()){
            dir.mkdirs();
            chmod777(dir);
        }

        //currentImageFile = new File(dir,System.currentTimeMillis() + ".jpg");
        currentImageFile = new File(dir,"file.jpg");
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


        ContextUtil.getInstance().startActivity(it);
    }

    //使用相册
    public static void usePhoto(Activity context){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, context.getResources().getInteger(R.integer.toPhoto));
    }


    //设置777权限
    public static void chmod777(File file){
        try {
            Runtime.getRuntime().exec("chmod 777 "+file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "use_camera: "+"文件夹权限赋予失败" );
        }
    }

}
