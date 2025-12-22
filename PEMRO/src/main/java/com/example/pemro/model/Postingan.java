package com.example.pemro.model;

// Poin E: Menggunakan abstract class
public abstract class Postingan {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String postType; // "OFFER" atau "REQUEST"
    private String status;   // "OPEN", "CLOSED", dll.

    public Postingan(int userId, String title, String description, String postType, String status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.postType = postType;
        this.status = status;
    }

    // Getter dan Setter (Poin D: Enkapsulasi)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPostType() { return postType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Poin F: Metode yang akan di-override di kelas anak
    public abstract String getDetailInfo();
}