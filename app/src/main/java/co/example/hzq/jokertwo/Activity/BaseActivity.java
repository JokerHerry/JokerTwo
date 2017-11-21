package co.example.hzq.jokertwo.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Hzq on 2017/10/18.
 */

public class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    private final int PERMISSION = 1;

    private ProgressDialog mProgressDialog;


    //Handler的what值
    //dialog
    private final int STOP_DIALOG = 1;
    private final int CHANGE_DIALOG = 2;
    private final int START_DIALOG = 3;
    //toast
    private final int LONG_TOAST = 11;
    private final int SHORT_TOAST = 12;


    //生命周期
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    //动态添加权限
    public void getPermission(@NonNull Context context, @NonNull String permission){
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(context,
                    permission)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission},PERMISSION);
            }else{
                Log.e(TAG, "getPermission: 该权限已经申请，无需继续申请" );
            }
        }else{
            Log.e(TAG, "getPermission: 不需要申请 "+permission+" 权限" );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.e(TAG, "onRequestPermissionsResult: 权限申请成功" );
                }else{
                    Log.e(TAG, "onRequestPermissionsResult: 权限申请失败" );
                }
                break;
            default:
                break;
        }
    }


    //简便Toast方法
    public void shortToast(String text){
        Message message = new Message();
        message.what = SHORT_TOAST;
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    public void longToast(String text){
        Message message = new Message();
        message.what = LONG_TOAST;
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    //简便Dialog
    public void showDialog(String str) {
    /* 等待Dialog具有屏蔽其他控件的交互能力
     * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
     * 下载等事件完成后，主动调用函数关闭该Dialog
     */
        Message message = new Message();
        message.what = START_DIALOG;
        Bundle bundle = new Bundle();
        bundle.putString("str",str);
        message.setData(bundle);
        handler.sendMessage(message);

    }
    public void stopDialog() {
        handler.sendEmptyMessage(STOP_DIALOG);
    }
    public void changeDialogMessage(String msg){
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STOP_DIALOG:
                    mProgressDialog.dismiss();
                    break;
                case START_DIALOG:
                    Bundle data = msg.getData();
                    String str = data.getString("str");

                    if (mProgressDialog!=null){
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }

                    mProgressDialog =
                            new ProgressDialog(BaseActivity.this);
                    mProgressDialog.setTitle("请稍后. . . ");
                    mProgressDialog.setMessage(str);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                    break;
                case CHANGE_DIALOG:
                    break;
                case LONG_TOAST:
                    Toast.makeText(BaseActivity.this,msg.getData().getString("text"),Toast.LENGTH_LONG).show();
                    break;
                case SHORT_TOAST:
                    Toast.makeText(BaseActivity.this,msg.getData().getString("text"),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
