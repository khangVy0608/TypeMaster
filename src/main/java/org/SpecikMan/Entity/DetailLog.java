package org.SpecikMan.Entity;

import java.sql.Date;

public class DetailLog {
    private String idLog;
    private String idLevel;
    private String levelName;
    private String idPublisher;
    private String publisherName;
    private String idPlayer;
    private String playerName;
    private int score;
    private float wpm;
    private float wps;
    private int correct;
    private int wrong;
    private String accuracy;
    private String timeLeft;
    private Date datePlayed;

    public String getIdLog() {
        return idLog;
    }

    public void setIdLog(String idLog) {
        this.idLog = idLog;
    }

    public String getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(String idLevel) {
        this.idLevel = idLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getIdPublisher() {
        return idPublisher;
    }

    public void setIdPublisher(String idPublisher) {
        this.idPublisher = idPublisher;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public DetailLog(String idLog, String idLevel, String levelName, String idPublisher, String publisherName, String idPlayer, String playerName, int score, float wpm, float wps, int correct, int wrong, String accuracy, String timeLeft, Date datePlayed) {
        this.idLog = idLog;
        this.idLevel = idLevel;
        this.levelName = levelName;
        this.idPublisher = idPublisher;
        this.publisherName = publisherName;
        this.idPlayer = idPlayer;
        this.playerName = playerName;
        this.score = score;
        this.wpm = wpm;
        this.wps = wps;
        this.correct = correct;
        this.wrong = wrong;
        this.accuracy = accuracy;
        this.timeLeft = timeLeft;
        this.datePlayed = datePlayed;
    }

    public DetailLog() {
    }
}
