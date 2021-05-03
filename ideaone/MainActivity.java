package com.example.ideaone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ideaone.Login_Register.LoginActivity;
import com.hjm.bottomtabbar.BottomTabBar;

public class MainActivity extends AppCompatActivity {

    private BottomTabBar bottomTabBar;
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        bottomTabBar = findViewById(R.id.bottom_tab_bar);
        //初始化Fragment
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(90, 90)   //图片大小
                .setFontSize(12)            //字体大小
                .setDividerColor(Color.rgb(171,217,224))
                .setTabBarBackgroundColor(Color.rgb(171,217,224))
                .setTabPadding(4, 6, 10)//选项卡的间距
                .setChangeColor(Color.rgb(235,216,176), Color.rgb(64,64,64))   //选项卡的选择颜色
                .addTabItem("首页", R.drawable.homepage_fill, PageFragment1.class)
                .addTabItem("排行", R.drawable.sort, PageFragment2.class)
                .addTabItem("我的", R.drawable.mine, PageFragment3.class)
                .isShowDivider(true)  //是否包含分割线
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {
                        Log.i("TGA", "位置：" + position + "   选项卡：" + name + username);
                    }
                });
//        Log.e("MainActivity",username);

    }
    public String getTitles(){
        return username;
    }
}