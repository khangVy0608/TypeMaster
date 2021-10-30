package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account> {
    private final Connection connection = DBConnection.getConnection();
    private final List<Account> accounts = new ArrayList<>();

    public AccountDao() {
    }

    public List<Account> getAll() {
        try {
            String query = "select * from Account,Role where Account.idRole = Role.idRole"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    accounts.add(new Account(rs.getString("idAccount").trim(), rs.getString("username").trim(),
                            rs.getString("password"), rs.getString("email"), rs.getDate("createDate"), rs.getDate("latestLoginDate"), rs.getInt("countLoginDate"), rs.getString("fullName"),
                            rs.getDate("dob"), rs.getBoolean("gender"), rs.getString("verificationCode"), rs.getString("uud"), rs.getString("idRole").trim(), rs.getString("nameRole")));
                }
            }
            prepareStatement.close();
            return accounts;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Account get(String id) {
        try {
            String query = "select * from Account,Role where Account.idAccount = ? and Account.idRole = Role.idRole";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Account acc = new Account();
            while (rs.next()) {
                acc = new Account(rs.getString("idAccount").trim(), rs.getString("username").trim(),
                        rs.getString("password"), rs.getString("email"), rs.getDate("createDate"), rs.getDate("latestLoginDate"), rs.getInt("countLoginDate"), rs.getString("fullName"),
                        rs.getDate("dob"), rs.getBoolean("gender"), rs.getString("verificationCode"), rs.getString("uud"), rs.getString("idRole").trim(), rs.getString("nameRole")); //namerole = null
            }
            prepareStatement.close();
            return acc;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Account account) {
        try {
            String query = "insert into Account values (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, account.getIdAccount());
            prepareStatement.setString(2, account.getUsername());
            prepareStatement.setString(3, account.getPassword());
            prepareStatement.setString(4, account.getEmail());
            prepareStatement.setDate(5,account.getCreateDate());
            prepareStatement.setDate(6,account.getLatestLoginDate());
            prepareStatement.setInt(7,account.getCountLoginDate());
            prepareStatement.setString(8, account.getFullname());
            prepareStatement.setDate(9, account.getDob());
            prepareStatement.setBoolean(10, false);
            prepareStatement.setString(11, account.getVerificationCode());
            prepareStatement.setString(12, account.getUud());
            prepareStatement.setString(13, account.getIdRole());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Account account) {
        try {
            String query = "update Account set username = ?,password = ?,email = ?,createDate = ?,latestLoginDate = ?,countLoginDate = ?,fullName = ?,dob = ?,gender = ?,idRole = ?,verificationCode=?,uud=? where idAccount = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, account.getUsername());
            prepareStatement.setString(2, account.getPassword());
            prepareStatement.setString(3, account.getEmail());
            prepareStatement.setDate(4,account.getCreateDate());
            prepareStatement.setDate(5,account.getLatestLoginDate());
            prepareStatement.setInt(6,account.getCountLoginDate());
            prepareStatement.setString(7, account.getFullname());
            prepareStatement.setDate(8, account.getDob());
            prepareStatement.setBoolean(9,account.isGender());
            prepareStatement.setString(10, account.getIdRole());
            prepareStatement.setString(11, account.getVerificationCode());
            prepareStatement.setString(12, account.getUud());
            //Condition
            prepareStatement.setString(13, account.getIdAccount());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Account account) {
        try {
            String query = "delete from Account where idAccount = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, account.getIdAccount());
            prepareStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
