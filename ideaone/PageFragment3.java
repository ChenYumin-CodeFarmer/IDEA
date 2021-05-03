package com.example.ideaone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ideaone.Login_Register.LoginActivity;

public class PageFragment3 extends Fragment {

    private Button mclick_but;
    private Button mMine_but;
    private Button mSetting_but;
    private Button mAbout_but;
    private Button mMark_but;
    //private String username = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);
        String but_text = "登录";
        //showResponse(but_text)
        String username = ((MainActivity) getActivity()).getTitles();//通过强转成宿主activity，就可以获取到MainActivity传递过来的数据
     //   String text = getArguments().getString("user");
        mclick_but = view.findViewById(R.id.click_btn);
        mclick_but.setText(but_text);
        if(username != null){
            mclick_but.setText(username);
        }

        mclick_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                Intent intent = ((AppCompatActivity) getActivity()).getIntent();
                String username = intent.getStringExtra("user");
                if(username != null) {
                    mclick_but.setText(username);
                }
            }
        });

        mMine_but = view.findViewById(R.id.mine_but);
        mMine_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MineActivity.class);
                i.putExtra("user",username);
                startActivity(i);
            }
        });

        mMark_but = view.findViewById(R.id.mark_but);
        mMark_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ConcernActivity.class);
                i.putExtra("user",username);
                startActivity(i);
            }
        });

        mSetting_but = view.findViewById(R.id.setting_but);
        mSetting_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });

        mAbout_but = view.findViewById(R.id.about_but);
        mAbout_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void showResponse(final String buttext){
        Intent intent = ((AppCompatActivity) getActivity()).getIntent();
        String username = intent.getStringExtra("user");
        if(username != null) {
            ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //在这里进行UI操作，将结果显示到界面上
                    mclick_but.setText(username);
                }
            });
        }else{
            ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //在这里进行UI操作，将结果显示到界面上
                    mclick_but.setText(buttext);
                }
            });
        }
    }

}
