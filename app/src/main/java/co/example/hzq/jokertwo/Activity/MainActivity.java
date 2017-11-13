package co.example.hzq.jokertwo.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.ERcyclerView.BaseViewHolder;
import co.example.hzq.jokertwo.ERcyclerView.ERecyclerAdapter;
import co.example.hzq.jokertwo.List.IndexItem;
import co.example.hzq.jokertwo.R;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private List<IndexItem> indexItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        setRecyclerView();
    }

    private void initData() {

        IndexItem bili1 = new IndexItem(R.drawable.pic1,"2014级2班","数字媒体技术基础","上课时间：周一   18：00-21：00");
        IndexItem bili2 = new IndexItem(R.drawable.pic2,"2014级1班","计算机导论","上课时间：周一   8：00-11：00");
        IndexItem bili3 = new IndexItem(R.drawable.pic3,"2015级3班","游戏架构","上课时间：周一   14：00-17：00");
        IndexItem bili4 = new IndexItem(R.drawable.pic5,"2017级1班","上什么课啊 不上课","上课时间：没有课了好吗？");
        indexItemList.add(bili1);
        indexItemList.add(bili2);
        indexItemList.add(bili3);
        indexItemList.add(bili4);

    }

    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ERecyclerAdapter<IndexItem> eRecyclerAdapter = new ERecyclerAdapter<IndexItem>(indexItemList, R.layout.the_main_list_item, this) {
            @Override
            protected void setParams(BaseViewHolder holder, IndexItem indexItem, int position) {
                holder.setImageResource(R.id.item_imageView,indexItem.getItem_imageView());
                holder.setText(R.id.item_text_class,indexItem.getItem_text_class());
                holder.setText(R.id.item_text_course,indexItem.getItem_text_course());
                holder.setText(R.id.item_text_time,indexItem.getItem_text_time());
            }
        };
        eRecyclerAdapter.setOnClickListener(new ERecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                Intent intent = new Intent(MainActivity.this,DetailPageActivity.class);
                intent.putExtra("image",indexItemList.get(position).getItem_imageView());
                intent.putExtra("class",indexItemList.get(position).getItem_text_class());
                intent.putExtra("course",indexItemList.get(position).getItem_text_course());
                intent.putExtra("time",indexItemList.get(position).getItem_text_time());
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,holder.getView(R.id.item_imageView),"image").toBundle());
            }
        });
        recyclerView.setAdapter(eRecyclerAdapter);
    }
}





































