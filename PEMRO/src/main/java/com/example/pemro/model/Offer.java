package com.example.pemro.model;

public class Offer extends Postingan {
    private int experienceYears;
    private String portfolioLink;

    public Offer(int userId, String title, String description, int experienceYears, String portfolioLink) {
        super(userId, title, description, "OFFER", "OPEN");
        this.experienceYears = experienceYears;
        this.portfolioLink = portfolioLink;
    }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public String getPortfolioLink() { return portfolioLink; }
    public void setPortfolioLink(String portfolioLink) { this.portfolioLink = portfolioLink; }
}
