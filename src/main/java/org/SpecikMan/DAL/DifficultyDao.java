package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Difficulty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DifficultyDao implements Dao<Difficulty>{
    private final Connection connection = DBConnection.getConnection();
    private final List<Difficulty> difficulties = new ArrayList<>();

    public DifficultyDao() {
    }

    public List<Difficulty> getAll() {
        try {
            String query = "select * from Difficulty"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    difficulties.add(new Difficulty(rs.getString("idDifficulty"),rs.getString("nameDifficulty")));
                }
            }
            prepareStatement.close();
            return difficulties;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Difficulty get(String id) {
        try {
            String query = "select * Difficulty where idDifficulty = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Difficulty difficulty = new Difficulty();
            while (rs.next()) {
                difficulty = new Difficulty(rs.getString("idDifficulty"), rs.getString("nameDifficulty"));
            }
            prepareStatement.close();
            return difficulty;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Difficulty difficulty) {
        try {
            String query = "insert into Difficulty values (?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, difficulty.getIdDifficulty());
            prepareStatement.setString(2, difficulty.getNameDifficulty());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Difficulty difficulty) {
        try {
            String query = "update Difficulty set name = ? where idDifficulty = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, difficulty.getNameDifficulty());
            //Condition
            prepareStatement.setString(2, difficulty.getIdDifficulty());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Difficulty difficulty) {
        try {
            String query = "delete from Difficulty where idDifficulty = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, difficulty.getIdDifficulty());
            prepareStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
