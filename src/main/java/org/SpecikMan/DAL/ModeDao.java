package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Mode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeDao implements Dao<Mode>{
    private final Connection connection = DBConnection.getConnection();
    private final List<Mode> modes = new ArrayList<>();

    public ModeDao() {
    }

    public List<Mode> getAll() {
        try {
            String query = "select * from Mode"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    modes.add(new Mode(rs.getString("idMode"),rs.getString("nameMode")));
                }
            }
            prepareStatement.close();
            return modes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Mode get(String id) {
        try {
            String query = "select * Mode where idMode = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Mode mode = new Mode();
            while (rs.next()) {
                mode = new Mode(rs.getString("idMode"), rs.getString("nameMode"));
            }
            prepareStatement.close();
            return mode;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Mode mode) {
        try {
            String query = "insert into Mode values (?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, mode.getIdMode());
            prepareStatement.setString(2, mode.getNameMode());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Mode mode) {
        try {
            String query = "update Mode set name = ? where idMode = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, mode.getNameMode());
            //Condition
            prepareStatement.setString(2, mode.getIdMode());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Mode mode) {
        try {
            String query = "delete from Mode where idMode = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, mode.getIdMode());
            prepareStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
