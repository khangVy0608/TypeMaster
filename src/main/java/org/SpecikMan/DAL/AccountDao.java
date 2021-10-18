package org.SpecikMan.DAL;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDao implements Dao<Account> {
    private final Connection connection = DBConnection.getConnection();
    private final List<Account> accounts = new ArrayList<>();

    public AccountDao() {
    }

    public List<Account> getAll() {
        try {
            String query = "select * from account,role where account.idRole = role.idRole"; //SQL Query
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    accounts.add(new Account(rs.getString("idAccount"), rs.getString("username"),
                            rs.getString("password"), rs.getString("email"), rs.getString("fullName"),
                            rs.getDate("dob"), rs.getString("idRole"), rs.getString("nameRole")));
                }
            }
            prepareStatement.close();
            return accounts;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Optional<Account> get(String id) {
        try {
            String query = "select * from account where idAccount = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs != null) {
                Account account = new Account(rs.getString("idAccount"), rs.getString("username"),
                        rs.getString("password"), rs.getString("email"), rs.getString("fullName"),
                        rs.getDate("dob"), rs.getString("idRole"), rs.getString("nameRole"));
                prepareStatement.close();
                return Optional.of(account);
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public void add(Account account) {
        try {
            String query = "insert into account values (?,?,?,?,null,null,?)"; //Full name - Dob null
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1,account.getIdAccount());
            prepareStatement.setString(2,account.getUsername());
            prepareStatement.setString(3,account.getPassword());
            prepareStatement.setString(4,account.getEmail());
            prepareStatement.setString(5,account.getIdRole());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Account account) {
        try {
            String query = "update account set username = ?,password = ?,email = ?,fullName = ?,dob = ?,idRole = ? where idAccount = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, account.getUsername());
            prepareStatement.setString(2, account.getPassword());
            prepareStatement.setString(3, account.getEmail());
            prepareStatement.setString(4, account.getFullname());
            prepareStatement.setDate(5, account.getDob());
            prepareStatement.setString(6, account.getIdRole());
            //Condition
            prepareStatement.setString(7, account.getIdAccount());
            prepareStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Account account) {
        try {
            String query = "delete from account where idAccount = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, account.getIdAccount());
            prepareStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
