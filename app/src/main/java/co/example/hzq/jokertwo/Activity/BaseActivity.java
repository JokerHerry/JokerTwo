package co.example.hzq.jokertwo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.example.hzq.jokertwo.Util.DialogUtil;

/**
 * Created by Hzq on 2017/10/18.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    private final int PERMISSION = 1;

    //Handler的what值
    //dialog
    private final int STOP_DIALOG = 1;
    private final int CHANGE_DIALOG = 2;
    private final int START_DIALOG = 3;
    //toast
    private final int LONG_TOAST = 11;
    private final int SHORT_TOAST = 12;

    public Context mContext;
    public Unbinder unbinder;

    //生命周期  实现强行下线
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(getLayoutId());
        mContext = this;
        unbinder = ButterKnife.bind(this);
        this.initView(savedInstanceState);

        try {
            FileOutputStream fileOutputStream = openFileOutput("bili.txt", Context.MODE_APPEND);
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            write.write("bilibili");
            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //简便开启活动
    public void startAC(Class<?> cls){
        startToActivity(cls,null);
    }
    public void startAC(Class<?> cls,Bundle bundle){
        startToActivity(cls,bundle);
    }
    private void startToActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        if (bundle!=null){
            startActivity(intent,bundle);
        }else{
            startActivity(intent);
        }
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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_DIALOG:
                    DialogUtil.getIntance().closeDialog();
                    break;
                case START_DIALOG:
                    DialogUtil.getIntance().waitDialog(BaseActivity.this).show();
                    break;
                case CHANGE_DIALOG:
                    break;
                case LONG_TOAST:
                    Toast.makeText(BaseActivity.this, msg.getData().getString("text"), Toast.LENGTH_LONG).show();
                    break;
                case SHORT_TOAST:
                    Toast.makeText(BaseActivity.this, msg.getData().getString("text"), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
