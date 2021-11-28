package org.SpecikMan.DAL;

import javafx.fxml.FXML;
import javafx.scene.input.InputMethodEvent;
import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LevelDao implements Dao<Level> {

    private final String url = apiURL.getApiURL() + "/level";
    public LevelDao() {
    }

    public List<Level> getAll() {
        try {
            List<Level> levels = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                levels.add(new Level(
                        obj.getString("idLevel"),
                        obj.getString("nameLevel").equals("null") ? null : obj.getString("nameLevel"),
                        obj.getString("numLike").equals("null") ? null : obj.getInt("numLike"),
                        obj.getString("createDate").equals("null") ? null : Date.valueOf(obj.getString("createDate")),
                        obj.getString("updatedDate").equals("null") ? null : Date.valueOf(obj.getString("updatedDate")),
                        obj.getString("levelContent").equals("null") ? null : obj.getString("levelContent"),
                        obj.getString("totalWords").equals("null") ? null : obj.getInt("totalWords"),
                        obj.getString("time").equals("null") ? null : obj.getString("time"),
                        new Difficulty(
                                obj.getString("idDifficulty").equals("null") ? null : obj.getString("idDifficulty"),
                                obj.getString("nameDifficulty").equals("null") ? null : obj.getString("nameDifficulty")
                        ),
                        new Mode(
                                obj.getString("idMode").equals("null") ? null : obj.getString("idMode"),
                                obj.getString("nameMode").equals("null") ? null : obj.getString("nameMode")
                        ),
                        obj.getString("idPublisher").equals("null") ? null : obj.getString("idPublisher"),
                        obj.getString("username").equals("null") ? null : obj.getString("username")
                ));
            }
            return levels;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Level get(String id) {
        try {
            List<Level> levels = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                levels.add(new Level(
                        obj.getString("idLevel"),
                        obj.getString("nameLevel").equals("null") ? null : obj.getString("nameLevel"),
                        obj.getString("numLike").equals("null") ? null : obj.getInt("numLike"),
                        obj.getString("createDate").equals("null") ? null : Date.valueOf(obj.getString("createDate")),
                        obj.getString("updatedDate").equals("null") ? null : Date.valueOf(obj.getString("updatedDate")),
                        obj.getString("levelContent").equals("null") ? null : obj.getString("levelContent"),
                        obj.getString("totalWords").equals("null") ? null : obj.getInt("totalWords"),
                        obj.getString("time").equals("null") ? null : obj.getString("time"),
                        new Difficulty(
                                obj.getString("idDifficulty").equals("null") ? null : obj.getString("idDifficulty"),
                                obj.getString("nameDifficulty").equals("null") ? null : obj.getString("nameDifficulty")
                        ),
                        new Mode(
                                obj.getString("idMode").equals("null") ? null : obj.getString("idMode"),
                                obj.getString("nameMode").equals("null") ? null : obj.getString("nameMode")
                        ),
                        obj.getString("idPublisher").equals("null") ? null : obj.getString("idPublisher"),
                        obj.getString("username").equals("null") ? null : obj.getString("username")
                ));
            }
            return levels.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Level l) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idLevel", l.getIdLevel());
            jsonObject.put("nameLevel", l.getNameLevel());
            jsonObject.put("numLike", l.getNumLike());
            jsonObject.put("createDate", l.getCreateDate());
            jsonObject.put("updatedDate", l.getUpdatedDate());
            jsonObject.put("levelContent", l.getLevelContent());
            jsonObject.put("totalWords", l.getTotalWords());
            jsonObject.put("time", l.getTime());
            jsonObject.put("idDifficulty", l.getDifficulty().getIdDifficulty());
            jsonObject.put("idMode", l.getMode().getIdMode());
            jsonObject.put("idPublisher", l.getIdAccount());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Level l) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idLevel", l.getIdLevel());
            jsonObject.put("nameLevel", l.getNameLevel());
            jsonObject.put("numLike", l.getNumLike());
            jsonObject.put("createDate", l.getCreateDate());
            jsonObject.put("updatedDate", l.getUpdatedDate());
            jsonObject.put("levelContent", l.getLevelContent());
            jsonObject.put("totalWords", l.getTotalWords());
            jsonObject.put("time", l.getTime());
            jsonObject.put("idDifficulty", l.getDifficulty().getIdDifficulty());
            jsonObject.put("idMode", l.getMode().getIdMode());
            jsonObject.put("idPublisher", l.getIdAccount());
            crudAPI.put(jsonObject, url + "/" + l.getIdLevel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Level l) {
        try {
            crudAPI.delete(url + "/" + l.getIdLevel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
