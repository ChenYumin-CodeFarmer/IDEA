package com.example.ideaone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideaone.Login_Register.LoginActivity;

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

public class SendActivity extends AppCompatActivity {

    private EditText title_edit;
    private EditText content_edit;
    private EditText git_edit;
    private EditText contact_edit;
    private Button mShare_but;
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        title_edit = (EditText) findViewById(R.id.title_edit);            //创建编辑控件实例
        content_edit = (EditText) findViewById(R.id.content_edit);
        git_edit = (EditText) findViewById(R.id.git_edit);
        contact_edit = (EditText) findViewById(R.id.call_edit);

      //  MainActivity mainActivity = new MainActivity();

        mShare_but = (Button) findViewById(R.id.share_but);
        mShare_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_text = title_edit.getText().toString();              //获取编辑框内容
                String content_text = content_edit.getText().toString();
                String git_text = git_edit.getText().toString();
                String contact_text = contact_edit.getText().toString();


                if(title_text != null || content_text != null) {
                    new Thread() {     //启动子线程，发送网络请求
                        @Override
                        public void run() {
                            String result = networkRequest(title_text,content_text,git_text,contact_text,user);
                            showResponse1(result,user);
                        }
                    }.start();
                }
            }
        });

    }

    private static String networkRequest(String title, String content,String git, String contact, String user){         //上传数据
        HttpURLConnection connection=null;
        String rs = null;
        try {
            //  URL url = new URL("http://192.168.43.167:8080/servlet02/ser02");
            URL url = new URL("http://10.17.155.84:8080/servlet02/ser03");     //后台url，即servlet
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
            String param="ideatitle="+ title +"&ideacontent="+ content+"&ideaaddress="+git+"&ideacontact="+contact+"&ideamaster="+user;               //上传输入的账号密码
            URLEncoder.encode(param,"utf-8" );
            dos.write(param.toString().getBytes());                   //将writebyte改为write，然后再将字符串转化为byte流传输，就不会乱码
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code" + responseCode);
            }
            //接受服务器返回的数据
            String result = getStringByStream(connection.getInputStream());
            rs = parseJSONWithJSONObject(result,"result");
            if (result == null) {
                Log.d("Fail", "服务器连接失败了！");
            }else {
                Log.d("succuss", "服务器连接成功了！");
                Log.d("succuss", rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static String getStringByStream(InputStream inputStream){    //接收返回的数据
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
            Log.d("SendActivity", "key = " +value);
            // }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value; //一个想法分享平台，主要是给开发者提供开发灵感的平台，用户和开发者都可以在上面分享想法
        //http://github?name=idea
        //QQ：2318033558
    }

    private void showResponse1(final String response,final String username){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //在这里进行UI操作，将结果显示到界面上
                if(response.equals(OK)){
                    Toast.makeText(SendActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SendActivity.this,MainActivity.class);
                    i.putExtra("user",username);
                    startActivity(i);
                }else {
                    Toast.makeText(SendActivity.this, "分享失败，请重新分享！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}