package co.example.hzq.jokertwo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.example.hzq.jokertwo.List.StuItem;
import co.example.hzq.jokertwo.List.StuItemAdapter;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.json.JsonUtil;

public class DetailPageActivity extends AppCompatActivity {

    private static final String TAG = "DetailPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        initData();
    }

    private void initData() {
        ImageView detail_imageView = (ImageView) findViewById(R.id.detail_imageView);
        detail_imageView.setImageResource(Integer.valueOf(getIntent().getStringExtra("image")));

        TextView detail_text_class = (TextView) findViewById(R.id.detail_text_class);
        TextView detail_text_course = (TextView) findViewById(R.id.detail_text_course);
        TextView detail_text_time = (TextView) findViewById(R.id.detail_text_time);

        detail_text_course.setText(getIntent().getStringExtra("course"));
        detail_text_class.setText(getIntent().getStringExtra("class"));
        detail_text_time.setText(getIntent().getStringExtra("time"));


        ImageButton bili = (ImageButton)findViewById(R.id.pink_icon);
        bili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
                JsonUtil.getStuFromClass("2014","2班");
            }
        });


        //接收到班级之后，找到班级中的人
        initStuList();

    }

    private void initStuList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stu_recyclerView);

        List<Map<String, String>> stuData = JsonUtil.getStuFromClass("2014", "2班");

        List<StuItem> stuItemList =new ArrayList<StuItem>();
        for(Map<String,String> item : stuData){
            Log.e(TAG, "initStuList: " );
            String id = item.get("id");
            String name = item.get("name");

            StuItem stuItem = new StuItem(Integer.valueOf(id),name);
            stuItemList.add(stuItem);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StuItemAdapter adapter = new StuItemAdapter(stuItemList);
        recyclerView.setAdapter(adapter);
    }
}
