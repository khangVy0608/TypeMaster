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

public class DetailsDao implements Dao<AccountLevelDetails> {
    private final String url = apiURL.getApiURL() + "/detail";

    public DetailsDao() {

    }

    public List<AccountLevelDetails> getAll() {
        try {
            List<AccountLevelDetails> details = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                details.add(new AccountLevelDetails(
                        obj.getString("idAccount") == "null" ? null : obj.getString("idAccount"),
                        obj.getString("username") == "null" ? null : obj.getString("username"),
                        obj.getString("password") == "null" ? null : obj.getString("password"),
                        obj.getString("email") == "null" ? null : obj.getString("email"),
                        obj.getString("fullName") == "null" ? null : obj.getString("fullName"),
                        obj.getString("dob") == "null" ? null : Date.valueOf(obj.getString("dob")),
                        obj.getString("verificationCode") == "null" ? null : obj.getString("verificationCode"),
                        obj.getString("uud") == "null" ? null : obj.getString("uud"),
                        obj.getString("idRole") == "null" ? null : obj.getString("idRole"),
                        null,
                        obj.getString("idLevelDetails") == "null" ? null : obj.getString("idLevelDetails"),
                        obj.getString("score") == "null" ? null : obj.getLong("score"),
                        obj.getString("timeLeft") == "null" ? null : obj.getString("timeLeft"),
                        obj.getString("datePlayed") == "null" ? null : Date.valueOf(obj.getString("datePlayed")),
                        obj.getString("isLike") == "null" ? null : obj.getBoolean("isLike"),
                        obj.getString("wpm") == "null" ? null : Float.valueOf(obj.getString("wpm")),
                        obj.getString("wps") == "null" ? null : Float.valueOf(obj.getString("wps")),
                        obj.getString("correct") == "null" ? null : Integer.valueOf(obj.getString("correct")),
                        obj.getString("wrong") == "null" ? null : Integer.valueOf(obj.getString("wrong")),
                        obj.getString("accuracy") == "null" ? null : obj.getString("accuracy"),
                        new Level(
                                obj.getString("idLevel") == "null" ? null : obj.getString("idLevel"),
                                obj.getString("nameLevel") == "null" ? null : obj.getString("nameLevel"),
                                obj.getString("numLike") == "null" ? null : obj.getInt("numLike"),
                                obj.getString("createDate") == "null" ? null : Date.valueOf(obj.getString("createDate")),
                                obj.getString("updatedDate") == "null" ? null : Date.valueOf(obj.getString("updatedDate")),
                                obj.getString("levelContent") == "null" ? null : obj.getString("levelContent"),
                                obj.getString("totalWords") == "null" ? null : obj.getInt("totalWords"),
                                obj.getString("time") == "null" ? null : obj.getString("time"),
                                new Difficulty(
                                        obj.getString("idDifficulty") == "null" ? null : obj.getString("idDifficulty"),
                                        null
                                ),
                                new Mode(
                                        obj.getString("idMode") == "null" ? null : obj.getString("idMode"),
                                        null
                                ),
                                obj.getString("idPublisher") == "null" ? null : obj.getString("idPublisher"),
                                null
                        ),
                        obj.getString("chartData") == "null" ? null : obj.getString("chartData")
                ));
            }
            return details;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AccountLevelDetails get(String id) {
        try {
            List<AccountLevelDetails> details = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                details.add(new AccountLevelDetails(
                        obj.getString("idAccount") == "null" ? null : obj.getString("idAccount"),
                        obj.getString("username") == "null" ? null : obj.getString("username"),
                        obj.getString("password") == "null" ? null : obj.getString("password"),
                        obj.getString("email") == "null" ? null : obj.getString("email"),
                        obj.getString("fullName") == "null" ? null : obj.getString("fullName"),
                        obj.getString("dob") == "null" ? null : Date.valueOf(obj.getString("dob")),
                        obj.getString("verificationCode") == "null" ? null : obj.getString("veridicationCode"),
                        obj.getString("uud") == "null" ? null : obj.getString("uud"),
                        obj.getString("idRole") == "null" ? null : obj.getString("idRole"),
                        null,
                        obj.getString("idLevelDetails") == "null" ? null : obj.getString("idLevelDetails"),
                        obj.getString("score") == "null" ? null : obj.getLong("score"),
                        obj.getString("timeLeft") == "null" ? null : obj.getString("timeLeft"),
                        obj.getString("datePlayed") == "null" ? null : Date.valueOf(obj.getString("datePlayed")),
                        obj.getString("isLike") == "null" ? null : obj.getBoolean("isLike"),
                        obj.getString("wpm") == "null" ? null : Float.valueOf(obj.getString("wpm")),
                        obj.getString("wps") == "null" ? null : Float.valueOf(obj.getString("wps")),
                        obj.getString("correct") == "null" ? null : Integer.valueOf(obj.getString("correct")),
                        obj.getString("wrong") == "null" ? null : Integer.valueOf(obj.getString("wrong")),
                        obj.getString("accuracy") == "null" ? null : obj.getString("accuracy"),
                        new Level(
                                obj.getString("idLevel") == "null" ? null : obj.getString("idLevel"),
                                obj.getString("nameLevel") == "null" ? null : obj.getString("nameLevel"),
                                obj.getString("numLike") == "null" ? null : obj.getInt("numLike"),
                                obj.getString("createDate") == "null" ? null : Date.valueOf(obj.getString("createDate")),
                                obj.getString("updatedDate") == "null" ? null : Date.valueOf(obj.getString("updatedDate")),
                                obj.getString("levelContent") == "null" ? null : obj.getString("levelContent"),
                                obj.getString("totalWords") == "null" ? null : obj.getInt("totalWords"),
                                obj.getString("time") == "null" ? null : obj.getString("time"),
                                new Difficulty(
                                        obj.getString("idDifficulty") == "null" ? null : obj.getString("idDifficulty"),
                                        null
                                ),
                                new Mode(
                                        obj.getString("idMode") == "null" ? null : obj.getString("idMode"),
                                        null
                                ),
                                obj.getString("idPublisher") == "null" ? null : obj.getString("idPublisher"),
                                null
                        ),
                        obj.getString("chartData") == "null" ? null : obj.getString("chartData")
                ));
            }
            return details.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(AccountLevelDetails d) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idLevelDetails", d.getIdLevelDetails());
            jsonObject.put("idAccount", d.getIdAccount());
            jsonObject.put("idLevel", d.getLevel().getIdLevel());
            jsonObject.put("score", d.getScore());
            jsonObject.put("timeLeft", d.getTimeLeft());
            jsonObject.put("datePlayed", d.getDatePlayed());
            jsonObject.put("isLike", d.getLike());
            jsonObject.put("wpm", d.getWpm());
            jsonObject.put("wps", d.getWps());
            jsonObject.put("correct", d.getCorrect());
            jsonObject.put("wrong", d.getWrong());
            jsonObject.put("accuracy", d.getAccuracy());
            jsonObject.put("chartData", d.getChartData());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(AccountLevelDetails d) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idLevelDetails", d.getIdLevelDetails());
            jsonObject.put("idAccount", d.getIdAccount());
            jsonObject.put("idLevel", d.getLevel().getIdLevel());
            jsonObject.put("score", d.getScore());
            jsonObject.put("timeLeft", d.getTimeLeft());
            jsonObject.put("datePlayed", d.getDatePlayed());
            jsonObject.put("isLike", d.getLike());
            jsonObject.put("wpm", d.getWpm());
            jsonObject.put("wps", d.getWps());
            jsonObject.put("correct", d.getCorrect());
            jsonObject.put("wrong", d.getWrong());
            jsonObject.put("accuracy", d.getAccuracy());
            jsonObject.put("chartData", d.getChartData());
            crudAPI.put(jsonObject, url + "/" + d.getIdLevelDetails());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(AccountLevelDetails d) {
        try {
            crudAPI.delete(url + "/" + d.getIdLevelDetails());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
