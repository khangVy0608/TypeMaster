package org.SpecikMan.Entity;

import java.sql.Date;

public class Group extends Account{
    private String idGroup;
    private Rank rank;
    private RankingLevel rankingLevel;
    private Long score1;
    private Long score2;
    private Long score3;
    private Date datePlayed1;
    private Date datePlayed2;
    private Date datePlayed3;
    private Long totalScore;

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public RankingLevel getRankingLevel() {
        return rankingLevel;
    }

    public void setRankingLevel(RankingLevel rankingLevel) {
        this.rankingLevel = rankingLevel;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getScore1() {
        return score1;
    }

    public void setScore1(Long score1) {
        this.score1 = score1;
    }

    public Long getScore2() {
        return score2;
    }

    public void setScore2(Long score2) {
        this.score2 = score2;
    }

    public Long getScore3() {
        return score3;
    }

    public void setScore3(Long score3) {
        this.score3 = score3;
    }

    public Date getDatePlayed1() {
        return datePlayed1;
    }

    public void setDatePlayed1(Date datePlayed1) {
        this.datePlayed1 = datePlayed1;
    }

    public Date getDatePlayed2() {
        return datePlayed2;
    }

    public void setDatePlayed2(Date datePlayed2) {
        this.datePlayed2 = datePlayed2;
    }

    public Date getDatePlayed3() {
        return datePlayed3;
    }

    public void setDatePlayed3(Date datePlayed3) {
        this.datePlayed3 = datePlayed3;
    }

    public Group(String idAccount, String username, String idGroup, Rank rank, RankingLevel rankingLevel,Long score1,Long score2,
            Long score3,Date datePlayed1,Date datePlayed2,Date datePlayed3,Long totalScore) {
        super(idAccount, username);
        this.idGroup = idGroup;
        this.rank = rank;
        this.rankingLevel = rankingLevel;
        this.totalScore = totalScore;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.datePlayed1 = datePlayed1;
        this.datePlayed2 = datePlayed2;
        this.datePlayed3 = datePlayed3;
    }

    public Group() {
    }
}
