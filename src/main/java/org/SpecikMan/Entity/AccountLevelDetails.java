package org.SpecikMan.Entity;

import java.sql.Date;

public class AccountLevelDetails extends Account{
    private String idLevelDetails;
    private Long score;
    private String timeLeft;
    private Date datePlayed;
    private Boolean isLike;
    private Float wpm;
    private Float wps;
    private Integer correct;
    private Integer wrong;
    private String accuracy;
    private Level level;
    private String chartData;

    public String getIdLevelDetails() {
        return idLevelDetails;
    }

    public void setIdLevelDetails(String idLevelDetails) {
        this.idLevelDetails = idLevelDetails;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public Float getWpm() {
        return wpm;
    }

    public void setWpm(Float wpm) {
        this.wpm = wpm;
    }

    public Float getWps() {
        return wps;
    }

    public void setWps(Float wps) {
        this.wps = wps;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getChartData() {
        return chartData;
    }

    @Override
    public String toString() {
        return this.level.getNameLevel();
    }
    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    public AccountLevelDetails() {
    }

    public AccountLevelDetails(String idAccount, String username, String password, String email, String fullName, Date dob, String verificationCode, String uud,Integer accountLevel,Integer levelCap,Integer levelExp, String idRole, String nameRole, String idLevelDetails, Long score, String timeLeft, Date datePlayed, Boolean isLike, Float wpm, Float wps, Integer correct, Integer wrong, String accuracy, Level level,String chartData) {
        super(idAccount, username, password, email, fullName, dob, verificationCode, uud,accountLevel,levelCap,levelExp, idRole, nameRole);
        this.idLevelDetails = idLevelDetails;
        this.score = score;
        this.timeLeft = timeLeft;
        this.datePlayed = datePlayed;
        this.isLike = isLike;
        this.wpm = wpm;
        this.wps = wps;
        this.correct = correct;
        this.wrong = wrong;
        this.accuracy = accuracy;
        this.level = level;
        this.chartData = chartData;
    }

    public AccountLevelDetails(String idAccount,String username, String idLevelDetails, Long score, String timeLeft, Date datePlayed, Boolean isLike, Float wpm, Float wps, Integer correct, Integer wrong, String accuracy, Level level) {
        super(idAccount,username);
        this.idLevelDetails = idLevelDetails;
        this.score = score;
        this.timeLeft = timeLeft;
        this.datePlayed = datePlayed;
        this.isLike = isLike;
        this.wpm = wpm;
        this.wps = wps;
        this.correct = correct;
        this.wrong = wrong;
        this.accuracy = accuracy;
        this.level = level;
    }
    public AccountLevelDetails(String idLevelDetails,String username,Long score){
        super(username);
        this.idLevelDetails = idLevelDetails;
        this.score = score;
    }
}
