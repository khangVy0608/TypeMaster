package org.SpecikMan.Entity;

public class Rank {
    private String idRank;
    private String rankName;
    private Integer reward;
    private String imagePath;
    private Integer promote;
    private Integer demote;

    public String getIdRank() {
        return idRank;
    }

    public String getRankName() {
        return rankName;
    }

    public Integer getReward() {
        return reward;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getPromote() {
        return promote;
    }

    public Integer getDemote() {
        return demote;
    }

    public Rank(String idRank, String rankName, Integer reward, String imagePath, Integer promote, Integer demote) {
        this.idRank = idRank;
        this.rankName = rankName;
        this.reward = reward;
        this.imagePath = imagePath;
        this.promote = promote;
        this.demote = demote;
    }
    public Rank(String idRank){
        this.idRank = idRank;
    }
}
