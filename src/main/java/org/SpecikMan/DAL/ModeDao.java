package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.Mode;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeDao implements Dao<Mode>{
    private final String url = apiURL.getApiURL() + "/mode";

    public ModeDao() {
    }

    public List<Mode> getAll() {
        try {
            List<Mode> diffs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                diffs.add(new Mode(
                        obj.getString("idMode"),
                        obj.getString("nameMode")
                ));
            }
            return diffs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Mode get(String id) {
        try {
            List<Mode> diffs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                diffs.add(new Mode(
                        obj.getString("idMode"),
                        obj.getString("nameMode")
                ));
            }
            return diffs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Mode m) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idMode", m.getIdMode());
            jsonObject.put("nameMode", m.getNameMode());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Mode m) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idMode", m.getIdMode());
            jsonObject.put("nameMode", m.getNameMode());
            crudAPI.put(jsonObject, url + "/" + m.getIdMode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Mode m) {
        try {
            crudAPI.delete(url + "/" + m.getIdMode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
