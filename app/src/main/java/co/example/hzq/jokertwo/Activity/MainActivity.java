package co.example.hzq.jokertwo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.example.hzq.jokertwo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username;
    EditText password;
    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        username = (EditText)findViewById(R.id.nameEdit);
        password = (EditText)findViewById(R.id.passwordEdit);
        loginBtn = (Button)findViewById(R.id.LoginBtn);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LoginBtn:
                Intent intent = new Intent(MainActivity.this,testActivity.class);
                startActivity(intent);
                break;
            case R.id.registerBtn:
                Intent intent1 = new Intent(MainActivity.this,test2Activity.class);
                startActivity(intent1);
                break;
        }
    }
}
