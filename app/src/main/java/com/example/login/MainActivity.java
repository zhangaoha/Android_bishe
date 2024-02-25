package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.login.fragment.FindFragment;
import com.example.login.fragment.HomeFragment;
import com.example.login.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private MineFragment mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);

        //默认第一个选中首页
        selectedFragment(0);

        //bottomNavigationView点击切换事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment(0);
                        break;
                    case R.id.find:
                        selectedFragment(1);
                        break;
                    case R.id.mine:
                        selectedFragment(2);
                        break;
                }
                return true;
            }
        });

    }

    private void selectedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);

        if (position == 0){
            if (mHomeFragment == null){
                mHomeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.content, mHomeFragment);
            }else {
                fragmentTransaction.show(mHomeFragment);
            }

        }else if (position == 1){
            if (mFindFragment == null){
                mFindFragment = new FindFragment();
                fragmentTransaction.add(R.id.content, mFindFragment);
            }else {
                fragmentTransaction.show(mFindFragment);
            }

        }else if (position == 2){
            if (mMineFragment == null){
                mMineFragment = new MineFragment();
                fragmentTransaction.add(R.id.content, mMineFragment);
            }else {
                fragmentTransaction.show(mMineFragment);
            }
        }
        //一定要提交事务
        fragmentTransaction.commit();
//        bottomNavigationView.getMenu().getItem(position).setChecked(true);

    }


    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(mHomeFragment != null){
            fragmentTransaction.hide(mHomeFragment);
        }
        if(mFindFragment != null){
            fragmentTransaction.hide(mFindFragment);
        }
        if(mMineFragment != null){
            fragmentTransaction.hide(mMineFragment);
        }
    }

}