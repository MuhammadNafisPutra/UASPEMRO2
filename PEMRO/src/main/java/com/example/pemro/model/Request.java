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

    // Poin F: Overriding metode dari parent
    @Override
    public String getDetailInfo() {
        return "Deadline: " + deadline + " | Urgensi: " + urgencyLevel;
    }

    // Getter & Setter lainnya tetap...
    public Date getDeadline() { return deadline; }
    public String getUrgencyLevel() { return urgencyLevel; }
}