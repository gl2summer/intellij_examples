package pers.cjh;

import java.sql.*;

public class MySqlTest {
    private Connection conn = null;
    private Statement stmt = null;

    public void disconnect(){

        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            conn = null;
        }
    }

    public boolean connect(String database, String user, String password){
        disconnect();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动程序
            System.out.println("load driver success!");

            String url = "jdbc:mysql://localhost:3306/";
            url += database;
            url += "?serverTimezone=UTC";
            try {
                conn = DriverManager.getConnection(url, user, password);//获得一个Connection对象
                stmt = conn.createStatement();//创建Statement对象，用来执行SQL语句
                System.out.println("connect to mysql success!");
                return true;
            } catch (SQLException e) {
                System.out.println("connect to mysql failed!");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("load driver failed!");
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnected(){
        return ((stmt != null)&&(conn != null));
    }

    public boolean select(String table, String fields){
        try {
            String sql = "select " + fields + " from " + table;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("column: " + rs.getString(2));
            }
            rs.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insert(String table, String fields, String values){
        try {
            String sql = "insert into " + table + "(" + fields + ") " + "values(" + values + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            //ps.setString();
            return (ps.executeUpdate()>0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(String table, String field, String value, String where){
        try {
            String sql = "update ";
            sql += table;
            sql += " set ";
            sql += field;
            sql += "=";
            sql += value;
            sql += " where ";
            sql += where;

            return (stmt.executeUpdate(sql)>0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String table, String where){
        try {
            String sql = "delete from ";
            sql += table;
            sql += " where ";
            sql += where;

            return (stmt.executeUpdate(sql)>0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
