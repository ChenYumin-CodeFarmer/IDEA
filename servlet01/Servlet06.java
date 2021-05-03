package com.example.servlet01;


import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * request作用域
 * 通过该对象可以在一个请求中传递数据，作用范围：在一次请求中有效，即服务器跳转有效，（请求转发跳转时有效）
 * //设置域对象内容
 *   req.setAttribute(String name,Object value);
 * //获取域对象内容
 *   req.getAttribute(String name);
 * //删除域对象内容
 *   req.removeAttribute(String name);
 */
@WebServlet("/ser06")
public class Servlet06 extends HttpServlet {

    private String ideamaster;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受客户端请求的参数
        resp.setContentType("text/html;charset=UTF-8");
        ideamaster = req.getParameter("ideamaster");
        //String result = ConnectionJDBC(username,userpassword);
        ConnectionJDBC db = new ConnectionJDBC();
        String concern = db.GetUserConcern(ideamaster);  //拿到原关注内容的uid
        System.out.println(concern);
        //if(rs.equals("OK"))
        JSONObject obj = JSONObject.fromObject(concern);
        int length = Integer.parseInt(obj.optString("length"));
        System.out.println(length);


        JSONObject obj1 = new JSONObject();
        for (int i = 1; i <= length; i++) {
            obj1.put(String.valueOf(i), db.SelectOtherIdea(obj.optString(String.valueOf(i))));  //用字符串接收
        }
        obj1.put("length", String.valueOf(length));
        PrintWriter out = resp.getWriter();
        out.println(obj1.toString());
        System.out.println(obj1.toString());
    }

    private static String parseJSONWithJSONObject(JSONObject obj, int length, String uid) {      //解析返回的json数据
        // String value = null;
        for (int i = 0; i <= length; i++) {
            if (obj.optString(String.valueOf(i)).equals(uid)) {
                return "ERROR";
            }
        }
        return "OK"; //一个想法分享平台，主要是给开发者提供开发灵感的平台，用户和开发者都可以在上面分享想法
        //http://github?name=idea
        //QQ：2318033558
    }
}