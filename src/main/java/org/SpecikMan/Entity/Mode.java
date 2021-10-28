package org.SpecikMan.Entity;

public class Mode {
    private String idMode;
    private String nameMode;

    public String getIdMode() {
        return idMode;
    }

    public void setIdMode(String idMode) {
        this.idMode = idMode;
    }

    public String getNameMode() {
        return nameMode;
    }

    public void setNameMode(String nameMode) {
        this.nameMode = nameMode;
    }

    public Mode() {
    }
    @Override
    public String toString(){
        return nameMode;
    }
    public Mode(String idMode, String name) {
        this.idMode = idMode;
        this.nameMode = name;
    }
}
