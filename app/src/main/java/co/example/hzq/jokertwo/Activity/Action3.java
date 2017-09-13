package co.example.hzq.jokertwo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.example.hzq.jokertwo.L;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.stuAdapter;
import co.example.hzq.jokertwo.student;

/**
 * Created by Hzq on 2017/7/21.
 */
public class Action3 extends AppCompatActivity implements OnMenuItemClickListener {
    private ContextMenuDialogFragment contextMenuDialogFragment;
    private android.support.v4.app.FragmentManager fragmentManager;

    private List<student> mData = null;
    private Context mContext;
    private stuAdapter mAdapter = null;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.aciton3);

        initContextMenuAndroid();

/*        List<Map<String,Object>> listitem = new ArrayList<Map<String, Object>>();
        for(int i = 0; i< names.length;i++){
            Map<String,Object> showitem = new HashMap<String,Object>();
            showitem.put("touxiang", Imgid[i]);
            showitem.put("name",names[i]);
            showitem.put("say",say[i]);
            listitem.add(showitem);
        }
        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(),listitem,R.layout.list_item,new String[]{"touxiang","name","say"},new int[]{R.id.imgtou,R.id.name,R.id.says});
        ListView listView = (ListView)findViewById(R.id.mylistView);
        listView.setAdapter(myAdapter);*/

        mContext = Action3.this;
        listView = (ListView)findViewById(R.id.mylistView);
        mData = new LinkedList<student>();
        mData.add(new student("狗说", "你是狗么?", R.drawable.icn_1));
        mData.add(new student("牛说", "你是牛么?", R.drawable.icn_1));
        mData.add(new student("鸭说", "你是鸭么?", R.drawable.icn_1));
        mData.add(new student("鱼说", "你是鱼么?", R.drawable.icn_1));
        mData.add(new student("马说", "你是马么?", R.drawable.icn_1));
        mData.add(new student("狗说", "你是狗么?", R.drawable.icn_1));
        mData.add(new student("牛说", "你是牛么?", R.drawable.icn_1));
        mData.add(new student("鸭说", "你是鸭么?", R.drawable.icn_1));
        mData.add(new student("鱼说", "你是鱼么?", R.drawable.icn_1));
        mData.add(new student("马说", "你是马么?", R.drawable.icn_1));
        mData.add(new student("狗说", "你是狗么?", R.drawable.icn_1));
        mData.add(new student("牛说", "你是牛么?", R.drawable.icn_1));
        mData.add(new student("鸭说", "你是鸭么?", R.drawable.icn_1));
        mData.add(new student("鱼说", "你是鱼么?", R.drawable.icn_1));
        mData.add(new student("马说", "你是马么?", R.drawable.icn_1));

        mAdapter = new stuAdapter((LinkedList<student>) mData,mContext);
        listView.setAdapter(mAdapter);
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

}
