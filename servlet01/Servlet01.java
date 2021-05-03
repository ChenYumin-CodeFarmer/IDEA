package com.example.servlet01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现servlet
 * 1、创建普通Java类
 * 2、实现servlet的规范，继承httpServlet类
 * 3、重写service方法，用来处理请求
 * 4、设置注解，指定访问的路径
 */
//@WebServlet("/ser01")let01"
////@WebServlet(name = "Serv,value = {"/ser01","/ser001"})
@WebServlet(name = "Servlet01",urlPatterns = {"/ser01","/ser001"})  //这是注解,标明路径
public class Servlet01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //打印内容在控制台
        System.out.println("Hello Servlet！");
        //通过流输出数据到浏览器
        resp.getWriter().write("Hello Servlet!");
    }
}
