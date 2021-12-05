package org.SpecikMan.DAL;

import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.Inventory;
import org.SpecikMan.Entity.Shop;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao implements Dao<Inventory>{
    private final String url = apiURL.getApiURL() + "/inv";
    public InventoryDao() {
    }

    public List<Inventory> getAll() {
        try {
            List<Inventory> invs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                invs.add(new Inventory(
                        obj.getString("idAccount"),
                        obj.getString("username"),
                        obj.getString("idInventory").equals("null") ? null : obj.getString("idInventory"),
                        new Shop(
                                obj.getString("idItem")==null ?null:obj.getString("idItem"),
                                obj.getString("itemName")==null?null:obj.getString("itemName"),
                                obj.getString("description")==null?null:obj.getString("description"),
                                obj.getString("cost")==null?null:obj.getInt("cost"),
                                obj.getString("maxLimit")==null?null:obj.getInt("maxLimit"),
                                obj.getString("imagePath")==null?null:obj.getString("imagePath"),
                                obj.getString("ShopTimeUsed")==null?null:obj.getInt("ShopTimeUsed"),
                                obj.getString("tips")==null?null:obj.getString("tips"),
                                obj.getString("effectsBy")==null?null:obj.getString("effectsBy")
                        ),
                        obj.getString("currentlyHave").equals("null") ? null : obj.getInt("currentlyHave"),
                        obj.getString("InvTimeUsed").equals("null") ? null : obj.getInt("InvTimeUsed")
                ));
            }
            return invs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Inventory get(String id) {
        try {
            List<Inventory> invs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/"+id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                invs.add(new Inventory(
                        obj.getString("idAccount"),
                        obj.getString("username"),
                        obj.getString("idInventory").equals("null") ? null : obj.getString("idInventory"),
                        new Shop(
                                obj.getString("idItem")==null ?null:obj.getString("idItem"),
                                obj.getString("itemName")==null?null:obj.getString("itemName"),
                                obj.getString("description")==null?null:obj.getString("description"),
                                obj.getString("cost")==null?null:obj.getInt("cost"),
                                obj.getString("maxLimit")==null?null:obj.getInt("maxLimit"),
                                obj.getString("imagePath")==null?null:obj.getString("imagePath"),
                                obj.getString("ShopTimeUsed")==null?null:obj.getInt("ShopTimeUsed"),
                                obj.getString("tips")==null?null:obj.getString("tips"),
                                obj.getString("effectsBy")==null?null:obj.getString("effectsBy")
                        ),
                        obj.getString("currentlyHave").equals("null") ? null : obj.getInt("currentlyHave"),
                        obj.getString("InvTimeUsed").equals("null") ? null : obj.getInt("InvTimeUsed")
                ));
            }
            return invs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Inventory i) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idInventory", i.getIdInventory());
            jsonObject.put("idAccount", i.getIdAccount());
            jsonObject.put("idItem", i.getItem().getIdItem());
            jsonObject.put("currentlyHave", i.getCurrentlyHave());
            jsonObject.put("timeUsed", i.getTimeUsed());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Inventory i) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idInventory", i.getIdInventory());
            jsonObject.put("idAccount", i.getIdAccount());
            jsonObject.put("idItem", i.getItem().getIdItem());
            jsonObject.put("currentlyHave", i.getCurrentlyHave());
            jsonObject.put("timeUsed", i.getTimeUsed());
            crudAPI.put(jsonObject, url + "/" + i.getIdInventory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Inventory i) {
        try {
            crudAPI.delete(url + "/" + i.getIdInventory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
