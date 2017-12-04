package co.example.hzq.jokertwo.MyMVP.MainActivity.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import co.example.hzq.jokertwo.Activity.ActivityCollector;
import co.example.hzq.jokertwo.Activity.BaseActivity;
import co.example.hzq.jokertwo.ERcyclerView.BaseViewHolder;
import co.example.hzq.jokertwo.ERcyclerView.ERecyclerAdapter;
import co.example.hzq.jokertwo.List.ClassItem;
import co.example.hzq.jokertwo.MyMVP.DetailActivity.DetailPageActivity;
import co.example.hzq.jokertwo.MyMVP.MainActivity.presenter.MainPresenter;
import co.example.hzq.jokertwo.MyMVP.MainActivity.presenter.MainPresenterImpl;
import co.example.hzq.jokertwo.MyMVP.MainActivity.view.MainView;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.Util.DensityUtils;
import co.example.hzq.jokertwo.Util.SPUtils;
import co.example.hzq.jokertwo.widget.ClassicRefreshHeaderView;

public class MainActivity extends BaseActivity implements MainView, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.recyclerView)
    IRecyclerView iRecyclerView;
    ERecyclerAdapter<ClassItem> eRecyclerAdapter;

    private List<ClassItem> classItemList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    MainPresenter mPresenter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter = new MainPresenterImpl(this, this);
        mPresenter.requestClass();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //利用ErecyclerAdapter建立adapter
    private void setRecyclerView() {

        iRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerAdapter = new ERecyclerAdapter<ClassItem>(classItemList, R.layout.the_main_list_item, this) {
            @Override
            protected void setParams(BaseViewHolder holder, ClassItem indexItem, int position) {
                holder.setImageResource(R.id.item_imageView, indexItem.getItem_imageView());
                holder.setText(R.id.item_text_class, indexItem.getItem_text_class());
                holder.setText(R.id.item_text_course, indexItem.getItem_text_course());
                holder.setText(R.id.item_text_time, indexItem.getItem_text_time());
            }
        };
        eRecyclerAdapter.setOnClickListener(new ERecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                Intent intent = new Intent(MainActivity.this, DetailPageActivity.class);
                intent.putExtra("image", classItemList.get(position).getItem_imageView());
                intent.putExtra("class", classItemList.get(position).getItem_text_class());
                intent.putExtra("course", classItemList.get(position).getItem_text_course());
                intent.putExtra("time", classItemList.get(position).getItem_text_time());
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, holder.getView(R.id.item_imageView), "image").toBundle());
            }
        });

        iRecyclerView.setOnRefreshListener(this);

        iRecyclerView.setRefreshEnabled(true);
        ClassicRefreshHeaderView classicRefreshHeaderView = new ClassicRefreshHeaderView(this);
        classicRefreshHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(this, 80)));
        iRecyclerView.setRefreshHeaderView(classicRefreshHeaderView);

        iRecyclerView.setIAdapter(eRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                SPUtils.setSharedBooleanData(null,"login",false);
                ActivityCollector.finishAll();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void returnClassInfos(List<ClassItem> classInfos) {
        //每次拿到数据就会刷新一次页面
        classItemList = classInfos;
        if (eRecyclerAdapter == null) {
            setRecyclerView();
        } else {
            eRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(false);
                shortToast("刷新");
            }
        },500);
    }

    @Override
    public void onLoadMore() {
        shortToast("加载更多");
    }

}





































