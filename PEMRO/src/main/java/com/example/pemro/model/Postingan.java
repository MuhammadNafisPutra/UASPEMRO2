package com.example.pemro.model;

public abstract class Postingan {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String postType;
    private String status;

    public Postingan(int userId, String title, String description, String postType, String status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.postType = postType;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public abstract String getDetailInfo();
}