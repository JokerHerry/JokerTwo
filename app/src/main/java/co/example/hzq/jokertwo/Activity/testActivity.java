package co.example.hzq.jokertwo.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.io.IOException;
import java.util.ArrayList;

import co.example.hzq.jokertwo.HttpUtil.HttpUtil;
import co.example.hzq.jokertwo.L;
import co.example.hzq.jokertwo.Media.MediaUtil;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.facePlusPlus.faceApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Hzq on 2017/7/21.
 */
public class testActivity extends AppCompatActivity implements OnMenuItemClickListener  {
    private static final String TAG = "testActivity";

    public static String faceToken = "8dc75300904aad0ade6c0df7cffd6cc1";
    public static String facesetToken = "ae0df34de5f0ca82c8712c797644e132";
    public static String faceToken2 = "dbb610134063e096915c17d7e6c9f15f";       //hzq

    private ContextMenuDialogFragment contextMenuDialogFragment;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.test_main);

        initContextMenuAndroid();

        MyMethod();
    }

    private void MyMethod() {
        final String filePath = getFilesDir().toString()+"/file.jpg";
        final String url = "http://192.168.43.175:8000/upload";

        Button httpBtn = (Button) findViewById(R.id.btn1);
        httpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HttpUtil.get("http://192.168.43.175:8000/upload");
                HttpUtil.upLoadFile(url,filePath);
            }
        });

        Button camera = (Button)findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtil.useCamera(testActivity.this);
            }
        });

        Button photo = (Button)findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaUtil.usePhoto(testActivity.this);
            }
        });

        Button detect = (Button)findViewById(R.id.detect);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://139.199.202.227:8000/static/img/doge2.jpg";
                faceApi.detectFace(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
        });



        Button compare = (Button)findViewById(R.id.compare);
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505295662935&di=a5462000110f3a1be0a1334ee6b4d0e2&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20160301%2Fmp61268485_1456824929490_9.jpg";
                String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1505288455&di=7cab9e918cb7841ff68feead1b457d8b&src=http://i0.sinaimg.cn/ent/y/2010-05-27/U996P28T3D2971053F326DT20100527155240.jpg";
                faceApi.compareFace(url1,url2);
            }
        });

        Button search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceApi.searchFace(faceToken2, facesetToken, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
        });

        Button create = (Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceApi.faceset_createFace("2014_5");
            }
        });

        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceApi.faceset_addfaceFace(facesetToken,faceToken,"lxy");
            }
        });

        Button remove = (Button)findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceApi.faceset_removefaceFace(facesetToken,faceToken);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 998:
                Log.e(TAG, "onActivityResult: " );
                break;
            case 999:
                Uri uri = data.getData();
                String path = uri.getPath();
                Log.e(TAG, "onActivityResult: "+ path );

                break;
            default :
                break;
        }
    }

    private void initContextMenuAndroid() {
        ArrayList<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject icn1 = new MenuObject("icn1");
        icn1.setResource(R.drawable.icn_1);

        MenuObject icn2 = new MenuObject("icn2");
        icn2.setResource(R.drawable.icn_2);

        MenuObject icn3 = new MenuObject("icn3");
        icn3.setResource(R.drawable.icn_3);

        menuObjects.add(close);
        menuObjects.add(icn1);
        menuObjects.add(icn2);
        menuObjects.add(icn3);

        //1	创建菜单组件
        MenuParams menuParams = new MenuParams();
        //2	将子项组件加入
        menuParams.setMenuObjects(menuObjects);
        //3	点击空白地区可以取消关闭菜单
        menuParams.setClosableOutside(true);
        //4	设置每个菜单子项的大小
        menuParams.setActionBarSize(200);

        fragmentManager = getSupportFragmentManager();
        //设置实例化
        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        //打开OnClick方法
        contextMenuDialogFragment.setItemClickListener(this);
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position){
            case 1:
                L.e("Position 1");
                Intent intent1 = new Intent(testActivity.this,Action1.class);
                startActivity(intent1);
                break;
            case 2:
                L.e("Position 2");
                Intent intent2 = new Intent(testActivity.this,Action2.class);
                startActivity(intent2);
                break;
            case 3:
                L.e("Position 3");
                Intent intent3 = new Intent(testActivity.this,Action3.class);
                startActivity(intent3);
                break;
            case 4:
                L.e("Position 4");
                Intent intent4 = new Intent(testActivity.this,Action4.class);
                startActivity(intent4);
                break;
            default:
                L.e("Position default");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                contextMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
