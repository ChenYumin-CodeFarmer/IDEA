package com.example.ideaone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class IdeaActivity extends AppCompatActivity {

    public static final String IDEA_NAME = "idea_name";
    public static final String IDEA_ADDRESS = "idea_address";
    public static final String IDEA_CONTENT = "idea_content";
    public static final String IDEA_CONTACT = "idea_contact";
    public static final String IDEA_ICON_ID = "Idea_icon_id";
    public static final String IDEA_UID = "idea_uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        Intent intent = getIntent();      //取从适配器传来的值
        String ideaName = intent.getStringExtra(IDEA_NAME);
        String ideaContent = intent.getStringExtra(IDEA_CONTENT);
        String ideaAddress = intent.getStringExtra(IDEA_ADDRESS);
        String ideaContact = intent.getStringExtra(IDEA_CONTACT);
        String ideauid = intent.getStringExtra(IDEA_UID);
        int ideaIconId = intent.getIntExtra(IDEA_ICON_ID,0);
        networkRequest("0","0");    //浏览量加一

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView ideaIconView = (ImageView) findViewById(R.id.idea_image_view);
        TextView ideaContentText = (TextView) findViewById(R.id.idea_content_text);
        TextView ideaAddressText = (TextView) findViewById(R.id.git_address);
        TextView ideaContactText = (TextView) findViewById(R.id.lianxi_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(ideaName);
        Glide.with(this).load(ideaIconId).into(ideaIconView);
        ideaContentText.setText(ideaContent);
        ideaAddressText.setText(ideaAddress);
        ideaContactText.setText(ideaContact);

        //监听点击事件
        //单击复制github地址
        ideaAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入需要复制的文字的控件
                CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getApplicationContext(),ideaAddressText);
                copyButtonLibrary.init();
            }
        });

        //监听点击事件
        //单击复制github地址
        ideaContactText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传入需要复制的文字的控件
                CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getApplicationContext(),ideaContactText);
                copyButtonLibrary.init();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mark_fab);    //设置悬浮标记按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {     //启动子线程，发送网络请求
                    @Override
                    public void run() {
                        String result = networkRequest(ideauid,ideaName);
                     //   showResponse1(result, userText);
                    }
                }.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static String networkRequest(String uid,String user){         //上传数据
        HttpURLConnection connection=null;
        String rs = null;
        try {
            //  URL url = new URL("http://192.168.43.167:8080/servlet02/ser02");
            URL url = new URL("http://10.17.155.84:8080/servlet02/ser05");     //后台url，即servlet
            connection = (HttpURLConnection) url.openConnection();                    //创建URL连接实例
            connection.setConnectTimeout(3000);                                       //设置连接超时时限
            connection.setReadTimeout(3000);                                          //设置链接内容读取超时时限
            //设置请求方式 GET / POST 一定要大写
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");   //设置编码方式为UTF-8，解决中文乱码问题
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setDoInput(true);                                              //必要设置
            connection.setDoOutput(false);
            connection.connect();                                                     //启动连接
            //设置传送的数据
            DataOutputStream dos=new DataOutputStream(connection.getOutputStream());
            String param="ideauid="+ uid+"&ideamaster="+user;               //上传idea的uid和当前用户
            URLEncoder.encode(param,"utf-8" );
            dos.write(param.toString().getBytes());                   //将writebyte改为write，然后再将字符串转化为byte流传输，就不会乱码
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code" + responseCode);
            }
            //接受服务器返回的数据
            String result = getStringByStream(connection.getInputStream());
          //  rs = parseJSONWithJSONObject(result,"result");
            if (result == null) {
                Log.d("Fail", "服务器连接失败了！");
            }else {
                Log.d("succuss", "服务器连接成功了！");
                Log.d("succuss", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    private static String getStringByStream(InputStream inputStream){
        Reader reader;
        try {
            reader=new InputStreamReader(inputStream,"UTF-8");
            char[] rawBuffer=new char[512];
            StringBuffer buffer=new StringBuffer();
            int length;
            while ((length=reader.read(rawBuffer))!=-1){
                buffer.append(rawBuffer,0,length);
            }
            return buffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseJSONWithJSONObject(String jsonData,String key) {      //解析返回的json数据
        String value = null;
        try {
            //JSONArray jsonArray = new JSONArray(jsonData);
            //for (int i = 0; i < jsonArray.length(); i++) {
            //JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject jsonObject = new JSONObject(jsonData);
            value = jsonObject.optString(key);
            Log.d("LoginActivity", "key = " +value);
            // }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }



}