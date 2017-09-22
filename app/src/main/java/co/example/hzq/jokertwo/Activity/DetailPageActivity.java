package co.example.hzq.jokertwo.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import co.example.hzq.jokertwo.NormalProgress;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.json.JsonUtil;

public class DetailPageActivity extends AppCompatActivity {
    /**
     *student的list变量
     */
    RecyclerView recyclerView;
    StuItemAdapter stuItemAdapter;
    List<StuItem> stuItemList;

    /**
     * 初始化的三个变量
     */
    String clazz;
    String time;
    String course;

    /**
     *是否点击过拍照，没有的话  返回键直接返回，
     * 否则的话  会有dialog询问
     */
    boolean if_change = false;

    private static final String TAG = "DetailPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        initData();
    }

    private void initData() {
        if_change = false;
        clazz = getIntent().getStringExtra("class");
        course = getIntent().getStringExtra("course");
        time = getIntent().getStringExtra("time");


        ImageView detail_imageView = (ImageView) findViewById(R.id.detail_imageView);
        detail_imageView.setImageResource(Integer.valueOf(getIntent().getStringExtra("image")));

        TextView detail_text_class = (TextView) findViewById(R.id.detail_text_class);
        TextView detail_text_course = (TextView) findViewById(R.id.detail_text_course);
        TextView detail_text_time = (TextView) findViewById(R.id.detail_text_time);

        detail_text_course.setText(course);
        detail_text_class.setText(clazz);
        detail_text_time.setText(time);



        /**
         * 这是同步数据的按钮
         */
        ImageButton bili2 = (ImageButton)findViewById(R.id.actionButton2);
        bili2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+ "这是同步数据的按钮");
            }
        });


        /**
         *这是normalProgress的按钮
         */
        ImageButton bili3 = (ImageButton)findViewById(R.id.actionButton3);
        bili3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if_change = true;
                NormalProgress.useCamera(DetailPageActivity.this,handler);
            }
        });

        /**
         * 接收到班级之后，找到班级中的人
         */
        initStuList();
    }

    private void initStuList() {
        recyclerView = (RecyclerView) findViewById(R.id.stu_recyclerView);
        String[] level_class = level_class(clazz);
        /**
         * 从本地的json数据中找到需要的班级的人员信息
         */
        List<Map<String, String>> stuData = JsonUtil.getStuFromClass(level_class[0], level_class[1]);

        stuItemList = new ArrayList<StuItem>();
        for(Map<String,String> item : stuData){
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
     *
     * @param str (class)   eg.2014级1班
     * @return String[]     eg.{2014,1}
     */
    private String[] level_class(String str){
        String[] split = str.split("级|班");
        return split;
    }

    /**
     * 参数position就是 face++返回的user_id
     * @param position
     */
    private void changeUI(int position){
        stuItemList.get(position).setCheckBox(true);
        stuItemAdapter.notifyItemChanged(position);
    }

    /**
     * @param requestCode   对应的执行函数
     * @param resultCode    返回的状态码
     * @param data          携带的参数
     */
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

    /**
     * 重写返回键  返回之前先要询问是否已经保存，采用dialog
     */
    @Override
    public void onBackPressed() {
        if(if_change){
            AlertDialog alert = null;
            AlertDialog.Builder builder = null;
            builder = new AlertDialog.Builder(this);
            alert = builder
                    .setTitle("系统提示：")
                    .setMessage("退出前，请同步数据。退出后，本次点到记录将会清除。\n请问是否需要退出？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DetailPageActivity.super.onBackPressed();
                        }
                    })
                    .create();             //创建AlertDialog对象
            alert.show();                    //显示对话框
        }else {
            super.onBackPressed();
        }

    }

    /**
     * 涉及到相机 999
     * 涉及到相册 998
     * 涉及到list 997
     *通过face++返回的user_id，找到需要改变状态的学生
     */
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
