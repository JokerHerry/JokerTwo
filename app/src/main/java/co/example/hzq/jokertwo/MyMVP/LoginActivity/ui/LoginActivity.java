package co.example.hzq.jokertwo.MyMVP.LoginActivity.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import co.example.hzq.jokertwo.Activity.BaseActivity;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.model.UserTeacher;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.presenter.LoginPresenter;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.presenter.LoginPresenterImpl;
import co.example.hzq.jokertwo.MyMVP.LoginActivity.view.LoginView;
import co.example.hzq.jokertwo.MyMVP.MainActivity.ui.MainActivity;
import co.example.hzq.jokertwo.R;
import co.example.hzq.jokertwo.Util.DialogUtil;
import co.example.hzq.jokertwo.Util.SPUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginView {

    @BindView(R.id.nameEdit)
    EditText nameEdit;
    @BindView(R.id.passwordEdit)
    EditText passwordEdit;
    @BindView(R.id.LoginBtn)
    Button loginBtn;
    @BindView(R.id.registerBtn)
    Button registerBtn;

    LoginPresenter presenter;


    @Override
    protected void initView(Bundle savedInstanceState) {
        checkLogin();
        checkRememberPass();
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        presenter = new LoginPresenterImpl(this);
    }

    private void checkRememberPass() {
        String name = SPUtils.getSharedStringData(null,"name");
        String pass = SPUtils.getSharedStringData(null,"password");
        if (!name.equals("")){
            nameEdit.setText(name);
        }
        if (!pass.equals("")){
            passwordEdit.setText(pass);
        }
    }

    private void checkLogin() {
        Boolean login = SPUtils.getSharedBooleanData(null,"login");
        if (login){
            startAC(MainActivity.class);
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed() {
        hideDialog();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LoginBtn:
                String name = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                UserTeacher userTeacher = new UserTeacher(name, password);
                presenter.checkLogin(userTeacher);
                break;
            case R.id.registerBtn:
                //注册界面
                /*Intent registerActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(registerActivity);*/

                DialogUtil.getIntance().waitDialog(this).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtil.getIntance().closeDialog();
                    }
                },2000);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        shortToast("登录成功");
        startAC(MainActivity.class);
        finish();
    }

    @Override
    public void passWError() {
        shortToast("密码不正确，注意区分大小写");
    }

    @Override
    public void emptyError() {
        shortToast("请正确输入账号和密码");
    }

    @Override
    public void showDialog() {
        super.showDialog("登录中...");
    }

    @Override
    public void hideDialog() {
        super.stopDialog();
    }
}
