package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login.Utils.UserInfo;
import com.example.login.db.UserDbHelper;

import es.dmoral.toasty.Toasty;

public class UpdataPwdActivity extends AppCompatActivity {
    private EditText et_new_password, et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_pwd);

        initData();
        initEvent();

    }



    private void initData() {
        et_new_password = findViewById(R.id.et_new_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);

    }
    private void initEvent() {
        //修改密码点击事件
        findViewById(R.id.btn_updata_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_pwd = et_new_password.getText().toString();
                String confirm_pwd = et_confirm_password.getText().toString();
                if (TextUtils.isEmpty(new_pwd) || TextUtils.isEmpty(confirm_pwd)){
                    Toasty.warning(UpdataPwdActivity.this,"信息不能为空", Toasty.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }else if (!new_pwd.equals(confirm_pwd)){
                    Toasty.error(UpdataPwdActivity.this,"两次密码不一致", Toasty.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }else {
                    UserInfo userInfo = UserInfo.getUserInfo();
                    if (null != userInfo){
                        int row = UserDbHelper.getInstance(UpdataPwdActivity.this).updatePwd(userInfo.getUsername(), confirm_pwd);
//                        Log.d("UpdatePassword", "Rows updated: " + row);
                        if (row > 0){
                            Toasty.success(UpdataPwdActivity.this,"修改密码成功", Toasty.LENGTH_SHORT).show();
                            //回传数据时要用，在startActivityForResult方法,启动一个页面，并且在该页面要设置
                            //setResult
                            setResult(1000);
                            Intent intent = new Intent(UpdataPwdActivity.this, LoginActivity.class);

                            // 设置标志以清除所有在LoginActivity上面的活动堆栈
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // 启动LoginActivity
                            startActivity(intent);

                            finish();
                        }else {
                            Toasty.error(UpdataPwdActivity.this,"修改密码失败", Toasty.LENGTH_SHORT).show();

                        }
                    }else {
                        Toasty.error(UpdataPwdActivity.this,"用户信息为空", Toasty.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED);
                    }
                }
            }
        });
    }
}