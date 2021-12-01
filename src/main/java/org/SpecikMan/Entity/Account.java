
package org.SpecikMan.Entity;

import java.sql.Date;

public class Account extends Role {
    private String idAccount;
    private String username;
    private String password;
    private String email;
    private Date createDate;
    private Date latestLoginDate;
    private Integer countLoginDate;
    private String pathImage;
    private String fullName;
    private Date dob;
    private Boolean gender;
    private Integer coin;
    private String verificationCode;
    private Integer accountLevel;
    private Integer levelCap;
    private Integer levelExp;
    private String uud;
    //region get-set

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPathImage() { return pathImage; }

    public void setPathImage(String pathImage) { this.pathImage = pathImage; }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getUud() {
        return uud;
    }

    public void setUud(String uud) {
        this.uud = uud;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(Date latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public int getCountLoginDate() {
        return countLoginDate;
    }

    public void setCountLoginDate(Integer countLoginDate) {
        this.countLoginDate = countLoginDate;
    }

    public Boolean isGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Integer accountLevel) {
        this.accountLevel = accountLevel;
    }

    public Integer getLevelCap() {
        return levelCap;
    }

    public void setLevelCap(Integer levelCap) {
        this.levelCap = levelCap;
    }

    public Integer getLevelExp() {
        return levelExp;
    }

    public void setLevelExp(Integer levelExp) {
        this.levelExp = levelExp;
    }
    //endregion
    //region Constructor

    public Account() {
    }

    public Account(String idAccount, String username, String password, String email, Date createDate,Date latestLogin,Integer countLoginDate ,String pathImage, String fullName, Date dob,Boolean gender,Integer coin, String verificationCode,String uud,Integer accountLevel,Integer levelCap
                   ,Integer levelExp,String idRole, String nameRole) {
        super(idRole, nameRole);
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.email = email;
        this.pathImage = pathImage;
        this.fullName = fullName;
        this.dob = dob;
        this.verificationCode = verificationCode;
        this.uud = uud;
        this.createDate = createDate;
        this.latestLoginDate = latestLogin;
        this.countLoginDate = countLoginDate;
        this.gender = gender;
        this.coin = coin;
        this.accountLevel = accountLevel;
        this.levelCap = levelCap;
        this.levelExp = levelExp;
    }


    public Account(String idAccount, String username, String password, String email,String fullName, Date dob, String verificationCode,String uud,Integer accountLevel,Integer levelCap
            ,Integer levelExp,String idRole, String nameRole) {
        super(idRole, nameRole);
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.dob = dob;
        this.verificationCode = verificationCode;
        this.uud = uud;
        this.accountLevel = accountLevel;
        this.levelCap = levelCap;
        this.levelExp = levelExp;
    }
    public Account(String idAccount,String username) {
        this.idAccount = idAccount;
        this.username = username;
    }
    public Account(String username){
        this.username = username;
    }
    //endregion
}
