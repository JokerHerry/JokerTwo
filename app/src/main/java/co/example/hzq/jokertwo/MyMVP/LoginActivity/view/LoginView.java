package co.example.hzq.jokertwo.MyMVP.LoginActivity.view;

/**
 * Created by Hzq on 2017/11/29.
 */

public interface LoginView {
    void loginSuccess();
    void passWError();
    void emptyError();

    void showDialog();
    void hideDialog();
}
