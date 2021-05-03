package com.example.servlet01;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionJDBC {
    //定义数据库的驱动程序
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    //定义数据库的连接地址、后面加的是时区
    public static final String DBURL = "jdbc:mysql://localhost:3306/database1?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
    //数据库的用户名
    public static final String DBUSER = "root";
    //数据库的密码
    public static final String DBPASS = "root";

    public static final String ERROR = "error";

    public void AddUser (String username,String userpassword){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作

        JSONObject obj = new JSONObject();
        obj.put("length",0);
        obj.put("0",0);
        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
       // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";
        String sql1 = "INSERT INTO usertables(name,password,concern)"+"VALUES('"+username+"','"+userpassword+"','"+obj.toString()+"')";
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            stmt.executeUpdate(sql1);           //执行数据库更新操作
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void AddIdea (String title, String content,String git, String contact, String user){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";
        String sql1 = "INSERT INTO ideatables(ideatitle,ideacontent,ideaaddress,ideacontact,ideamaster)"+"VALUES('"+title+"','"+content+"','"+git+"','"+contact+"','"+user+"')";
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            stmt.executeUpdate(sql1);           //执行数据库更新操作
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String SelectIdea(String title,String user){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果
        String sql = "SELECT ideatitle,ideamaster FROM ideatables";

        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            rs = stmt.executeQuery(sql);           //执行数据库更新操作\

            while (rs.next()){
                String ideatitle = rs.getString("ideatitle");
                String ideamaster = rs.getString("ideamaster");
                if(ideatitle.equals(title) && ideamaster.equals(user)){
                    return "OK";
                }
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }

        return "ERROR";
    }

    public ArrayList<String> SelectUserIdea(String user){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果
        String sql = "SELECT ideamaster,ideatitle,ideacontent,ideahot,ideaaddress,ideacontact,ideastate,ideauid FROM ideatables";

        ArrayList<String> array = new ArrayList<>();

        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            rs = stmt.executeQuery(sql);           //执行数据库更新操作\

            while (rs.next()){
                String ideamaster = rs.getString("ideamaster");
                if(ideamaster.equals(user)){
                    JSONObject obj = new JSONObject();
                    obj.put("ideamaster",ideamaster);
                    obj.put("ideatitle",rs.getString("ideatitle"));
                    obj.put("ideacontent",rs.getString("ideacontent"));
                    obj.put("ideahot",rs.getString("ideahot"));
                    obj.put("ideaaddress",rs.getString("ideaaddress"));
                    obj.put("ideacontact",rs.getString("ideacontact"));
                    obj.put("ideastate",rs.getString("ideastate"));
                    obj.put("ideauid",rs.getString("ideauid"));
                    String idea = obj.toString();
                    array.add(idea);
                }
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }

        return array;
    }

    public ArrayList<String> SelectAllIdea(){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果
        String sql = "SELECT ideamaster,ideatitle,ideacontent,ideahot,ideaaddress,ideacontact,ideastate,ideauid FROM ideatables";

        ArrayList<String> array = new ArrayList<>();

        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            rs = stmt.executeQuery(sql);           //执行数据库更新操作\

            while (rs.next()){
                 JSONObject obj = new JSONObject();
                 obj.put("ideamaster",rs.getString("ideamaster"));
                 obj.put("ideatitle",rs.getString("ideatitle"));
                 obj.put("ideacontent",rs.getString("ideacontent"));
                 obj.put("ideahot",rs.getString("ideahot"));
                 obj.put("ideaaddress",rs.getString("ideaaddress"));
                 obj.put("ideacontact",rs.getString("ideacontact"));
                 obj.put("ideastate",rs.getString("ideastate"));
                 obj.put("ideauid",rs.getString("ideauid"));
                 String idea = obj.toString();
                 array.add(idea);
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }

        return array;
    }

    public String SelectOtherIdea(String uid){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果
        String sql = "SELECT ideamaster,ideatitle,ideacontent,ideahot,ideaaddress,ideacontact,ideastate,ideauid FROM ideatables";

        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            rs = stmt.executeQuery(sql);           //执行数据库更新操作\

            while (rs.next()){
                String ideauid = rs.getString("ideauid");
                if(ideauid.equals(uid)){
                    JSONObject obj = new JSONObject();
                    obj.put("ideamaster",rs.getString("ideamaster"));
                    obj.put("ideatitle",rs.getString("ideatitle"));
                    obj.put("ideacontent",rs.getString("ideacontent"));
                    obj.put("ideahot",rs.getString("ideahot"));
                    obj.put("ideaaddress",rs.getString("ideaaddress"));
                    obj.put("ideacontact",rs.getString("ideacontact"));
                    obj.put("ideastate",rs.getString("ideastate"));
                    obj.put("ideauid",ideauid);
                    String idea = obj.toString();
                    return idea;
                }
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }

        return "ERROR";
    }


    public String DeleteUser(String username,String userpassword){


        return "OK";
    }

    public String SelectUser(String username,String userpassword){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果
        String sql = "SELECT name,password FROM usertables";

        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            System.out.println(conn);
            stmt = conn.createStatement();     //实例化Statement对象
            rs = stmt.executeQuery(sql);           //执行数据库更新操作\

            while (rs.next()){
                String name = rs.getString("name");
                String pass = rs.getString("password");
                if(name.equals(username) && pass.equals(userpassword)){
                    return "ERROR";
                }
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }

        return "OK";
    }

    public String GetUserConcern (String master){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";
        String sql1 = "SELECT name,concern FROM usertables";
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            stmt = conn.createStatement();     //实例化Statement对象
         //   stmt.executeUpdate(sql1);           //执行数据库更新操作
            rs = stmt.executeQuery(sql1);           //执行数据库更新操作

            while (rs.next()){
                String name = rs.getString("name");
                String concern = rs.getString("concern");
                if(name.equals(master)){
                    return concern;
                }
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "ERROR";
    }

    public String UpdateConcern (String master,String update) {

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
      //  ResultSet rs = null;            //保存查询结果

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";

    /*    for (int i = 0; i < mjson.size(); i++) {
            // JSONObject obj = new JSONObject();
            // obj.put("ideauid", uid);
            if(uid.equals(mjson.get(i))){
                return "ERROR";
            }
        }

     */
      //  obj.put("uid",uid);
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            stmt = conn.createStatement();     //

            String sql = "UPDATE usertables SET concern='" + update + "'WHERE name='" + master+"'";
            stmt.executeUpdate(sql);           //执行数据库更新操作
            //  rs = stmt.executeQuery(sql);           //执行数据库更新操作

            //  rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    public void UpdateHot (String uid,String update) {

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        //  ResultSet rs = null;            //保存查询结果

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";

    /*    for (int i = 0; i < mjson.size(); i++) {
            // JSONObject obj = new JSONObject();
            // obj.put("ideauid", uid);
            if(uid.equals(mjson.get(i))){
                return "ERROR";
            }
        }

     */
        //  obj.put("uid",uid);
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            stmt = conn.createStatement();     //

            String sql = "UPDATE ideatables SET ideahot='" + update + "'WHERE ideauid='" + uid +"'";
            stmt.executeUpdate(sql);           //执行数据库更新操作
            //  rs = stmt.executeQuery(sql);           //执行数据库更新操作

            //  rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String GetIdeaHot (String uid){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";
        String sql1 = "SELECT ideauid,ideahot FROM ideatables";
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            stmt = conn.createStatement();     //实例化Statement对象
            //   stmt.executeUpdate(sql1);           //执行数据库更新操作
            rs = stmt.executeQuery(sql1);           //执行数据库更新操作

            while (rs.next()){
                String ideauid = rs.getString("ideauid");
                String ideahot = rs.getString("ideahot");
                if(ideauid.equals(uid)){
                    return ideahot;
                }
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "ERROR";
    }


    public ArrayList<String> SelectTopIdea (String uid){

        Connection conn = null;         //数据库连接
        Statement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果

        ArrayList<String> array = new ArrayList<>();

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";
        String sql1 = "SELECT * FROM ideatables order by ideahot desc limit 10";
        //select * from tablename order by num desc limit 10;
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            stmt = conn.createStatement();     //实例化Statement对象
            //   stmt.executeUpdate(sql1);           //执行数据库更新操作
            rs = stmt.executeQuery(sql1);           //执行数据库更新操作

            while (rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("ideamaster",rs.getString("ideamaster"));
                obj.put("ideatitle",rs.getString("ideatitle"));
                obj.put("ideacontent",rs.getString("ideacontent"));
                obj.put("ideahot",rs.getString("ideahot"));
                obj.put("ideaaddress",rs.getString("ideaaddress"));
                obj.put("ideacontact",rs.getString("ideacontact"));
                obj.put("ideastate",rs.getString("ideastate"));
                obj.put("ideauid",rs.getString("ideauid"));
                String idea = obj.toString();
                array.add(idea);
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }
        return array;
    }

    public ArrayList<String> SearchIdea (String SearchContent){

        Connection conn = null;         //数据库连接
        PreparedStatement stmt = null;          //数据库操作
        ResultSet rs = null;            //保存查询结果

        ArrayList<String> array = new ArrayList<>();

        //String sql = "INSERT INTO usertables(name, password)"+"VALUES("+username+","+userpassword+")";
        // String sql = "INSERT INTO usertables(name, password)"+"VALUES("+"'"+username+"'"+","+userpassword+")";
        String sql1 = "select * from ideatables WHERE ideamaster like ?";
                //or ideatitle like '%"+ SearchContent+"%'";
        //select * from tablename order by num desc limit 10;
        try {
            Class.forName(DBDRIVER);        //加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            //连接数据库时，要写上连接的用户名和密码
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            stmt = conn.prepareStatement(sql1);     //实例化Statement对象
            //   stmt.executeUpdate(sql1);           //执行数据库更新操作
            stmt.setString(1,"%"+SearchContent+"%");
            rs = stmt.executeQuery();           //执行数据库更新操作

            while (rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("ideamaster",rs.getString("ideamaster"));
                obj.put("ideatitle",rs.getString("ideatitle"));
                obj.put("ideacontent",rs.getString("ideacontent"));
                obj.put("ideahot",rs.getString("ideahot"));
                obj.put("ideaaddress",rs.getString("ideaaddress"));
                obj.put("ideacontact",rs.getString("ideacontact"));
                obj.put("ideastate",rs.getString("ideastate"));
                obj.put("ideauid",rs.getString("ideauid"));
                String idea = obj.toString();
                array.add(idea);
            }

            rs.close();
            stmt.close();         //操作关闭
            conn.close();         //数据库关闭
        }catch (SQLException e){
            e.printStackTrace();
        }
        return array;
    }

}
