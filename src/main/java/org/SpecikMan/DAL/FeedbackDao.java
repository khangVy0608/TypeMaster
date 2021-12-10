package org.SpecikMan.DAL;

import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.Feedback;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao implements Dao<Feedback> {
    private final String url = apiURL.getApiURL() + "/fb";
    @Override
    public List<Feedback> getAll() {
        try {
            List<Feedback> fbs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                fbs.add(new Feedback(
                        obj.getString("idAccount"),
                        obj.getString("username"),
                        obj.getString("idFeedback"),
                        obj.getString("submitDetail"),
                        Date.valueOf(obj.getString("submitDate"))
                ));
            }
            return fbs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Feedback get(String id) {
        try {
            List<Feedback> fbs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                fbs.add(new Feedback(
                        obj.getString("idAccount"),
                        obj.getString("username"),
                        obj.getString("idFeedback"),
                        obj.getString("submitDetail"),
                        Date.valueOf(obj.getString("submitDate"))
                ));
            }
            return fbs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Feedback f) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idFeedback", f.getIdFeedback());
            jsonObject.put("submitDetail", f.getSubmitDetail());
            jsonObject.put("submitDate", f.getSubmitDate());
            jsonObject.put("idAccount", f.getIdAccount());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Feedback f) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idFeedback", f.getIdFeedback());
            jsonObject.put("submitDetail", f.getSubmitDetail());
            jsonObject.put("submitDate", f.getSubmitDate());
            jsonObject.put("idAccount", f.getIdAccount());
            crudAPI.put(jsonObject, url + "/" + f.getIdFeedback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Feedback f) {
        try {
            crudAPI.delete(url + "/" + f.getIdFeedback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
