package org.SpecikMan.DAL;

import org.SpecikMan.Entity.Mode;
import org.SpecikMan.Entity.Role;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.GenerateID;
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

public class RoleDao implements Dao<Role>{
    private final String url = apiURL.getApiURL() + "/role";

    public RoleDao() {
    }

    public List<Role> getAll() {
        try {
            List<Role> diffs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                diffs.add(new Role(
                        obj.getString("idRole"),
                        obj.getString("nameRole")
                ));
            }
            return diffs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Role get(String id) {
        try {
            List<Role> diffs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                diffs.add(new Role(
                        obj.getString("idRole"),
                        obj.getString("nameRole")
                ));
            }
            return diffs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Role r) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idRole", r.getIdRole());
            jsonObject.put("nameRole", r.getNameRole());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Role r) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idRole", r.getIdRole());
            jsonObject.put("nameRole", r.getNameRole());
            crudAPI.put(jsonObject, url + "/" + r.getIdRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Role r) {
        try {
            crudAPI.delete(url + "/" + r.getIdRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
