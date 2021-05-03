package com.example.servlet01;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/ser07")
public class servlet07 extends HttpServlet { //主页数据后台
    private String ideamaster;
    private static String rs;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受客户端请求的参数
        resp.setContentType("text/html;charset=UTF-8");
        ideamaster = req.getParameter("ideamaster");
        //String result = ConnectionJDBC(username,userpassword);
        ConnectionJDBC db = new ConnectionJDBC();
        ArrayList<String> rs = db.SelectAllIdea();  //用字符串数组接收

        JSONObject obj1 = new JSONObject();
        for(int i = 0; i<rs.size();i++) {
            obj1.put(String.valueOf(i), rs.get(i));
        }
        obj1.put("length",String.valueOf(rs.size()));
        PrintWriter out = resp.getWriter();
        out.println(obj1.toString());
    }
}
