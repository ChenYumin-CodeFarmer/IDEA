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


@WebServlet("/ser09")
public class Servlet09 extends HttpServlet {

    private String SearchContent;
    private static String rs;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受客户端请求的参数
        resp.setContentType("text/html;charset=UTF-8");
        SearchContent = req.getParameter("SearchContent");
        //String result = ConnectionJDBC(username,userpassword);
        ConnectionJDBC db = new ConnectionJDBC();
        System.out.println(SearchContent);
        ArrayList<String> rs = db.SearchIdea(SearchContent);  //用字符串数组接收
        System.out.println(rs);
        JSONObject obj1 = new JSONObject();
        for(int i = 0; i<rs.size();i++) {
            obj1.put(String.valueOf(i), rs.get(i));
        }
        obj1.put("length",String.valueOf(rs.size()));
        PrintWriter out = resp.getWriter();
        out.println(obj1.toString());
        System.out.println(obj1.toString());
    }
}
