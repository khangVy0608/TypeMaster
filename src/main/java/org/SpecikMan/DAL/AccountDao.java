package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;

public class AccountDao implements Dao<Account> {
    private final Connection connection = DBConnection.getConnection();
    private final List<Account> accounts = new ArrayList<>();

    public AccountDao() {
    }

    public List<Account> getAll() {
        try {
            String query = "select * from account,role where role.idRole = account.idRole"; //SQL Query
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.isBeforeFirst()) {
                if (rs.next()) {
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
            String query = "select * from account where account.idAccount = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
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
            prepareStatement.setString(3,BCrypt.hashpw(account.getPassword(),BCrypt.gensalt()));
            prepareStatement.setString(4,account.getEmail());
            prepareStatement.setString(5,account.getIdRole());
            prepareStatement.executeQuery();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Account account) {
    }

    public void delete(Account account) {
    }

}
