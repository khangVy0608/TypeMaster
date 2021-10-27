package org.SpecikMan.DAL;

import javafx.fxml.FXML;
import javafx.scene.input.InputMethodEvent;
import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Entity.Mode;
import org.SpecikMan.Entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LevelDao implements Dao<Level> {
    private final Connection connection = DBConnection.getConnection();
    private final List<Level> levels = new ArrayList<>();

    public LevelDao() {
    }

    public List<Level> getAll() {
        try {
            String query = "select * from Level,Account,Mode,Difficulty where Level.idMode = Mode.idMode and Level.idDifficulty = Difficulty.idDifficulty and Level.idPublisher = Account.idAccount"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    levels.add(new Level(rs.getString("idLevel"),rs.getString("nameLevel"),rs.getInt("numLike"),rs.getDate("createDate"),
                            rs.getDate("updatedDate"),rs.getString("levelContent"),rs.getInt("totalWords"),rs.getString("time"),
                            new Difficulty(rs.getString("idDifficulty"),rs.getString("nameDifficulty")),new Mode(rs.getString("idMode"),rs.getString("nameMode")),
                            rs.getString("idPublisher")));
                }
            }
            prepareStatement.close();
            return levels;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Level get(String id) {
        try {
            String query = "select * from Level,Account,Mode,Difficulty where Level.idMode = Mode.idMode and Level.idDifficulty = Difficulty.idDifficulty and Level.idPublisher = Account.idAccount and idLevel = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Level level = new Level();
            while (rs.next()) {
                level = new Level(rs.getString("idLevel"),rs.getString("nameLevel"),rs.getInt("numLike"),rs.getDate("createDate"),
                        rs.getDate("updatedDate"),rs.getString("levelContent"),rs.getInt("totalWords"),rs.getString("time"),
                        new Difficulty(rs.getString("idDifficulty"),rs.getString("nameDifficulty")),new Mode(rs.getString("idMode"),rs.getString("nameMode")),
                        rs.getString("idPublisher"));
            }
            prepareStatement.close();
            return level;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Level level) {
        try {
            String query = "insert into Level values (?,?,?,?,?,?,?,?,?,?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, level.getIdLevel());
            prepareStatement.setString(2, level.getNameLevel());
            prepareStatement.setInt(3,level.getNumLike());
            prepareStatement.setDate(4,level.getCreateDate());
            prepareStatement.setDate(5,level.getUpdatedDate());
            prepareStatement.setString(6,level.getLevelContent());
            prepareStatement.setInt(7,level.getTotalWords());
            prepareStatement.setString(8,level.getTime());
            prepareStatement.setString(9,level.getDifficulty().getIdDifficulty());
            prepareStatement.setString(10,level.getMode().getIdMode());
            prepareStatement.setString(11,level.getIdAccount());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Level level) {
        try {
            String query = "update Level set nameLevel = ?,numLike = ?,createDate = ?,updatedDate = ?,levelContent = ?,totalWords = ?,time = ?,idDifficulty = ?,idMode = ?,idPublisher = ? where idLevel = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, level.getNameLevel());
            prepareStatement.setInt(2,level.getNumLike());
            prepareStatement.setDate(3,level.getCreateDate());
            prepareStatement.setDate(4,level.getUpdatedDate());
            prepareStatement.setString(5,level.getLevelContent());
            prepareStatement.setInt(6,level.getTotalWords());
            prepareStatement.setString(7,level.getTime());
            prepareStatement.setString(8,level.getDifficulty().getIdDifficulty());
            prepareStatement.setString(9,level.getMode().getIdMode());
            prepareStatement.setString(10,level.getIdAccount());
            //Condition
            prepareStatement.setString(11, level.getIdLevel());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Level level) {
        try {
            String query = "delete from Level where idLevel = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, level.getIdLevel());
            prepareStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
