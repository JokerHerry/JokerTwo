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

        for(int i=0;i<10;i++){
            IndexItem bili1 = new IndexItem(R.drawable.pic1,"数字媒体技术基础","2014级5班","上课时间：周一   18：00-21：00");
            IndexItem bili2 = new IndexItem(R.drawable.pic2,"计算机导论","2014级5班","上课时间：周一   8：00-11：00");
            IndexItem bili3 = new IndexItem(R.drawable.pic3,"游戏架构","2014级5班","上课时间：周一   14：00-17：00");
            indexItemList.add(bili1);
            indexItemList.add(bili2);
            indexItemList.add(bili3);
        }

    }
}





































