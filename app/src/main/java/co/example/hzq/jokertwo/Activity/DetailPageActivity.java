package co.example.hzq.jokertwo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import co.example.hzq.jokertwo.HttpUtil.HttpUtil;
import co.example.hzq.jokertwo.List.StuItem;
import co.example.hzq.jokertwo.List.StuItemAdapter;
import co.example.hzq.jokertwo.NormalProgress;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.json.JsonUtil;

public class DetailPageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StuItemAdapter stuItemAdapter;
    List<StuItem> stuItemList;


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


        ImageButton bili1 = (ImageButton)findViewById(R.id.pink_icon);
        bili1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stuItemList.get(0).setName("hzq");
                stuItemList.get(0).setCheckBox(true);
                stuItemAdapter.notifyItemChanged(0);
            }
        });

        ImageButton bili2 = (ImageButton)findViewById(R.id.actionButton2);
        final String filePath = getFilesDir().toString()+"/pictures/file.jpg";
        final String url = "http://192.168.43.175:8000/upload";
        final String url2 = "http://139.199.202.227:8000/upload";
        bili2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.upLoadFile(url2,filePath);
            }
        });



        ImageButton bili3 = (ImageButton)findViewById(R.id.actionButton3);
        final String[] bilitoken = new String[1];
        bili3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalProgress.useCamera(DetailPageActivity.this,handler);
            }
        });

        //接收到班级之后，找到班级中的人
        initStuList();
    }

    private void initStuList() {
        recyclerView = (RecyclerView) findViewById(R.id.stu_recyclerView);

        List<Map<String, String>> stuData = JsonUtil.getStuFromClass("2017", "1");

        stuItemList = new ArrayList<StuItem>();
        for(Map<String,String> item : stuData){
            //Log.e(TAG, "initStuList: " );
            String id = item.get("id");
            String name = item.get("name");

            StuItem stuItem = new StuItem(id,name);
            stuItemList.add(stuItem);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        stuItemAdapter = new StuItemAdapter(stuItemList);
        recyclerView.setAdapter(stuItemAdapter);
    }

    /**
     * 参数position就是 face++返回的
     * @param position
     */
    private void changeUI(int position){
        stuItemList.get(position).setCheckBox(true);
        stuItemAdapter.notifyItemChanged(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Activity.DEFAULT_KEYS_DIALER:
                Log.e(TAG, "onActivityResult: "+ resultCode );

                /**
                 * resultCode
                 * -1 成功
                 * 0  取消
                 */
                if(resultCode == -1){
                    NormalProgress.handler.sendEmptyMessage(998);
                }else if(resultCode == 0 ){
                    Log.e(TAG, "拍照取消" );
                }
                break;
        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 997:
                    Bundle data = msg.getData();
                    String user_id = (String) data.get("name");
                    if(user_id.equals("hzq")){
                        changeUI(0);
                    }else if(user_id.equals("lxy")){
                        changeUI(1);
                    }
                    break;
                case 123:
                    Log.e(TAG, "handleMessage: "+"test what" );
            }
        }
    };
}
