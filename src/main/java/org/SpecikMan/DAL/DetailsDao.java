package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Entity.Mode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailsDao implements Dao<AccountLevelDetails> {
    private final Connection connection = DBConnection.getConnection();
    private final List<AccountLevelDetails> details = new ArrayList<>();

    public DetailsDao() {
    }

    public List<AccountLevelDetails> getAll() {
        try {
            String query = "select * from AccountLevelDetails, Level,Account where AccountLevelDetails.idLevel = Level.idLevel and AccountLevelDetails.idAccount = Account.idAccount"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    details.add(new AccountLevelDetails(rs.getString("idAccount"),rs.getString("username"),rs.getString("password"),
                            rs.getString("email"),rs.getString("fullName"),rs.getDate("dob"),rs.getString("verificationCode"),
                            rs.getString("uud"),rs.getString("idRole"),null,rs.getString("idLevelDetails"),
                            rs.getInt("score"),rs.getString("timeLeft"),rs.getDate("datePlayed"),rs.getBoolean("isLike"),rs.getFloat("wpm"),
                            rs.getFloat("wps"),rs.getInt("correct"),rs.getInt("wrong"),rs.getString("accuracy"),
                            new Level(rs.getString("idLevel"),rs.getString("nameLevel"),rs.getInt("numLike"),rs.getDate("createDate"),
                                    rs.getDate("updatedDate"),rs.getString("levelContent"),rs.getInt("totalWords"),rs.getString("time"),
                                    new Difficulty(rs.getString("idDifficulty"),null),new Mode(rs.getString("idMode"),null),rs.getString("idAccount"))));
                }
            }
            prepareStatement.close();
            return details;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public AccountLevelDetails get(String id) {
        try {
            String query = "select * from AccountLevelDetails, Level,Account where AccountLevelDetails.idLevel = Level.idLevel and AccountLevelDetails.idAccount = Account.idAccount and idLevelDetails = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            AccountLevelDetails detail = new AccountLevelDetails();
            while (rs.next()) {
                detail = new AccountLevelDetails(rs.getString("idAccount"),rs.getString("username"),rs.getString("password"),
                        rs.getString("email"),rs.getString("fullName"),rs.getDate("dob"),rs.getString("verificationCode"),
                        rs.getString("uud"),rs.getString("idRole"),null,rs.getString("idLevelDetails"),
                        rs.getInt("score"),rs.getString("timeLeft"),rs.getDate("datePlayed"),rs.getBoolean("isLike"),rs.getFloat("wpm"),
                        rs.getFloat("wps"),rs.getInt("correct"),rs.getInt("wrong"),rs.getString("accuracy"),
                        new Level(rs.getString("idLevel"),rs.getString("nameLevel"),rs.getInt("numLike"),rs.getDate("createDate"),
                                rs.getDate("updatedDate"),rs.getString("levelContent"),rs.getInt("totalWords"),rs.getString("time"),
                                new Difficulty(rs.getString("idDifficulty"),null),new Mode(rs.getString("idMode"),null),rs.getString("idAccount")));
            }
            prepareStatement.close();
            return detail;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(AccountLevelDetails detail) {
        try {
            String query = "insert into AccountLevelDetails values (?,?,?,?,?,?,?,?,?,?,?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, detail.getIdLevelDetails());
            prepareStatement.setString(2, detail.getIdAccount());
            prepareStatement.setString(3,detail.getLevel().getIdLevel());
            prepareStatement.setInt(4,detail.getScore());
            prepareStatement.setString(5,detail.getTimeLeft());
            prepareStatement.setDate(6,detail.getDatePlayed());
            prepareStatement.setBoolean(7,detail.isLike());
            prepareStatement.setFloat(8,detail.getWpm());
            prepareStatement.setFloat(9,detail.getWps());
            prepareStatement.setInt(10,detail.getCorrect());
            prepareStatement.setInt(11,detail.getWrong());
            prepareStatement.setString(12,detail.getAccuracy());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(AccountLevelDetails detail) {
        try {
            String query = "update AccountLevelDetails set idAccount = ?,idLevel = ?,score = ?,timeLeft = ?,datePlayed = ?,isLike = ?,wpm = ?,wps = ?,correct = ?,wrong = ?,accuracy = ? where idLevelDetails = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, detail.getIdAccount());
            prepareStatement.setString(2,detail.getLevel().getIdLevel());
            prepareStatement.setInt(3,detail.getScore());
            prepareStatement.setString(4,detail.getTimeLeft());
            prepareStatement.setDate(5,detail.getDatePlayed());
            prepareStatement.setBoolean(6,detail.isLike());
            prepareStatement.setFloat(7,detail.getWpm());
            prepareStatement.setFloat(8,detail.getWps());
            prepareStatement.setInt(9,detail.getCorrect());
            prepareStatement.setInt(10,detail.getWrong());
            prepareStatement.setString(11,detail.getAccuracy());
            //Condition
            prepareStatement.setString(12, detail.getIdLevelDetails());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(AccountLevelDetails detail) {
        try {
            String query = "delete from AccountLevelDetails where idLevelDetails = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, detail.getIdLevelDetails());
            prepareStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
