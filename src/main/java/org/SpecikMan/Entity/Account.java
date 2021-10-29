
package org.SpecikMan.Entity;

import java.sql.Date;

public class Account extends Role {
    private String idAccount;
    private String username;
    private String password;
    private String email;
    private Date createDate;
    private Date latestLoginDate;
    private int countLoginDate;
    private String fullName;
    private Date dob;
    private String verificationCode;
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

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

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

    public void setCountLoginDate(int countLoginDate) {
        this.countLoginDate = countLoginDate;
    }
    //endregion
    //region Constructor

    public Account() {
    }

    public Account(String idAccount, String username, String password, String email, Date createDate,Date latestLogin,int countLoginDate ,String fullName, Date dob, String verificationCode,String uud,String idRole, String nameRole) {
        super(idRole, nameRole);
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.dob = dob;
        this.verificationCode = verificationCode;
        this.uud = uud;
        this.createDate = createDate;
        this.latestLoginDate = latestLogin;
        this.countLoginDate = countLoginDate;
    }
    public Account(String idAccount, String username, String password, String email,String fullName, Date dob, String verificationCode,String uud,String idRole, String nameRole) {
        super(idRole, nameRole);
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.dob = dob;
        this.verificationCode = verificationCode;
        this.uud = uud;
    }
    public Account(String idAccount,String username) {
        this.idAccount = idAccount;
        this.username = username;
    }
    //endregion
}
