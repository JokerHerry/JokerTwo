package co.example.hzq.jokertwo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.List.IndexItem;
import co.example.hzq.jokertwo.List.IndexItemAdapter;
import co.example.hzq.jokertwo.R;

public class test2Activity extends AppCompatActivity {

    private static final String TAG = "test2Activity";
    private List<IndexItem> indexItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        initData();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        IndexItemAdapter adapter = new IndexItemAdapter(indexItemList);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {

        IndexItem bili1 = new IndexItem(R.drawable.pic1,"2014级2班","数字媒体技术基础","上课时间：周一   18：00-21：00");
        IndexItem bili2 = new IndexItem(R.drawable.pic2,"2014级1班","计算机导论","上课时间：周一   8：00-11：00");
        IndexItem bili3 = new IndexItem(R.drawable.pic3,"2015级3班","游戏架构","上课时间：周一   14：00-17：00");
        IndexItem bili4 = new IndexItem(R.drawable.pic4,"2017级1班","上什么课啊 不上课","上课时间：没有课了好吗？");
        indexItemList.add(bili1);
        indexItemList.add(bili2);
        indexItemList.add(bili3);
        indexItemList.add(bili4);

    }
}





































