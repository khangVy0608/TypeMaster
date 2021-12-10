package org.SpecikMan.Entity;

import java.sql.Date;

public class Feedback extends Account{
    private String idFeedback;
    private String submitDetail;
    private Date submitDate;

    public String getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(String idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getSubmitDetail() {
        return submitDetail;
    }

    public void setSubmitDetail(String submitDetail) {
        this.submitDetail = submitDetail;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Feedback(String idAccount, String username, String idFeedback, String submitDetail, Date submitDate) {
        super(idAccount, username);
        this.idFeedback = idFeedback;
        this.submitDetail = submitDetail;
        this.submitDate = submitDate;
    }

    public Feedback() {
    }
}
