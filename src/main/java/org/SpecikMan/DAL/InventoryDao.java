package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Inventory;
import org.SpecikMan.Entity.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao implements Dao<Inventory>{
    private final Connection connection = DBConnection.getConnection();
    private final List<Inventory> items = new ArrayList<>();

    public InventoryDao() {
    }

    public List<Inventory> getAll() {
        try {
            String query = "select * from Inventory,Account,Shop where Inventory.idItem = Shop.idItem and Inventory.idAccount = Account.idAccount"; //SQL Query
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    items.add(new Inventory(rs.getString("idAccount"),rs.getString("username"),rs.getString("idInventory"),
                            new Shop(rs.getString("idItem"),rs.getString("itemName"),rs.getString("description"),
                                    rs.getInt("cost"),rs.getInt("maxLimit"),rs.getString("imagePath"),rs.getInt("timeUsed"),rs.getString("tips"),
                                    rs.getString("effectsBy")),rs.getInt("currentlyHave"),rs.getInt("timeUsed")));
                }
            }
            prepareStatement.close();
            return items;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Inventory get(String id) {
        try {
            String query = "select * from Inventory,Account,Shop where Inventory.idItem = Shop.idItem and Inventory.idAccount = Account.idAccount and Inventory.idInventory = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, id);
            ResultSet rs = prepareStatement.executeQuery();
            Inventory inventory = new Inventory();
            while (rs.next()) {
                inventory = new Inventory(rs.getString("idAccount"),rs.getString("username"),rs.getString("idInventory"),
                        new Shop(rs.getString("idItem"),rs.getString("itemName"),rs.getString("description"),
                                rs.getInt("cost"),rs.getInt("maxLimit"),rs.getString("imagePath"),rs.getInt("timeUsed"),rs.getString("tips"),
                                rs.getString("effectsBy")),rs.getInt("currentlyHave"),rs.getInt("timeUsed"));
            }
            prepareStatement.close();
            return inventory;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Inventory inventory) {
        try {
            String query = "insert into Inventory values (?,?,?,?,?)"; //Full name - Dob null
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, inventory.getIdInventory());
            prepareStatement.setString(2, inventory.getIdAccount());
            prepareStatement.setString(3,inventory.getItem().getIdItem());
            prepareStatement.setInt(4, inventory.getCurrentlyHave());
            prepareStatement.setInt(5, inventory.getTimeUsed());
            prepareStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Inventory inventory) {
        try {
            String query = "update Inventory set idAccount = ?,idItem = ?, currentlyHave = ?, timeUsed = ? where idInventory = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, inventory.getIdAccount());
            prepareStatement.setString(2,inventory.getItem().getIdItem());
            prepareStatement.setInt(3,inventory.getCurrentlyHave());
            prepareStatement.setInt(4,inventory.getTimeUsed());
            //Condition
            prepareStatement.setString(5, inventory.getIdInventory());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Inventory inventory) {
        try {
            String query = "delete from Inventory where idInventory = ?";
            assert connection != null;
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            //Condition
            prepareStatement.setString(1, inventory.getIdInventory());
            prepareStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
