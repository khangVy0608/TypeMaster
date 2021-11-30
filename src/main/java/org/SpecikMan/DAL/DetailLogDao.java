package org.SpecikMan.DAL;

import org.SpecikMan.DB.DBConnection;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.DetailLog;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailLogDao implements Dao<DetailLog>{
    private final String url = apiURL.getApiURL() + "/log";
    public DetailLogDao() {
    }

    public List<DetailLog> getAll() {
        try {
            List<DetailLog> logs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url+"s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                logs.add(new DetailLog(
                        obj.getString("idLog"),
                        obj.getString("idLevel").equals("null")?null:obj.getString("idLevel"),
                        obj.getString("levelName").equals("null") ? null : obj.getString("levelName"),
                        obj.getString("idPublisher").equals("null") ? null : obj.getString("idPublisher"),
                        obj.getString("publisherName").equals("null")?null:obj.getString("publisherName"),
                        obj.getString("idPlayer").equals("null")?null:obj.getString("idPlayer"),
                        obj.getString("playerName").equals("null")?null:obj.getString("playerName"),
                        obj.getString("score").equals("null")?null:obj.getLong("score"),
                        obj.getString("wpm").equals("null")?null:Float.valueOf(obj.getString("wpm")),
                        obj.getString("wps").equals("null")?null:Float.valueOf(obj.getString("wps")),
                        obj.getString("correct").equals("null")?null:obj.getInt("correct"),
                        obj.getString("wrong").equals("null")?null:obj.getInt("wrong"),
                        obj.getString("accuracy").equals("null")?null:obj.getString("accuracy"),
                        obj.getString("timeLeft").equals("null")?null:obj.getString("timeLeft"),
                        obj.getString("datePlayed").equals("null")?null:Date.valueOf(obj.getString("datePlayed")),
                        obj.getString("chartData").equals("null")?null:obj.getString("chartData")
                ));
            }
            return logs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DetailLog get(String id) {
        try {
            List<DetailLog> logs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id.trim()));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                logs.add(new DetailLog(
                        obj.getString("idLog"),
                        obj.getString("idLevel").equals("null")?null:obj.getString("idLevel"),
                        obj.getString("levelName").equals("null") ? null : obj.getString("levelName"),
                        obj.getString("idPublisher").equals("null") ? null : obj.getString("idPublisher"),
                        obj.getString("publisherName").equals("null")?null:obj.getString("publisherName"),
                        obj.getString("idPlayer").equals("null")?null:obj.getString("idPlayer"),
                        obj.getString("playerName").equals("null")?null:obj.getString("playerName"),
                        obj.getString("score").equals("null")?null:obj.getLong("score"),
                        obj.getString("wpm").equals("null")?null:Float.valueOf(obj.getString("wpm")),
                        obj.getString("wps").equals("null")?null:Float.valueOf(obj.getString("wps")),
                        obj.getString("correct").equals("null")?null:obj.getInt("correct"),
                        obj.getString("wrong").equals("null")?null:obj.getInt("wrong"),
                        obj.getString("accuracy").equals("null")?null:obj.getString("accuracy"),
                        obj.getString("timeLeft").equals("null")?null:obj.getString("timeLeft"),
                        obj.getString("datePlayed").equals("null")?null:Date.valueOf(obj.getString("datePlayed")),
                        obj.getString("chartData").equals("null")?null:obj.getString("chartData")
                ));
            }
            return logs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(DetailLog log) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idLog", log.getIdLog());
            jsonObject.put("idLevel", log.getIdLevel());
            jsonObject.put("levelName", log.getLevelName());
            jsonObject.put("idPublisher", log.getIdPublisher());
            jsonObject.put("publisherName", log.getPublisherName());
            jsonObject.put("idPlayer", log.getIdPlayer());
            jsonObject.put("playerName",log.getPlayerName());
            jsonObject.put("score", log.getScore());
            jsonObject.put("wpm", log.getWpm());
            jsonObject.put("wps", log.getWps());
            jsonObject.put("correct", log.getCorrect());
            jsonObject.put("wrong", log.getWrong());
            jsonObject.put("accuracy", log.getAccuracy());
            jsonObject.put("timeLeft", log.getTimeLeft());
            jsonObject.put("datePlayed",  log.getDatePlayed());
            jsonObject.put("chartData",log.getChartData());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(DetailLog log) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idLog", log.getIdLog());
            jsonObject.put("idLevel", log.getIdLevel());
            jsonObject.put("levelName", log.getLevelName());
            jsonObject.put("idPublisher", log.getIdPublisher());
            jsonObject.put("publisherName", log.getPublisherName());
            jsonObject.put("idPlayer", log.getIdPlayer());
            jsonObject.put("playerName",log.getPlayerName());
            jsonObject.put("score", log.getScore());
            jsonObject.put("wpm", log.getWpm());
            jsonObject.put("wps", log.getWps());
            jsonObject.put("correct", log.getCorrect());
            jsonObject.put("wrong", log.getWrong());
            jsonObject.put("accuracy", log.getAccuracy());
            jsonObject.put("timeLeft", log.getTimeLeft());
            jsonObject.put("datePlayed",  log.getDatePlayed());
            jsonObject.put("chartData",log.getChartData());
            crudAPI.put(jsonObject, url + "/" + log.getIdLog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(DetailLog log) {
        try {
            crudAPI.delete(url + "/" + log.getIdLog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
