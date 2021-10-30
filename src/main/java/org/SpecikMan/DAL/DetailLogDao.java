package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.DetailLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailLogDao implements Dao<DetailLog>{
    private final Connection connection = DBConnection.getConnection();
    private final List<DetailLog> logs = new ArrayList<>();

    public DetailLogDao() {
    }

    public List<DetailLog> getAll() {
        try {
            String query = "select * from DetailLog"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    logs.add(new DetailLog(rs.getString("idLog"),rs.getString("idLevel"),rs.getString("levelName"),rs.getString("idPublisher"),
                            rs.getString("publisherName"),rs.getString("idPlayer"),rs.getString("playerName"),
                            rs.getInt("score"),rs.getFloat("wpm"),rs.getFloat("wps"),rs.getInt("correct"),
                            rs.getInt("wrong"),rs.getString("accuracy"),rs.getString("timeLeft"),rs.getDate("datePlayed")));
                }
            }
            prepareStatement.close();
            return logs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public DetailLog get(String id) {
        try {
            String query = "select * from DetailLog where idLog = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            DetailLog log = new DetailLog();
            while (rs.next()) {
                log = new DetailLog(rs.getString("idLog"),rs.getString("idLevel"),rs.getString("levelName"),rs.getString("idPublisher"),
                        rs.getString("publisherName"),rs.getString("idPlayer"),rs.getString("playerName"),
                        rs.getInt("score"),rs.getFloat("wpm"),rs.getFloat("wps"),rs.getInt("correct"),
                        rs.getInt("wrong"),rs.getString("accuracy"),rs.getString("timeLeft"),rs.getDate("datePlayed"));
            }
            prepareStatement.close();
            return log;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(DetailLog log) {
        try {
            String query = "insert into DetailLog values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, log.getIdLog());
            prepareStatement.setString(2, log.getIdLevel());
            prepareStatement.setString(3, log.getLevelName());
            prepareStatement.setString(4, log.getIdPublisher());
            prepareStatement.setString(5,log.getPublisherName());
            prepareStatement.setString(6,log.getIdPlayer());
            prepareStatement.setString(7,log.getPlayerName());
            prepareStatement.setInt(8, log.getScore());
            prepareStatement.setFloat(9, log.getWpm());
            prepareStatement.setFloat(10, log.getWps());
            prepareStatement.setInt(11, log.getCorrect());
            prepareStatement.setInt(12, log.getWrong());
            prepareStatement.setString(13, log.getAccuracy());
            prepareStatement.setString(14, log.getTimeLeft());
            prepareStatement.setDate(15, log.getDatePlayed());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(DetailLog detailLog) {
    }

    public void delete(DetailLog log) {
        try {
            String query = "delete from DetailLog where idLog = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, log.getIdLog());
            prepareStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
