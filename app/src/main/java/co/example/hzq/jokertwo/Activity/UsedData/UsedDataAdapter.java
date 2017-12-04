package co.example.hzq.jokertwo.Activity.UsedData;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.Activity.ContextUtil;
import co.example.hzq.jokertwo.List.StuItem;
import co.example.hzq.jokertwo.List.StuItemAdapter;
import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/11/3.
 */

public class UsedDataAdapter extends RecyclerView.Adapter<UsedDataAdapter.ViewHolder> {
    private static final String TAG = "UsedDataAdapter";

    public UsedDataAdapter(){
    }

    private View mView;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_useddata_view,parent,false);
        final ViewHolder holder = new ViewHolder(mView);

        //设置点击事件
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String[] titles = {"已到","未到"};
        holder.tab_title.setTabData(titles);
        View view1 = mView.inflate(ContextUtil.getInstance(),R.layout.recycler_view, null);
        View view2 = mView.inflate(ContextUtil.getInstance(),R.layout.recycler_view, null);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextUtil.getInstance());
        ((RecyclerView)view1).setLayoutManager(layoutManager);
        List<StuItem> stuItemList = new ArrayList<StuItem>();
        stuItemList.add(new StuItem("1","haozhiqiang"));
        stuItemList.add(new StuItem("2","haozhiqiang"));
        StuItemAdapter adapter = new StuItemAdapter(stuItemList);
        ((RecyclerView)view1).setAdapter(adapter);

        List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);

        holder.tab_body.setAdapter(new MyViewPagerAdapter(viewList));

        holder.tab_title.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                holder.tab_body.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        holder.tab_body.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                holder.tab_title.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: " );
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SegmentTabLayout tab_title;
        ViewPager tab_body;

        public ViewHolder(View itemView) {
            super(itemView);
            tab_title = (SegmentTabLayout) itemView.findViewById(R.id.tab_title);
            tab_body = (ViewPager) itemView.findViewById(R.id.tab_body);
        }
    }

    private class MyViewPagerAdapter extends PagerAdapter {
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
