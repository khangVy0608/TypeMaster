package org.SpecikMan.DAL;

import org.SpecikMan.Entity.Group;
import org.SpecikMan.Entity.Rank;
import org.SpecikMan.Entity.RankingLevel;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GroupDao implements Dao<Group> {
    private final String url = apiURL.getApiURL() + "/group";
    @Override
    public List<Group> getAll() {
        try {
            List<Group> grs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                grs.add(new Group(
                        obj.getString("idAccount").equals("null") ? null : obj.getString("idAccount"),
                        obj.getString("username").equals("null") ? null : obj.getString("username"),
                        obj.getString("idGroup").equals("null") ? null : obj.getString("idGroup"),
                        new Rank(
                                obj.getString("idRank").equals("null") ? null : obj.getString("idRank"),
                                obj.getString("rankName").equals("null") ? null : obj.getString("rankName"),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("reward")),
                                obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                                obj.getString("promote").equals("null") ? null : Integer.parseInt(obj.getString("promote")),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("demote"))
                        ),
                        new RankingLevel(
                                obj.getString("idRankingLevel").equals("null") ? null : obj.getString("idRankingLevel"),
                                obj.getString("rankingCreateDate").equals("null") ? null : Date.valueOf(obj.getString("rankingCreateDate")),
                                obj.getString("fromRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("fromRankPeriod")),
                                obj.getString("toRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("toRankPeriod")),
                                obj.getString("levelContent1").equals("null") ? null : obj.getString("levelContent1"),
                                obj.getString("levelContent2").equals("null") ? null : obj.getString("levelContent2"),
                                obj.getString("levelContent3").equals("null") ? null : obj.getString("levelContent3"),
                                obj.getString("time1").equals("null") ? null : obj.getString("time1"),
                                obj.getString("time2").equals("null") ? null : obj.getString("time2"),
                                obj.getString("time3").equals("null") ? null : obj.getString("time3")
                        ),
                        obj.getString("score1").equals("null") ? null : Long.parseLong(obj.getString("score1")),
                        obj.getString("score2").equals("null") ? null : Long.parseLong(obj.getString("score2")),
                        obj.getString("score3").equals("null") ? null : Long.parseLong(obj.getString("score3")),
                        obj.getString("datePlayed1").equals("null") ? null : Date.valueOf(obj.getString("datePlayed1")),
                        obj.getString("datePlayed2").equals("null") ? null : Date.valueOf(obj.getString("datePlayed2")),
                        obj.getString("datePlayed3").equals("null") ? null : Date.valueOf(obj.getString("datePlayed3")),
                        obj.getString("totalScore").equals("null") ? null : Long.parseLong(obj.getString("totalScore"))

                ));
            }
            return grs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Group get(String id) {
        try {
            List<Group> grs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                grs.add(new Group(
                        obj.getString("idAccount").equals("null") ? null : obj.getString("idAccount"),
                        obj.getString("username").equals("null") ? null : obj.getString("username"),
                        obj.getString("idGroup").equals("null") ? null : obj.getString("idGroup"),
                        new Rank(
                                obj.getString("idRank").equals("null") ? null : obj.getString("idRank"),
                                obj.getString("rankName").equals("null") ? null : obj.getString("rankName"),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("reward")),
                                obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                                obj.getString("promote").equals("null") ? null : Integer.parseInt(obj.getString("promote")),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("demote"))
                        ),
                        new RankingLevel(
                                obj.getString("idRankingLevel").equals("null") ? null : obj.getString("idRankingLevel"),
                                obj.getString("rankingCreateDate").equals("null") ? null : Date.valueOf(obj.getString("rankingCreateDate")),
                                obj.getString("fromRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("fromRankPeriod")),
                                obj.getString("toRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("toRankPeriod")),
                                obj.getString("levelContent1").equals("null") ? null : obj.getString("levelContent1"),
                                obj.getString("levelContent2").equals("null") ? null : obj.getString("levelContent2"),
                                obj.getString("levelContent3").equals("null") ? null : obj.getString("levelContent3"),
                                obj.getString("time1").equals("null") ? null : obj.getString("time1"),
                                obj.getString("time2").equals("null") ? null : obj.getString("time2"),
                                obj.getString("time3").equals("null") ? null : obj.getString("time3")
                        ),
                        obj.getString("score1").equals("null") ? null : Long.parseLong(obj.getString("score1")),
                        obj.getString("score2").equals("null") ? null : Long.parseLong(obj.getString("score2")),
                        obj.getString("score3").equals("null") ? null : Long.parseLong(obj.getString("score3")),
                        obj.getString("datePlayed1").equals("null") ? null : Date.valueOf(obj.getString("datePlayed1")),
                        obj.getString("datePlayed2").equals("null") ? null : Date.valueOf(obj.getString("datePlayed2")),
                        obj.getString("datePlayed3").equals("null") ? null : Date.valueOf(obj.getString("datePlayed3")),
                        obj.getString("totalScore").equals("null") ? null : Long.parseLong(obj.getString("totalScore"))

                ));
            }
            return grs.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Group gr) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idGroup", gr.getIdGroup());
            jsonObject.put("idRank", gr.getRank().getIdRank());
            jsonObject.put("idAccount", gr.getIdAccount());
            jsonObject.put("idRankingLevel", gr.getRankingLevel().getIdRankingLevel());
            jsonObject.put("score1", gr.getScore1());
            jsonObject.put("score2", gr.getScore2());
            jsonObject.put("score3", gr.getScore3());
            jsonObject.put("datePlayed1", gr.getDatePlayed1());
            jsonObject.put("datePlayed2", gr.getDatePlayed2());
            jsonObject.put("datePlayed3", gr.getDatePlayed3());
            jsonObject.put("totalScore", gr.getTotalScore());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Group gr) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idGroup", gr.getIdGroup());
            jsonObject.put("idRank", gr.getRank().getIdRank());
            jsonObject.put("idAccount", gr.getIdAccount());
            jsonObject.put("idRankingLevel", gr.getRankingLevel().getIdRankingLevel());
            jsonObject.put("score1", gr.getScore1());
            jsonObject.put("score2", gr.getScore2());
            jsonObject.put("score3", gr.getScore3());
            jsonObject.put("datePlayed1", gr.getDatePlayed1());
            jsonObject.put("datePlayed2", gr.getDatePlayed2());
            jsonObject.put("datePlayed3", gr.getDatePlayed3());
            jsonObject.put("totalScore", gr.getTotalScore());
            crudAPI.put(jsonObject, url + "/" + gr.getIdGroup());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Group gr) {
        try {
            crudAPI.delete(url + "/" + gr.getIdGroup());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Group> getByRank(String id){
        try {
            List<Group> grs = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s/" + id));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                grs.add(new Group(
                        obj.getString("idAccount").equals("null") ? null : obj.getString("idAccount"),
                        obj.getString("username").equals("null") ? null : obj.getString("username"),
                        obj.getString("idGroup").equals("null") ? null : obj.getString("idGroup"),
                        new Rank(
                                obj.getString("idRank").equals("null") ? null : obj.getString("idRank"),
                                obj.getString("rankName").equals("null") ? null : obj.getString("rankName"),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("reward")),
                                obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                                obj.getString("promote").equals("null") ? null : Integer.parseInt(obj.getString("promote")),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("demote"))
                        ),
                        new RankingLevel(
                                obj.getString("idRankingLevel").equals("null") ? null : obj.getString("idRankingLevel"),
                                obj.getString("rankingCreateDate").equals("null") ? null : Date.valueOf(obj.getString("rankingCreateDate")),
                                obj.getString("fromRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("fromRankPeriod")),
                                obj.getString("toRankPeriod").equals("null") ? null : Date.valueOf(obj.getString("toRankPeriod")),
                                obj.getString("levelContent1").equals("null") ? null : obj.getString("levelContent1"),
                                obj.getString("levelContent2").equals("null") ? null : obj.getString("levelContent2"),
                                obj.getString("levelContent3").equals("null") ? null : obj.getString("levelContent3"),
                                obj.getString("time1").equals("null") ? null : obj.getString("time1"),
                                obj.getString("time2").equals("null") ? null : obj.getString("time2"),
                                obj.getString("time3").equals("null") ? null : obj.getString("time3")
                        ),
                        obj.getString("score1").equals("null") ? null : Long.parseLong(obj.getString("score1")),
                        obj.getString("score2").equals("null") ? null : Long.parseLong(obj.getString("score2")),
                        obj.getString("score3").equals("null") ? null : Long.parseLong(obj.getString("score3")),
                        obj.getString("datePlayed1").equals("null") ? null : Date.valueOf(obj.getString("datePlayed1")),
                        obj.getString("datePlayed2").equals("null") ? null : Date.valueOf(obj.getString("datePlayed2")),
                        obj.getString("datePlayed3").equals("null") ? null : Date.valueOf(obj.getString("datePlayed3")),
                        obj.getString("totalScore").equals("null") ? null : Long.parseLong(obj.getString("totalScore"))

                ));
            }
            return grs;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
