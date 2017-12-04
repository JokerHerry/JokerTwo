package co.example.hzq.jokertwo.MyMVP.MainActivity.view;

import java.util.List;

import co.example.hzq.jokertwo.List.ClassItem;

/**
 * Created by Hzq on 2017/11/29.
 */

public interface MainView {

    void refreshView();
    void returnClassInfos(List<ClassItem> classInfos);
}
