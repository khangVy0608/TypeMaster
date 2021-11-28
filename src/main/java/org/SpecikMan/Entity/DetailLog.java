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
    private Integer score;
    private Float wpm;
    private Float wps;
    private Integer correct;
    private Integer wrong;
    private String accuracy;
    private String timeLeft;
    private Date datePlayed;
    private String chartData;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getChartData() {
        return chartData;
    }

    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    public DetailLog(String idLog, String idLevel, String levelName, String idPublisher, String publisherName, String idPlayer, String playerName, Integer score, Float wpm, Float wps, Integer correct, Integer wrong, String accuracy, String timeLeft, Date datePlayed, String chartData) {
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
        this.chartData = chartData;
    }
    public DetailLog(Integer score, Float wpm,Integer correct, Integer wrong, String accuracy, String timeLeft, Date datePlayed) {
        this.score = score;
        this.wpm = wpm;
        this.correct = correct;
        this.wrong = wrong;
        this.accuracy = accuracy;
        this.timeLeft = timeLeft;
        this.datePlayed = datePlayed;
    }
    public DetailLog() {
    }
}
