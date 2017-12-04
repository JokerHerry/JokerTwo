package co.example.hzq.jokertwo.MyMVP.LoginActivity.model;

import co.example.hzq.jokertwo.MyMVP.LoginActivity.presenter.LoginModelListener;

/**
 * Created by Hzq on 2017/11/29.
 */

public interface LoginModel {

    void login(UserTeacher user, LoginModelListener listener);
}
