package co.example.hzq.jokertwo.MyMVP.LoginActivity.presenter;

/**
 * Created by Hzq on 2017/11/29.
 */

public interface LoginModelListener {
    void onLoginSuccess();
    void passWError();
    void emptyError();
}
