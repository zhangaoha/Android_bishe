package com.example.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.login.R;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getSupportActionBar().setTitle("关于APP");
    }
}