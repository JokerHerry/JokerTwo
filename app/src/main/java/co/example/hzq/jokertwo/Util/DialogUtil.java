package co.example.hzq.jokertwo.Util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Hzq on 2017/11/29.
 */

public class DialogUtil {

    private static DialogUtil instance = new DialogUtil();

    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder alterDialog;

    private Context context;

    public static DialogUtil getIntance(){
        return  instance;
    }

    public DialogUtil normalDialog(Context context){

        return this;
    }

    public DialogUtil waitDialog(Context context){
        if (mProgressDialog==null){
            mProgressDialog = new ProgressDialog(context);
        }
        mProgressDialog.setTitle("请稍后. . . ");
        mProgressDialog.setMessage("等待中");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        return this;
    }

    public DialogUtil title(String title){
        mProgressDialog.setTitle(title);
        return this;
    }

    public DialogUtil message(String message){
        mProgressDialog.setMessage(message);
        return this;
    }

    public void show(){
        mProgressDialog.show();
    }

    public void closeDialog(){
        if (mProgressDialog!=null){
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

}
