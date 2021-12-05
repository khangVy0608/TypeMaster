package org.SpecikMan.DAL;

import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DifficultyDao implements Dao<Difficulty>{
    private final String url = apiURL.getApiURL() + "/diff";

    public DifficultyDao() {
    }

    public List<Difficulty> getAll() {
        try {
            List<Difficulty> diffs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                diffs.add(new Difficulty(
                        obj.getString("idDifficulty"),
                        obj.getString("nameDifficulty")
                ));
            }
            return diffs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Difficulty get(String id) {
        try {
            List<Difficulty> diffs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/"+id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                diffs.add(new Difficulty(
                        obj.getString("idDifficulty"),
                        obj.getString("nameDifficulty")
                ));
            }
            return diffs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Difficulty d) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idDifficulty", d.getIdDifficulty());
            jsonObject.put("nameDifficulty", d.getNameDifficulty());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Difficulty d) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idDifficulty", d.getIdDifficulty());
            jsonObject.put("nameDifficulty", d.getNameDifficulty());
            crudAPI.put(jsonObject, url + "/" + d.getIdDifficulty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Difficulty d) {
        try {
            crudAPI.delete(url + "/" + d.getIdDifficulty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
