package co.example.hzq.jokertwo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.L;
import co.example.hzq.jokertwo.R;

import static co.example.hzq.jokertwo.json.JsonUtil.getClassFromLevel;
import static co.example.hzq.jokertwo.json.JsonUtil.getYourNeed;

/**
 * Created by Hzq on 2017/7/21.
 */
public class Action2 extends AppCompatActivity implements OnMenuItemClickListener, MaterialSpinner.OnItemSelectedListener, View.OnClickListener {
    private ContextMenuDialogFragment contextMenuDialogFragment;
    private android.support.v4.app.FragmentManager fragmentManager;

    //private Button bili;
    MaterialSpinner spinnerLevel;
    MaterialSpinner spinnerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.aciton2);
        initUi();
    }

    private void initUi() {
        initContextMenuAndroid();
        setSpinner();

        //要显示的数据
        String[] strs = {"基神","B神","翔神","曹神","J神","基神","B神","翔神","曹神","J神","基神","B神","翔神","曹神","J神","基神","B神","翔神","曹神","J神"};
        //创建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_expandable_list_item_1,strs);
        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
        ListView list_test = (ListView) findViewById(R.id.stuList);
        list_test.setAdapter(adapter);
    }

    private void setSpinner() {
        spinnerLevel = (MaterialSpinner) findViewById(R.id.spinnerLevel);
        spinnerLevel.setOnItemSelectedListener(this);

        spinnerClass = (MaterialSpinner) findViewById(R.id.spinnerClass);
        spinnerClass.setItems("default");
        spinnerClass.setOnItemSelectedListener(this);

        List<String> bili = getYourNeed(getApplicationContext());
        spinnerLevel.setItems(bili);
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
                break;
            case 2:
                L.e("Position 2");
                break;
            case 3:
                L.e("Position 3");
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


    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (view.getId()){
            case R.id.spinnerLevel:
                //L.e("spinnerLevel  "+position);
                List<String> theClass = getClassFromLevel(item.toString());
                spinnerClass.setItems(theClass);
                break;
            case R.id.spinnerClass:
                //L.e("spinnerClass  " + position);
                break;
            default:
                L.e("no id");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            default:
                L.e("json default");
                break;
        }
    }
}
