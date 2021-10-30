package org.SpecikMan.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;


import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        SQLServerDataSource ds = new SQLServerDataSource();
        String user = "sa";
        String server = "DESKTOP-VGBCIMJ\\SQLEXPRESS2014";
        String password = "12345";
        String db = "TypeMaster";
        int port = 1433;
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);
        Connection conn = null;
        try{
            conn = ds.getConnection();
        } catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return conn;
    }
}
