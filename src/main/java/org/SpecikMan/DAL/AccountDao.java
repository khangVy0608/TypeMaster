package org.SpecikMan.DAL;


import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.Rank;
import org.SpecikMan.Entity.apiURL;
import org.SpecikMan.Tools.crudAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account> {
    private final String url = apiURL.getApiURL() + "/account";

    public AccountDao() {
    }

    public List<Account> getAll() {
        try {
            List<Account> accounts = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "s"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                accounts.add(new Account(
                        obj.getString("idAccount"),
                        obj.getString("username"),
                        obj.getString("password").equals("null") ? null : obj.getString("password"),
                        obj.getString("email").equals("null") ? null : obj.getString("email"),
                        obj.getString("createDate").equals("null") ? null : Date.valueOf(obj.getString("createDate")),
                        obj.getString("latestLoginDate").equals("null") ? null : Date.valueOf(obj.getString("latestLoginDate")),
                        obj.getString("countLoginDate").equals("null") ? null : Integer.valueOf(obj.getString("countLoginDate")),
                        obj.getString("pathImage").equals("null") ? null : obj.getString("pathImage"),
                        obj.getString("fullName").equals("null") ? null : obj.getString("fullName"),
                        obj.getString("dob").equals("null") ? null : Date.valueOf(obj.getString("dob")),
                        obj.getString("gender").equals("null") ? null : Boolean.valueOf(obj.getString("gender")),
                        obj.getString("coin").equals("null") ? null : Integer.valueOf(obj.getString("coin")),
                        obj.getString("verificationCode").equals("null") ? null : obj.getString("verificationCode"),
                        obj.getString("uud").equals("null") ? null : obj.getString("uud"),
                        obj.getString("accountLevel").equals("null")?null:Integer.valueOf(obj.getString("accountLevel")),
                        obj.getString("levelCap").equals("null")?null:Integer.valueOf(obj.getString("levelCap")),
                        obj.getString("levelExp").equals("null")?null:Integer.valueOf(obj.getString("levelExp")),
                        obj.getString("idRole").equals("null") ? null : obj.getString("idRole"),
                        obj.getString("nameRole").equals("null") ? null : obj.getString("nameRole"),
                        new Rank(
                                obj.getString("idRank").equals("null") ? null : obj.getString("idRank"),
                                obj.getString("rankName").equals("null") ? null : obj.getString("rankName"),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("reward")),
                                obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                                obj.getString("promote").equals("null") ? null : Integer.parseInt(obj.getString("promote")),
                                obj.getString("demote").equals("null") ? null : Integer.parseInt(obj.getString("demote"))
                        )
                ));
            }
            return accounts;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account get(String id) {
        try {
            List<Account> accounts = new ArrayList<>();
            JSONArray data = new JSONArray(crudAPI.get(url + "/" + id.trim()));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = (JSONObject) data.get(i);
                accounts.add(new Account(
                        obj.getString("idAccount"),
                        obj.getString("username"),
                        obj.getString("password").equals("null") ? null : obj.getString("password"),
                        obj.getString("email").equals("null") ? null : obj.getString("email"),
                        obj.getString("createDate").equals("null") ? null : Date.valueOf(obj.getString("createDate")),
                        obj.getString("latestLoginDate").equals("null") ? null : Date.valueOf(obj.getString("latestLoginDate")),
                        obj.getString("countLoginDate").equals("null") ? null : Integer.valueOf(obj.getString("countLoginDate")),
                        obj.getString("pathImage").equals("null") ? null : obj.getString("pathImage"),
                        obj.getString("fullName").equals("null") ? null : obj.getString("fullName"),
                        obj.getString("dob").equals("null") ? null : Date.valueOf(obj.getString("dob")),
                        obj.getString("gender").equals("null") ? null : Boolean.valueOf(obj.getString("gender")),
                        obj.getString("coin").equals("null") ? null : Integer.valueOf(obj.getString("coin")),
                        obj.getString("verificationCode").equals("null") ? null : obj.getString("verificationCode"),
                        obj.getString("uud").equals("null") ? null : obj.getString("uud"),
                        obj.getString("accountLevel").equals("null")?null:Integer.valueOf(obj.getString("accountLevel")),
                        obj.getString("levelCap").equals("null")?null:Integer.valueOf(obj.getString("levelCap")),
                        obj.getString("levelExp").equals("null")?null:Integer.valueOf(obj.getString("levelExp")),
                        obj.getString("idRole").equals("null") ? null : obj.getString("idRole"),
                        obj.getString("nameRole").equals("null") ? null : obj.getString("nameRole"),
                        new Rank(
                                obj.getString("idRank").equals("null") ? null : obj.getString("idRank"),
                                obj.getString("rankName").equals("null") ? null : obj.getString("rankName"),
                                obj.getString("reward").equals("null") ? null : Integer.parseInt(obj.getString("reward")),
                                obj.getString("imagePath").equals("null") ? null : obj.getString("imagePath"),
                                obj.getString("promote").equals("null") ? null : Integer.parseInt(obj.getString("promote")),
                                obj.getString("demote").equals("null") ? null : Integer.parseInt(obj.getString("demote"))
                        )
                ));
            }
            return accounts.get(0);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(Account acc) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idAccount", acc.getIdAccount());
            jsonObject.put("username", acc.getUsername());
            jsonObject.put("password", acc.getPassword());
            jsonObject.put("email", acc.getEmail());
            jsonObject.put("createDate", acc.getCreateDate());
            jsonObject.put("latestLoginDate", acc.getLatestLoginDate());
            jsonObject.put("countLoginDate", acc.getCountLoginDate());
            jsonObject.put("pathImage", acc.getPathImage());
            jsonObject.put("fullName", acc.getFullName());
            jsonObject.put("dob", acc.getDob());
            jsonObject.put("gender", acc.isGender());
            jsonObject.put("coin", acc.getCoin());
            jsonObject.put("verificationCode", acc.getVerificationCode());
            jsonObject.put("uud", acc.getUud());
            jsonObject.put("accountLevel", acc.getAccountLevel());
            jsonObject.put("levelCap", acc.getLevelCap());
            jsonObject.put("levelExp", acc.getLevelExp());
            jsonObject.put("idRole", acc.getIdRole());
            jsonObject.put("idRank", acc.getRank().getIdRank());
            crudAPI.post(jsonObject, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Account acc) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idAccount", acc.getIdAccount());
            jsonObject.put("username", acc.getUsername());
            jsonObject.put("password", acc.getPassword());
            jsonObject.put("email", acc.getEmail());
            jsonObject.put("createDate", acc.getCreateDate());
            jsonObject.put("latestLoginDate", acc.getLatestLoginDate());
            jsonObject.put("countLoginDate", acc.getCountLoginDate());
            jsonObject.put("pathImage", acc.getPathImage());
            jsonObject.put("fullName", acc.getFullName());
            jsonObject.put("dob", acc.getDob());
            jsonObject.put("gender", acc.isGender());
            jsonObject.put("coin", acc.getCoin());
            jsonObject.put("verificationCode", acc.getVerificationCode());
            jsonObject.put("uud", acc.getUud());
            jsonObject.put("accountLevel", acc.getAccountLevel());
            jsonObject.put("levelCap", acc.getLevelCap());
            jsonObject.put("levelExp", acc.getLevelExp());
            jsonObject.put("idRole", acc.getIdRole());
            jsonObject.put("idRank", acc.getRank().getIdRank());
            crudAPI.put(jsonObject, url + "/" + acc.getIdAccount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Account acc) {
        try {
            crudAPI.delete(url + "/" + acc.getIdAccount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
