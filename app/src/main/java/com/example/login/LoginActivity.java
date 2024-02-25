package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.login.Utils.UserInfo;
import com.example.login.db.UserDbHelper;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username,et_password;
    private SharedPreferences mSharedPreferences;
    private CheckBox checkBox;
    private boolean is_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");

        //初始化控件
        et_username = findViewById(R.id.et_login_username);
        et_password = findViewById(R.id.et_login_password);
        checkBox = findViewById(R.id.checkbox);

        //获取SharedPreferences对象
        mSharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        //是否勾选了记住密码
        is_login = mSharedPreferences.getBoolean("is_login", false);
        //勾选记住密码
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoginActivity.this.is_login = isChecked;

            }
        });
        if (is_login){
           String username = mSharedPreferences.getString("username", null);
           String password = mSharedPreferences.getString("password", null);
           et_username.setText(username);
           et_password.setText(password);

           checkBox.setChecked(true);
        }




        //点击注册
        findViewById(R.id.regester).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //登录
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String username = et_username.getText().toString();
               String password = et_password.getText().toString();
               if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                   Toasty.error(LoginActivity.this, "请输入用户名和密码", Toasty.LENGTH_SHORT).show();
               }else {
//                   String name = mSharedPreferences.getString("username", null);  用sharedPreferences存储用户名和密码
//                   String pwd = mSharedPreferences.getString("password", null);
                   //用sqlite数据库存储用户名和密码
                   UserInfo login = UserDbHelper.getInstance(LoginActivity.this).login(username);
                   if (login != null) {
                       UserInfo.setUserInfo(login);
                       if (username.equals(login.getUsername()) && password.equals(login.getPassword())){
                           //登陆成功
                           SharedPreferences.Editor editor = mSharedPreferences.edit();
                           editor.putBoolean("is_login", LoginActivity.this.is_login);
                           editor.putString("username", username);
                           editor.putString("password", password);
                           editor.apply();

                           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       }else {
                           Toasty.error(LoginActivity.this, "用户名或密码错误", Toasty.LENGTH_SHORT).show();

                       }
                   }else {
                       Toasty.error(LoginActivity.this, "该账号未注册", Toasty.LENGTH_SHORT).show();
                   }
               }
            }
        });

    }


}