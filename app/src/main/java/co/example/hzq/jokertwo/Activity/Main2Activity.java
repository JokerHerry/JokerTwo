package co.example.hzq.jokertwo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import co.example.hzq.jokertwo.Media.MediaUtil;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.ui.LoginActivity;
import co.example.hzq.jokertwo.R;

public class Main2Activity extends BaseActivity {

    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button testTest = (Button) findViewById(R.id.testTest);
        testTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaUtil.useCamera(Main2Activity.this);
            }
        });

        Button testPhoto = (Button)findViewById(R.id.testPhoto);
        testPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaUtil.usePhoto(Main2Activity.this);
            }
        });


        Button app = (Button)findViewById(R.id.testJoker);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Activity.DEFAULT_KEYS_DIALER:
                Intent intent2 = new Intent(Main2Activity.this,ImageDisplayActivity.class);
                startActivity(intent2);
                break;
            case 999:
                if(resultCode == -1){
                    Log.e(TAG, "相册点击");
                    final Uri data1 = data.getData();
                    Intent intent = new Intent(Main2Activity.this,ImageDisplayActivity.class);
                    intent.setData(data1);
                    startActivity(intent);
                }else{
                    Log.e(TAG, "相册取消" );
                }
                break;
            default:
                break;
        }
    }

}

