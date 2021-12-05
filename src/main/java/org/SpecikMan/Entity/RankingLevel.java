package org.SpecikMan.Entity;

import java.sql.Date;

public class RankingLevel {
    private String idRankingLevel;
    private Date createDate;
    private Date fromRankPeriod;
    private Date toRankPeriod;
    private String levelContent1;
    private String levelContent2;
    private String levelContent3;
    private String time1;
    private String time2;
    private String time3;

    public String getIdRankingLevel() {
        return idRankingLevel;
    }

    public void setIdRankingLevel(String idRankingLevel) {
        this.idRankingLevel = idRankingLevel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFromRankPeriod() {
        return fromRankPeriod;
    }

    public void setFromRankPeriod(Date fromRankPeriod) {
        this.fromRankPeriod = fromRankPeriod;
    }

    public Date getToRankPeriod() {
        return toRankPeriod;
    }

    public void setToRankPeriod(Date toRankPeriod) {
        this.toRankPeriod = toRankPeriod;
    }

    public String getLevelContent1() {
        return levelContent1;
    }

    public void setLevelContent1(String levelContent1) {
        this.levelContent1 = levelContent1;
    }

    public String getLevelContent2() {
        return levelContent2;
    }

    public void setLevelContent2(String levelContent2) {
        this.levelContent2 = levelContent2;
    }

    public String getLevelContent3() {
        return levelContent3;
    }

    public void setLevelContent3(String levelContent3) {
        this.levelContent3 = levelContent3;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public RankingLevel() {
    }

    public RankingLevel(String idRankingLevel, Date createDate, Date fromRankPeriod, Date toRankPeriod, String levelContent1, String levelContent2, String levelContent3,String time1,
                        String time2,String time3) {
        this.idRankingLevel = idRankingLevel;
        this.createDate = createDate;
        this.fromRankPeriod = fromRankPeriod;
        this.toRankPeriod = toRankPeriod;
        this.levelContent1 = levelContent1;
        this.levelContent2 = levelContent2;
        this.levelContent3 = levelContent3;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
    }
}
