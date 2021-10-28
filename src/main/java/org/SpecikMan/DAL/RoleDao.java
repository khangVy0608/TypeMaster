package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Role;
import org.SpecikMan.Tools.GenerateID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao implements Dao<Role>{
    private final Connection connection = DBConnection.getConnection();
    private final List<Role> roles = new ArrayList<>();

    public RoleDao() {
    }

    public List<Role> getAll() {
        try {
            String query = "select * from Role"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    roles.add(new Role(rs.getString("idRole"),rs.getString("nameRole")));
                }
            }
            prepareStatement.close();
            return roles;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Role get(String id) {
        try {
            String query = "select * Role where idRole = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Role role = new Role();
            while (rs.next()) {
                role = new Role(rs.getString("idRole"), rs.getString("roleName"));
            }
            prepareStatement.close();
            return role;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Role role) {
        try {
            String query = "insert into Role values (?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, role.getIdRole());
            prepareStatement.setString(2, role.getNameRole());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Role role) {
        try {
            String query = "update Role set nameRole = ? where idRole = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, role.getNameRole());
            //Condition
            prepareStatement.setString(2, role.getIdRole());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Role role) {
        try {
            String query = "delete from Role where idRole = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, role.getIdRole());
            prepareStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
