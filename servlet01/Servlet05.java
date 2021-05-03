package com.example.servlet01;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
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
@WebServlet("/ser05")
public class Servlet05 extends HttpServlet {

    private String ideamaster;
    private String ideauid;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受客户端请求的参数
        resp.setContentType("text/html;charset=UTF-8");
        ideamaster = req.getParameter("ideamaster");
        ideauid = req.getParameter("ideauid");
        //String result = ConnectionJDBC(username,userpassword);
        ConnectionJDBC db = new ConnectionJDBC();
        if(ideamaster.equals(0)) {
            String hot = db.GetIdeaHot(ideauid);
            db.UpdateHot(ideauid, String.valueOf(Integer.parseInt(hot) + 1));
        }
        String concern = db.GetUserConcern(ideamaster);  //拿到原关注内容的uid
        System.out.println(concern+ideauid);
        //if(rs.equals("OK"))
        JSONObject obj = JSONObject.fromObject(concern);
        int length = Integer.parseInt(obj.optString("length"))+1;
        System.out.println(length);
        obj.remove("length");
        obj.put("length",length);
        String re = parseJSONWithJSONObject(obj,length,ideauid);
        //obj.remove("uid");
        //obj.put("uid",obj1.toString());
        System.out.println(re);
        if(re.equals("OK")) {
            obj.put(String.valueOf(length),ideauid);
            String rs = db.UpdateConcern(ideamaster, obj.toString());
            System.out.println(rs);
        }

}

    private static String parseJSONWithJSONObject(JSONObject obj,int length, String uid) {      //解析返回的json数据
      // String value = null;
            for(int i = 0;i<=length;i++) {
                if(obj.optString(String.valueOf(i)).equals(uid)) {
                    return "ERROR";
                }
            }
        return "OK"; //一个想法分享平台，主要是给开发者提供开发灵感的平台，用户和开发者都可以在上面分享想法
        //http://github?name=idea
        //QQ：2318033558
    }

}
