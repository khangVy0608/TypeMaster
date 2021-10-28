package org.SpecikMan.Entity;

public class Difficulty {
    private String idDifficulty;
    private String nameDifficulty;

    public String getIdDifficulty() {
        return idDifficulty;
    }

    public void setIdDifficulty(String idDifficulty) {
        this.idDifficulty = idDifficulty;
    }

    public String getNameDifficulty() {
        return nameDifficulty;
    }

    public void setNameDifficulty(String nameDifficulty) {
        this.nameDifficulty = nameDifficulty;
    }

    public Difficulty() {
    }
    @Override
    public String toString() {
        return nameDifficulty;
    }
    public Difficulty(String idDifficulty, String nameDifficulty) {
        this.idDifficulty = idDifficulty;
        this.nameDifficulty = nameDifficulty;
    }
}
