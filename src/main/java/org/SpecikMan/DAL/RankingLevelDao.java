package org.SpecikMan.DAL;

import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RankingLevelDao implements Dao<RankingLevel> {
    private final String url = apiURL.getApiURL() + "/rkLv";
    @Override
    public List<RankingLevel> getAll() {
        try {
            List<RankingLevel> levels = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                levels.add(new RankingLevel(
                        obj.getString("idRankingLevel").equals("null") ? null : obj.getString("idRankingLevel"),
                        obj.getString("createDate").equals("null") ? null : Date.valueOf(obj.getString("createDate")),
                        obj.getString("fromRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("fromRankPeriod")),
                        obj.getString("toRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("toRankPeriod")),
                        obj.getString("levelContent1").equals("null") ? null : obj.getString("levelContent1"),
                        obj.getString("levelContent2").equals("null") ? null : obj.getString("levelContent2")
                ));
            }
            return levels;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RankingLevel get(String id) {
        try {
            List<RankingLevel> levels = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                levels.add(new RankingLevel(
                        obj.getString("idRankingLevel").equals("null") ? null : obj.getString("idRankingLevel"),
                        obj.getString("createDate").equals("null") ? null : Date.valueOf(obj.getString("createDate")),
                        obj.getString("fromRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("fromRankPeriod")),
                        obj.getString("toRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("toRankPeriod")),
                        obj.getString("levelContent1").equals("null") ? null : obj.getString("levelContent1"),
                        obj.getString("levelContent2").equals("null") ? null : obj.getString("levelContent2")
                ));
            }
            return levels.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(RankingLevel rl) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idRankingLevel", rl.getIdRankingLevel());
            jsonObject.put("createDate", rl.getCreateDate());
            jsonObject.put("fromRankPeriod", rl.getFromRankPeriod());
            jsonObject.put("toRankPeriod", rl.getToRankPeriod());
            jsonObject.put("levelContent1", rl.getLevelContent1());
            jsonObject.put("levelContent2", rl.getLevelContent2());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RankingLevel rl) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idRankingLevel", rl.getIdRankingLevel());
            jsonObject.put("createDate", rl.getCreateDate());
            jsonObject.put("fromRankPeriod", rl.getFromRankPeriod());
            jsonObject.put("toRankPeriod", rl.getToRankPeriod());
            jsonObject.put("levelContent1", rl.getLevelContent1());
            jsonObject.put("levelContent2", rl.getLevelContent2());
            crudAPI.put(jsonObject, url + "/" + rl.getIdRankingLevel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(RankingLevel rl) {
        try {
            crudAPI.delete(url + "/" + rl.getIdRankingLevel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
