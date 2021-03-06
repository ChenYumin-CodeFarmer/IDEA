package com.example.ideaone;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ideaone.Adapter.IdeaAdapter;
import com.example.ideaone.Adapter.TopIdeaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

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

public class PageFragment1 extends Fragment {

    private DrawerLayout mDrawerLayout;
    private List<idea> mideaList = new ArrayList<>();
    private List<topIdea> ideaList = new ArrayList<>();
    private static int length;
    private static ArrayList<String> masterArray = new ArrayList<>();
    private static ArrayList<String> titleArray = new ArrayList<>();
    private static ArrayList<String> contentArray = new ArrayList<>();
    private static ArrayList<String> hotArray = new ArrayList<>();
    private static ArrayList<String> addressArray = new ArrayList<>();
    private static ArrayList<String> contactArray = new ArrayList<>();
    private static ArrayList<String> uidArray = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        Toolbar mtoolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mtoolbar);   //???????????????AppCompatActivity??????????????????

        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) view.findViewById(R.id.nav_view);        //??????????????????
        TextView usertext = (TextView) navView.getHeaderView(0).findViewById(R.id.user_txt);          //?????????????????????
        String user = ((MainActivity) getActivity()).getTitles();//?????????????????????activity?????????????????????MainActivity?????????????????????
        if(user != null){
            usertext.setText(user);
        }
        ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.more);
        }

        navView.setCheckedItem(R.id.nav_setting);        //??????????????????
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);    //????????????????????????
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SendActivity.class);
                i.putExtra("user",user);
                startActivity(i);
            }
        });

        setHasOptionsMenu(true);

        new Thread() {     //????????????????????????????????????
            @Override
            public void run() {
                networkRequest(user);
            }
        }.start();


        initIdeas();          //?????????????????????
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);   //???????????????
        recyclerView.setLayoutManager(layoutManager);
        TopIdeaAdapter adapter = new TopIdeaAdapter(ideaList);
        recyclerView.setAdapter(adapter);

        SwipeRefreshLayout mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_fresh);
        //?????????listview????????????????????????
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //??????????????????????????????????????????
                refresh(user);

                mSwipeLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void refresh(String user) {
        ((MainActivity) getActivity()).finish();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        ((MainActivity) getActivity()).overridePendingTransition(0, 0);
    }

    private void initIdeas(){
        if (masterArray.size() != length) {
            for(int i = 0; i < length; i++){
                topIdea one = new topIdea(masterArray.get(i),titleArray.get(i)+": "+contentArray.get(i),hotArray.get(i),
                        String.valueOf(i+1)+"???",addressArray.get(i),contactArray.get(i),uidArray.get(i));
                ideaList.add(one);
            }
        }
    }


    public String getRandomLengthName(String name){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuffer builder = new StringBuffer();
        for(int i = 0; i<length; i++){
            builder.append(name);
        }
        return builder.toString();
    }

    private static String networkRequest(String user){         //????????????
        HttpURLConnection connection=null;
        String rs = null;
        try {
            //  URL url = new URL("http://192.168.43.167:8080/servlet02/ser02");
            URL url = new URL("http://10.17.155.84:8080/servlet02/ser07");     //??????url??????servlet
            connection = (HttpURLConnection) url.openConnection();                    //??????URL????????????
            connection.setConnectTimeout(3000);                                       //????????????????????????
            connection.setReadTimeout(3000);                                          //????????????????????????????????????
            //?????????????????? GET / POST ???????????????
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");   //?????????????????????UTF-8???????????????????????????
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setDoInput(true);                                              //????????????
            connection.setDoOutput(false);
            connection.connect();                                                     //????????????
            //?????????????????????
            DataOutputStream dos=new DataOutputStream(connection.getOutputStream());
            String param="ideamaster="+user;               //???????????????
            URLEncoder.encode(param,"utf-8" );
            dos.write(param.toString().getBytes());                   //???writebyte??????write?????????????????????????????????byte???????????????????????????
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code" + responseCode);
            }
            //??????????????????????????????
            String result = getStringByStream(connection.getInputStream());
            length = Integer.parseInt(parseJSONWithJSONObject(result,"length"));
            for(int j = 0;j<length; j++) {
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
                Log.d("Fail", "???????????????????????????");
            }else {
                Log.d("succuss", "???????????????????????????");
                Log.d("succuss", result);
                //   Log.d("succuss", key);
                Log.d("succuss", ""+length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static String getStringByStream(InputStream inputStream){    //?????????????????????
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

    private static String parseJSONWithJSONObject(String jsonData,String key) {      //???????????????json??????
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
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //http://github?name=idea
        //QQ???2318033558
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {          //????????????
        inflater.inflate(R.menu.search, menu);
        SearchManager searchManager =
                (SearchManager) ((AppCompatActivity) getActivity()).getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(((AppCompatActivity) getActivity()).getComponentName()));
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}
