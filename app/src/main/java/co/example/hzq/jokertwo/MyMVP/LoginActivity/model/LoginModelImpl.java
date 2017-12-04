package co.example.hzq.jokertwo.MyMVP.LoginActivity.model;

import android.os.Handler;

import co.example.hzq.jokertwo.MyMVP.LoginActivity.presenter.LoginModelListener;
import co.example.hzq.jokertwo.Util.SPUtils;

/**
 * Created by Hzq on 2017/11/29.
 */

public class LoginModelImpl implements LoginModel{

    @Override
    public void login(UserTeacher user, final LoginModelListener listener) {
        final String name = user.getName();
        final String password = user.getPassword();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(name.equals("")){
                    listener.emptyError();
                    return ;
                }
                if (password.equals("")){
                    listener.emptyError();
                    return;
                }
                if(name.equals(password)){
                    listener.onLoginSuccess();

                    SPUtils.setSharedStringData(null,"name",name);
                    SPUtils.setSharedStringData(null,"password",password);
                    SPUtils.setSharedBooleanData(null,"login",true);

                }else{
                    listener.passWError();
                }
            }
        },2000);
    }
}
