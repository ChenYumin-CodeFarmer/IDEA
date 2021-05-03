package com.example.servlet01;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendPost {

    public static void main(String[] args) {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://10.17.235.168:8080/servlet02/ser02");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");    //方法为上传数据
                    connection.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                   // out.writeBytes("name=chen" + "&" + "password=112");            //上传用户名和密码
                    System.out.println("登录成功");
                    out.writeBytes("name=chen&password=123456");            //上传用户名和密码
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
        }).start();
    }

    private void showResponse() {
        System.out.println("登录成功");
    }
}