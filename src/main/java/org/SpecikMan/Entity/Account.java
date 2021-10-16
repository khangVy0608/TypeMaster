
package org.SpecikMan.Entity;

import java.sql.Date;

public class Account extends Role{
    private String id;
    private String email;
    private String user;
    private String password;
    private String name;
    private Boolean gender;
    private Date dob;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Account() {
    }

    public Account(String id, String email, String user, String password, String name, Boolean gender, Date dob, String idRole) {
        this.id = id;
        this.email = email;
        this.user = user;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    
}
