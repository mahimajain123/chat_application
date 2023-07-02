package com.june;

import java.sql.*;

public class sqlclass {
    private final String DB_NAME = "multiclientchat";
    private final String USER_NAME = "root";
    private final String PASSWORD = "";
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    public sqlclass(){

        connection();
    }

    public void connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME+"?useLegacyDatetimeCode=false&serverTimezone=UTC",USER_NAME,PASSWORD);
            System.out.println("Connection Established");

        }
        catch(Exception e)
        { System.out.println(e);}
    }
    public void insertt(String table_name,String column_name,String value){
        try {
            String query = "insert into "+table_name+" ("+column_name+") values ("+value+")";
            System.out.println(query);
            ps = con.prepareStatement(query);
            int k = ps.executeUpdate();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
    }


    }

