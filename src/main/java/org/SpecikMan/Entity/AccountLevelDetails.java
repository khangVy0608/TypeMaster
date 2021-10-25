package org.SpecikMan.Entity;

import java.sql.Date;

public class AccountLevelDetails extends Account{
    private String idLevelDetails;
    private int score;
    private String timeLeft;
    private Date datePlayed;
    private boolean isLike;
    private float wpm;
    private float wps;
    private int correct;
    private int wrong;
    private String accuracy;
    private Level level;

    public String getIdLevelDetails() {
        return idLevelDetails;
    }

    public void setIdLevelDetails(String idLevelDetails) {
        this.idLevelDetails = idLevelDetails;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public float getWpm() {
        return wpm;
    }

    public void setWpm(float wpm) {
        this.wpm = wpm;
    }

    public float getWps() {
        return wps;
    }

    public void setWps(float wps) {
        this.wps = wps;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
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

    public AccountLevelDetails() {
    }

    public AccountLevelDetails(String idAccount, String username, String password, String email, String fullName, Date dob, String verificationCode, String uud, String idRole, String nameRole, String idLevelDetails, int score, String timeLeft, Date datePlayed, boolean isLike, float wpm, float wps, int correct, int wrong, String accuracy, Level level) {
        super(idAccount, username, password, email, fullName, dob, verificationCode, uud, idRole, nameRole);
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

    public AccountLevelDetails(String idAccount, String idLevelDetails, int score, String timeLeft, Date datePlayed, boolean isLike, float wpm, float wps, int correct, int wrong, String accuracy, Level level) {
        super(idAccount);
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
}
