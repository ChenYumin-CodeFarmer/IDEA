package com.example.ideaone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ideaone.Adapter.TopIdeaAdapter;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConcernActivity extends AppCompatActivity {       //关注功能

    private List<topIdea> ideaList = new ArrayList<>();
    private static int length;
    private static ArrayList<String> masterArray = new ArrayList<>();
    private static ArrayList<String> titleArray = new ArrayList<>();
    private static ArrayList<String> contentArray = new ArrayList<>();
    private static ArrayList<String> hotArray = new ArrayList<>();
    private static ArrayList<String> addressArray = new ArrayList<>();
    private static ArrayList<String> contactArray = new ArrayList<>();
    private static ArrayList<String> uidArray = new ArrayList<>();
    private static String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concern);


        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        // Toast.makeText(MineActivity.this, user, Toast.LENGTH_SHORT).show();

        new Thread() {     //启动子线程，发送网络请求
            @Override
            public void run() {
                networkRequest(user);
            }
        }.start();

        initIdeas();          //初始化想法数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_concern);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);   //设置瀑布流
        recyclerView.setLayoutManager(layoutManager);
        TopIdeaAdapter adapter = new TopIdeaAdapter(ideaList);
        recyclerView.setAdapter(adapter);

        SwipeRefreshLayout mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.concern_fresh);
        //设置在listview上下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里可以做一下下拉刷新的操作
                refresh();

                mSwipeLayout.setRefreshing(false);
            }
        });
    }

    public void refresh() {   //下拉刷新操作
        finish();
        Intent intent = new Intent(this, ConcernActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    private void initIdeas(){
        if (masterArray.size() != length) {
            for(int i = 0; i < length; i++){
                topIdea one = new topIdea(masterArray.get(i),titleArray.get(i)+": "+contentArray.get(i),hotArray.get(i),
                        String.valueOf(i+1)+"、",addressArray.get(i),contactArray.get(i),uidArray.get(i));
                ideaList.add(one);
            }
        }
    }

    private static String networkRequest(String user){         //上传数据
        HttpURLConnection connection=null;
        String rs = null;
        try {
            //  URL url = new URL("http://192.168.43.167:8080/servlet02/ser02");
            URL url = new URL("http://10.17.155.84:8080/servlet02/ser06");     //后台url，即servlet
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
            String param="ideamaster="+user;               //上传用户名
            URLEncoder.encode(param,"utf-8" );
            dos.write(param.toString().getBytes());                   //将writebyte改为write，然后再将字符串转化为byte流传输，就不会乱码
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code" + responseCode);
            }
            //接受服务器返回的数据
            String result = getStringByStream(connection.getInputStream());
            length = Integer.parseInt(parseJSONWithJSONObject(result,"length"));
            for(int j = 1;j<=length; j++) {
                rs = parseJSONWithJSONObject(result, String.valueOf(j));
                masterArray.add(parseJSONWithJSONObject(rs,"ideamaster"));
                titleArray.add(parseJSONWithJSONObject(rs, "ideatitle"));
                contentArray.add(parseJSONWithJSONObject(rs,"ideacontent"));
                hotArray.add(parseJSONWithJSONObject(rs,"ideahot"));
                addressArray.add(parseJSONWithJSONObject(rs,"ideaaddress"));
                contactArray.add(parseJSONWithJSONObject(rs,"ideacontact"));
                uidArray.add(parseJSONWithJSONObject(rs,"ideauid"));
            }
            if (result == null) {
                Log.d("Fail", "服务器连接失败了！");
            }else {
                Log.d("succuss", "服务器连接成功了！");
                   Log.d("succuss", result);
                //   Log.d("succuss", key);
                Log.d("succuss", ""+length);
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
            Log.d("MineActivity", "key = " +value);
            // }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
        //一个想法分享平台，主要是给开发者提供开发灵感的平台，用户和开发者都可以在上面分享想法
        //http://github?name=idea
        //QQ：2318033558
    }
}