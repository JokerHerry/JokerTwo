package co.example.hzq.jokertwo.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.R;

public class Main2Activity extends AppCompatActivity {

    private ViewPager viewPager;

    private static final String TAG = "Main2Activity";

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles_3 = {"已到","未到"};
    private View mDecorView;
    private SegmentTabLayout mTabLayout_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTabLayout_3 = (SegmentTabLayout) findViewById(R.id.tab_title);
        viewPager = (ViewPager) findViewById(R.id.tab_body);
        tl_3();
    }

    private void tl_3() {
        mTabLayout_3.setTabData(mTitles_3);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                Log.e(TAG, "onTabReselect: "+ position );
            }
        });

        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 =  lf.inflate(R.layout.the_main_list_item, null);
        View view2 = lf.inflate(R.layout.the_main_list_item, null);

        List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(viewList);

        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;
        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }
        //直接继承PagerAdapter，至少必须重写下面的四个方法，否则会报错
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)     {
            container.removeView(mListViews.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }
        @Override
        public int getCount() {
            return  mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;//官方提示这样写
        }
    }

}

