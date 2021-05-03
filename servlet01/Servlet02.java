package com.example.servlet01;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Servlet02",urlPatterns = {"/ser02","/ser002"})  //这是注解,标明路径
public class Servlet02 extends HttpServlet {

    //定义数据库的驱动程序
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    //定义数据库的连接地址、后面加的是时区
    public static final String DBURL = "jdbc:mysql://localhost:3306/database1?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
    //数据库的用户名
    public static final String DBUSER = "root";
    //数据库的密码
    public static final String DBPASS = "root";

    private static final String ERROR = "error";


    private String username;
    private String userpassword;
    private static String rs;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*json
        response.setContentType("text/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String json="666";
        JSONArray jsonarray = JSONArray.fromObject(json);
            out.println(jsonarray);

         */
        JSONObject obj = new JSONObject();
        obj.put("result",rs);
        PrintWriter out = response.getWriter();
        out.println(obj.toString());
       // response.getWriter().write(obj.toString());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*常用方法*/
     /*  获取请求时的完整路径（从http开始，到“？”前面结束）
         String url = req.getRequestURL() +"";
         System.out.println("获取请求时的完整路径："+ url);
        //获取请求时的部分路径（从项目的站点名开始，到“？”前面结束）
         String uri = req.getRequestURI();
         System.out.println("获取请求时的部分路径："+ uri);
        //获取请求时的参数字符串（从“？”后面开始，到最后的字符串)
         String queryString = req.getQueryString();
         System.out.println("获取请求时的参数字符串：" + queryString);
        //获取请求方式（GET和POST）
         String method = req.getMethod();
         System.out.println("获取请求方式：" + method);
        //获取当前协议版本（HTTP/1.1）
         String protocol = req.getProtocol();
         System.out.println("获取当前协议版本： " + protocol);
        //获取项目目前站点名（项目对外访问路径）
         String webapp = req.getContextPath();  //上下文路径
        System.out.println("获取项目的站点名: "+ webapp);
     */


        /* 获取请求的参数*/
        //获取指定名称的参数值，返回的是字符串
        //resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        username  = req.getParameter("name");
        userpassword = req.getParameter("password");
        //String result = ConnectionJDBC(username,userpassword);
        ConnectionJDBC db = new ConnectionJDBC();
        String rs = db.SelectUser(username,userpassword);
        if(rs.equals("OK")){
            db.AddUser(username,userpassword);
            System.out.println("name: " + username + " , password: " + userpassword +rs );
        }else {
            System.out.println("name: " + username + " , password: " + userpassword +rs );
        }
        JSONObject obj = new JSONObject();
        obj.put("result",rs);
        PrintWriter out = resp.getWriter();
        out.println(obj.toString());

     /*
        req.setAttribute(rs,"OK");
      */
        //resp.getWriter();

/*
        //获取指定名称的参数的所有参数值，返回字符串数组（用于复选框传值）
        String[] hobbys = req.getParameterValues("hobby");
        //判断数组是否为空
        if(hobbys != null && hobbys.length > 0){
            for(String hobby : hobbys){
                System.out.println("爱好： "+hobby);
            }

 */
    }

}
