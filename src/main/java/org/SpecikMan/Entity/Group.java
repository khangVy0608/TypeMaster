package org.SpecikMan.Entity;

public class Group extends Account{
    private String idGroup;
    private Rank rank;
    private RankingLevel rankingLevel;
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

    public Group(String idAccount, String username, String idGroup, Rank rank, RankingLevel rankingLevel, Long totalScore) {
        super(idAccount, username);
        this.idGroup = idGroup;
        this.rank = rank;
        this.rankingLevel = rankingLevel;
        this.totalScore = totalScore;
    }

    public Group() {
    }
}
