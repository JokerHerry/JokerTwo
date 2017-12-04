package co.example.hzq.jokertwo.MyMVP.LoginActivity.presenter;

import co.example.hzq.jokertwo.MyMVP.LoginActivity.model.LoginModel;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.model.LoginModelImpl;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.model.UserTeacher;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.view.LoginView;

/**
 * Created by Hzq on 2017/11/29.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginModelListener{
    LoginView view;
    LoginModel model;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        this.model = new LoginModelImpl();
    }


    @Override
    public void checkLogin(UserTeacher user) {
        if(view==null){
            return;
        }
        view.showDialog();
        model.login(user,this);
    }

    @Override
    public void onLoginSuccess() {
        view.hideDialog();
        view.loginSuccess();
    }

    @Override
    public void passWError() {
        view.hideDialog();
        view.passWError();
    }

    @Override
    public void emptyError() {
        view.hideDialog();
        view.emptyError();
    }

}
