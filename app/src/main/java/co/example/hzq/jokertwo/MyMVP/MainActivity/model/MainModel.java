package co.example.hzq.jokertwo.MyMVP.MainActivity.model;

import java.util.List;

import co.example.hzq.jokertwo.List.ClassItem;
import io.reactivex.Observable;

/**
 * Created by Hzq on 2017/11/30.
 */

public interface MainModel {
    Observable<List<ClassItem>> getClassResult();
}
