package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Mode;
import org.SpecikMan.Entity.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDao implements Dao<Shop> {
    private final Connection connection = DBConnection.getConnection();
    private final List<Shop> items = new ArrayList<>();

    public ShopDao() {
    }

    public List<Shop> getAll() {
        try {
            String query = "select * from Shop"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    items.add(new Shop(rs.getString("idItem"),rs.getString("itemName"),rs.getString("description"),
                            rs.getInt("cost"),rs.getInt("maxLimit"),rs.getString("imagePath"),rs.getInt("timeUsed"),rs.getString("tips"),
                            rs.getString("effectsBy")));
                }
            }
            prepareStatement.close();
            return items;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Shop get(String id) {
        try {
            String query = "select * Shop where idItem = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Shop shop = new Shop();
            while (rs.next()) {
                shop = new Shop(rs.getString("idItem"),rs.getString("itemName"),rs.getString("description"),
                        rs.getInt("cost"),rs.getInt("maxLimit"),rs.getString("imagePath"),rs.getInt("timeUsed"),rs.getString("tips"),
                        rs.getString("effectsBy"));
            }
            prepareStatement.close();
            return shop;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Shop shop) {
        try {
            String query = "insert into Shop values (?,?,?,?,?,?,?,?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, shop.getIdItem());
            prepareStatement.setString(2, shop.getItemName());
            prepareStatement.setString(3,shop.getDescription());
            prepareStatement.setInt(4, shop.getCost());
            prepareStatement.setInt(5,shop.getMaxLimit());
            prepareStatement.setString(6,shop.getImagePath());
            prepareStatement.setInt(7, shop.getTimeUsed());
            prepareStatement.setString(8,shop.getTips());
            prepareStatement.setString(9, shop.getEffectsBy());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Shop shop) {
        try {
            String query = "update Shop set nameShop = ?,description = ?,cost = ?,maxLimit = ?,imagePath = ?,timeUsed = ?,tips = ?,effectsBy = ? where idItem = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, shop.getItemName());
            prepareStatement.setString(2,shop.getDescription());
            prepareStatement.setInt(3,shop.getCost());
            prepareStatement.setInt(4,shop.getMaxLimit());
            prepareStatement.setString(5,shop.getImagePath());
            prepareStatement.setInt(6, shop.getTimeUsed());
            prepareStatement.setString(7,shop.getTips());
            prepareStatement.setString(8, shop.getEffectsBy());
            //Condition
            prepareStatement.setString(9, shop.getIdItem());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Shop shop) {
        try {
            String query = "delete from Mode where idItem = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, shop.getIdItem());
            prepareStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
