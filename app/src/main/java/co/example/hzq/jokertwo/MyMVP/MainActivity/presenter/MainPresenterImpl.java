package co.example.hzq.jokertwo.MyMVP.MainActivity.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import co.example.hzq.jokertwo.List.ClassItem;
import co.example.hzq.jokertwo.MyMVP.MainActivity.model.MainModel;
import co.example.hzq.jokertwo.MyMVP.MainActivity.model.MainModelImpl;
import co.example.hzq.jokertwo.MyMVP.MainActivity.view.MainView;
import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/11/30.
 */

public class MainPresenterImpl implements MainPresenter{

    private List<ClassItem> classItemList = new ArrayList<>();

    Context context;
    MainView view;
    MainModel model;

    public MainPresenterImpl(MainView view, Context context) {
        this.view = view;
        this.context = context;
        model = new MainModelImpl();

        initData();

    }


    @Override
    public void requestClass() {
        view.returnClassInfos(classItemList);
    }


    private void initData() {
        ClassItem bili1 = new ClassItem(R.drawable.pic1, "2014级2班", "数字媒体技术基础", "上课时间：周一   18：00-21：00");
        ClassItem bili2 = new ClassItem(R.drawable.pic2, "2014级1班", "计算机导论", "上课时间：周一   8：00-11：00");
        ClassItem bili3 = new ClassItem(R.drawable.pic3, "2015级3班", "游戏架构", "上课时间：周一   14：00-17：00");
        ClassItem bili4 = new ClassItem(R.drawable.pic5, "2017级1班", "上什么课啊 不上课", "上课时间：没有课了好吗？");
        classItemList.add(bili1);
        classItemList.add(bili2);
        classItemList.add(bili3);
        classItemList.add(bili4);
    }

}
