package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.Utils.UserInfo;
import com.example.login.db.UserDbHelper;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_password, et_passwore2;
//    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取SharedPreferences对象
//        mSharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

        //初始化控件
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);
        et_passwore2 = findViewById(R.id.et_password2);
        //点击注册
        findViewById(R.id.bt_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toasty.error(RegisterActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (isUsernameExist(username)) {
                    Toasty.error(RegisterActivity.this, "该用户名已经被注册", Toast.LENGTH_SHORT).show();
                } else {
                    int row = UserDbHelper.getInstance(RegisterActivity.this).register(username, password);
                    if (row > 0) {
                        Toasty.success(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
    // 判断用户名是否已经存在
    private boolean isUsernameExist(String username) {
        // 查询数据库，判断用户名是否已经存在
        return UserDbHelper.getInstance(RegisterActivity.this).isUsernameExist(username);
    }
}