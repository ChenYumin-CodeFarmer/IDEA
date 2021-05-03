package com.example.ideaone.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideaone.Adapter.UserAdapter;
import com.example.ideaone.MainActivity;
import com.example.ideaone.PageFragment1;
import com.example.ideaone.PageFragment3;
import com.example.ideaone.R;
import com.example.ideaone.SearchResultActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginBut;          //定义登录按钮
    private Button mRegisterBut;       //定义注册按钮

    private EditText mUserEdT;         //定义用户名编辑框
    private EditText mPasswordEdT;     //定义密码编辑框

    public static final String ERROR = "ERROR";
    public static final String OK = "OK";

    private String userText;
    private String passwordText;
    private String result;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserEdT = (EditText) findViewById(R.id.user_edit);
        //http://localhost:8080/servlet02/ser02
        mPasswordEdT = (EditText) findViewById(R.id.password_edit);

        mLoginBut = (Button) findViewById(R.id.login_btn);   //登录按钮设置监听器
        mLoginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userText = mUserEdT.getText().toString().trim();
                passwordText = mPasswordEdT.getText().toString().trim();
                //sendRequestWithOkHttpURLConnection();
              //  sendRequestWithOkHttp1("http://10.17.235.168:8080/servlet02/ser07","user");            //发送网络请求
     /*           new Thread() {
                    @Override
                    public void run() {
                        networkRequest();
                    }
                }.start();
      */
                if(userText.equals("") || passwordText.equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else {
                        new Thread() {     //启动子线程，发送网络请求
                            @Override
                            public void run() {
                                String result = networkRequest(passwordText, userText);
                                showResponse1(result, userText);
                            }
                        }.start();
                }
            }
        });

        mRegisterBut = (Button) findViewById(R.id.register_btn);  //注册按钮设置监听器
        mRegisterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userText = mUserEdT.getText().toString();
                passwordText = mPasswordEdT.getText().toString();
                //sendRequestWithOkHttpURLConnection();
                if(userText.equals("") || passwordText.equals("")) {
                     Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else {
                        new Thread() {     //启动子线程，发送网络请求
                            @Override
                            public void run() {
                                String result = networkRequest(passwordText, userText);
                                showResponse(result);
                            }
                        }.start();
                }
            }
        });
    }

    private String sendRequestWithOkHttp1(String url,String key){   //上传用户数据（用户名和密码）的方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //.url("http://10.17.235.168:8080/servlet02/ser02?name=chenj&password=12")
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //parseJSONWithGSON(responseData);
                    System.out.println(responseData);
                    result = parseJSONWithJSONObject(responseData,key);       //接受返回的数据
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return result;
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

    private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        List<UserAdapter> userAdapterList = gson.fromJson(jsonData,new TypeToken<List<UserAdapter>>(){}.getType());
        for(UserAdapter userAdapter : userAdapterList){
            Log.d("LoginActivity","user = "+userAdapter.getUser());
            Log.d("LoginActivity","password = "+userAdapter.getPassword());

        }
    }

    private static String networkRequest(String passwordText, String userText){         //上传数据
        HttpURLConnection connection=null;
        String rs = null;
        try {
          //  URL url = new URL("http://192.168.43.167:8080/servlet02/ser02");
            URL url = new URL("http://10.17.155.84:8080/servlet02/ser02");     //后台url，即servlet
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
            String param="name="+ userText +"&password="+ passwordText;               //上传输入的账号密码
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



    private void sendRequestWithOkHttpURLConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                   // URL url = new URL("http://10.17.235.168:8080/servlet02/ser02");
                    URL url = new URL("http://10.17.155.84:8080/servlet02/ser02");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");    //方法为上传数据
                    connection.getConnectTimeout();
                    connection.getReadTimeout();
                    connection.setDoInput(true);
                    connection.setDoOutput(false);
                    InputStream in = connection.getInputStream();

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("name="+userText+"&"+"password="+passwordText);            //上传用户名和密码
                    //out.writeBytes("name=chen&password=123456");            //上传用户名和密码
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    //在这可对输入的数据进行UI处理
                    showResponse(userText);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void sendRequestWithOkHttp(){   //上传用户数据（用户名和密码）的方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("name",userText)
                            .add("password",passwordText)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://10.17.235.168:8080/servlet02/ser02")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //.url("http://10.17.235.168:8080/servlet02/ser02?name=chenj&password=12")
                            .url("http://10.17.155.84:8080/servlet02/ser02")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //在这里进行UI操作，将结果显示到界面上
                if(response.equals(OK)){
                    Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(LoginActivity.this, "用户已存在！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showResponse1(final String response,final String username){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //在这里进行UI操作，将结果显示到界面上
                if(response.equals(OK)){
                    Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    i.putExtra("user",username);
                    startActivity(i);
                }
            }
        });
    }
}