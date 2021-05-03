package com.example.servlet01;

import net.sf.json.JSONObject;
import org.apache.commons.collections.BidiMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *请求转发跳转
 *  req.getRequestDispatcher(url).forward(req,resp);
 *  可以让请求从服务器跳转到客户端（或跳转到指定Servlet）
 *
 *  特点：
 *  1、服务端行为
 *  2、地址栏不会发生改变
 *  3、从始至终只有一个请求
 *  4、request数据可以共享
 */

@WebServlet("/ser03")
public class Servlet03 extends HttpServlet{

    private String ideatitle;
    private String ideacontent;
    private String ideaaddress;
    private String ideacontact;
    private String ideamaster;
    private static String rs;

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
        ideatitle  = req.getParameter("ideatitle");
        ideacontent = req.getParameter("ideacontent");
        ideaaddress = req.getParameter("ideaaddress");
        ideacontact = req.getParameter("ideacontact");
        ideamaster = req.getParameter("ideamaster");
        //String result = ConnectionJDBC(username,userpassword);
        ConnectionJDBC db = new ConnectionJDBC();
        db.AddIdea(ideatitle,ideacontent,ideaaddress,ideacontact,ideamaster);
        String rs = db.SelectIdea(ideatitle, ideamaster);
        if(rs.equals("OK")){
            System.out.println("title: " +ideatitle + " , user: " + ideamaster +rs );
        }else {
            System.out.println("title: " +ideatitle + " , user: " + ideamaster +rs );
        }
        JSONObject obj = new JSONObject();
        obj.put("result",rs);
        PrintWriter out = resp.getWriter();
        out.println(obj.toString());

    }
}
