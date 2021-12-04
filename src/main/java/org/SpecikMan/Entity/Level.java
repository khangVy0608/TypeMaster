package org.SpecikMan.Entity;

import java.sql.Date;

public class Level extends Account{
    private String idLevel;
    private String nameLevel;
    private Integer numLike;
    private Date createDate;
    private Date updatedDate;
    private String levelContent;
    private Integer totalWords;
    private String time;
    private Difficulty difficulty;
    private Mode mode;

    public String getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(String idLevel) {
        this.idLevel = idLevel;
    }

    public String getNameLevel() {
        return nameLevel;
    }

    public void setNameLevel(String nameLevel) {
        this.nameLevel = nameLevel;
    }

    public Integer getNumLike() {
        return numLike;
    }

    public void setNumLike(Integer numLike) {
        this.numLike = numLike;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLevelContent() {
        return levelContent;
    }

    public void setLevelContent(String levelContent) {
        this.levelContent = levelContent;
    }

    public Integer getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(Integer totalWords) {
        this.totalWords = totalWords;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.nameLevel;
    }
    public Level() {
    }
    //change when use
    public Level(String idLevel, String name, Integer numLike, Date createDate, Date updatedDate, String levelContent, Integer totalWords, String time, Difficulty difficulty, Mode mode,String idAccount,String username) {
        super(idAccount,username);
        this.idLevel = idLevel;
        this.nameLevel = name;
        this.numLike = numLike;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
        this.levelContent = levelContent;
        this.totalWords = totalWords;
        this.time = time;
        this.difficulty = difficulty;
        this.mode = mode;
    }

    public Level(String idAccount, String username, String password, String email, String fullName, Date dob, String verificationCode, String uud,Integer accountLevel,Integer levelCap,Integer levelExp, String idRole, String nameRole,Rank rank, String idLevel, String name, Integer numLike, Date createDate, Date updatedDate, String levelContent, Integer totalWords, String time, Difficulty difficulty, Mode mode) {
        super(idAccount, username, password, email, fullName, dob, verificationCode, uud,accountLevel,levelCap,levelExp, idRole, nameRole,rank);
        this.idLevel = idLevel;
        this.nameLevel = name;
        this.numLike = numLike;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
        this.levelContent = levelContent;
        this.totalWords = totalWords;
        this.time = time;
        this.difficulty = difficulty;
        this.mode = mode;
    }

    public Level(String idLevel, String nameLevel, Integer numLike, Date createDate, Date updatedDate, String levelContent, Integer totalWords, String time, Difficulty difficulty, Mode mode) {
        this.idLevel = idLevel;
        this.nameLevel = nameLevel;
        this.numLike = numLike;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
        this.levelContent = levelContent;
        this.totalWords = totalWords;
        this.time = time;
        this.difficulty = difficulty;
        this.mode = mode;
    }
    public Level(String idLevel){
        this.idLevel = idLevel;
    }
}
