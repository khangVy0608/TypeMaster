package org.SpecikMan.Table;

import javafx.scene.control.Hyperlink;

import java.sql.Date;

public class TableViewItem {
    private int no;
    private Hyperlink username;
    private int score;
    private String time;
    private Date date;
    public TableViewItem(){

    }
    public TableViewItem(int no, Hyperlink username, int score, String time, Date date) {
        this.no = no;
        this.username = username;
        this.score = score;
        this.time = time;
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Hyperlink getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = new Hyperlink(username);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
