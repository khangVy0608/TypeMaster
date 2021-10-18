package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDao implements Dao<Role>{
    private final Connection connection = DBConnection.getConnection();
    private final List<Role> roles = new ArrayList<>();

    public RoleDao() {
    }

    public List<Role> getAll() {
        try {
            String query = "select * from role"; //SQL Query
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    roles.add(new Role(rs.getString("idRole"), rs.getString("nameRole")));
                }
            }
            prepareStatement.close();
            return roles;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Optional<Role> get(String id) {
        try {
            String query = "select * from role where idRole = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs != null) {
                Role role = new Role(rs.getString("idRole"), rs.getString("nameRole"));
                prepareStatement.close();
                return Optional.of(role);
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public void add(Role role) {
        try {
            String query = "insert into role values (?,?)"; //Full name - Dob null
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,role.getIdRole());
            prepareStatement.setString(2,role.getNameRole());
            prepareStatement.executeQuery();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Role role) {
        try {
            String query = "update role set nameRole = ? where idRole = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, role.getNameRole());
            //Condition
            prepareStatement.setString(2, role.getIdRole());
            prepareStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Role role) {
        try {
            String query = "delete from role where idRole = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, role.getIdRole());
            prepareStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
