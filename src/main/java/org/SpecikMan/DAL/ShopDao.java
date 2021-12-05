package org.SpecikMan.DAL;


import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.Mode;
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

public class ShopDao implements Dao<Shop> {
    private final String url = apiURL.getApiURL() + "/item";

    public ShopDao() {
    }

    public List<Shop> getAll() {
        try {
            List<Shop> accounts = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                accounts.add(new Shop(
                        obj.getString("idItem"),
                        obj.getString("itemName"),
                        obj.getString("description").equals("null") ? null : obj.getString("description"),
                        obj.getString("cost").equals("null") ? null : obj.getInt("cost"),
                        obj.getString("maxLimit").equals("null") ? null : obj.getInt("maxLimit"),
                        obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                        obj.getString("timeUsed").equals("null") ? null : obj.getInt("timeUsed"),
                        obj.getString("tips").equals("null") ? null : obj.getString("tips"),
                        obj.getString("effectsBy").equals("null") ? null : obj.getString("effectsBy")
                ));
            }
            return accounts;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Shop get(String id) {
        try {
            List<Shop> accounts = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                accounts.add(new Shop(
                        obj.getString("idItem"),
                        obj.getString("itemName"),
                        obj.getString("description").equals("null") ? null : obj.getString("description"),
                        obj.getString("cost").equals("null") ? null : obj.getInt("cost"),
                        obj.getString("maxLimit").equals("null") ? null : obj.getInt("maxLimit"),
                        obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                        obj.getString("timeUsed").equals("null") ? null : obj.getInt("timeUsed"),
                        obj.getString("tips").equals("null") ? null : obj.getString("tips"),
                        obj.getString("effectsBy").equals("null") ? null : obj.getString("effectsBy")
                ));
            }
            return accounts.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Shop s) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idItem", s.getIdItem());
            jsonObject.put("itemName", s.getItemName());
            jsonObject.put("description", s.getDescription());
            jsonObject.put("cost", s.getCost());
            jsonObject.put("maxLimit", s.getMaxLimit());
            jsonObject.put("imagePath", s.getImagePath());
            jsonObject.put("timeUsed", s.getTimeUsed());
            jsonObject.put("tips", s.getTips());
            jsonObject.put("effectsBy", s.getEffectsBy());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Shop s) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idItem", s.getIdItem());
            jsonObject.put("itemName", s.getItemName());
            jsonObject.put("description", s.getDescription());
            jsonObject.put("cost", s.getCost());
            jsonObject.put("maxLimit", s.getMaxLimit());
            jsonObject.put("imagePath", s.getImagePath());
            jsonObject.put("timeUsed", s.getTimeUsed());
            jsonObject.put("tips", s.getTips());
            jsonObject.put("effectsBy", s.getEffectsBy());
            crudAPI.put(jsonObject, url + "/" + s.getIdItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Shop s) {
        try {
            crudAPI.delete(url + "/" + s.getIdItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
