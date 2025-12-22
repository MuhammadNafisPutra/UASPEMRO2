package com.example.pemro.model;

import java.sql.Date;

public class Request extends Postingan {
    private Date deadline;
    private String urgencyLevel;

    public Request(int userId, String title, String description, Date deadline, String urgencyLevel) {
        super(userId, title, description, "REQUEST", "OPEN");
        this.deadline = deadline;
        this.urgencyLevel = urgencyLevel;
    }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }
}
