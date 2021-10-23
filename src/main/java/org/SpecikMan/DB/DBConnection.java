package org.SpecikMan.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        SQLServerDataSource ds = new SQLServerDataSource();
        String user = "sa";
        String server = "MSI\\SQLEXPRESS";
        String password = "0359292183";
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
