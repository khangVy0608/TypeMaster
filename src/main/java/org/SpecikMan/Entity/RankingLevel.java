package org.SpecikMan.Entity;

import java.sql.Date;

public class RankingLevel {
    private String idRankingLevel;
    private Date createDate;
    private Date fromRankPeriod;
    private Date toRankPeriod;
    private String levelContent1;
    private String levelContent2;

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

    public RankingLevel() {
    }

    public RankingLevel(String idRankingLevel, Date createDate, Date fromRankPeriod, Date toRankPeriod, String levelContent1, String levelContent2) {
        this.idRankingLevel = idRankingLevel;
        this.createDate = createDate;
        this.fromRankPeriod = fromRankPeriod;
        this.toRankPeriod = toRankPeriod;
        this.levelContent1 = levelContent1;
        this.levelContent2 = levelContent2;
    }
}
