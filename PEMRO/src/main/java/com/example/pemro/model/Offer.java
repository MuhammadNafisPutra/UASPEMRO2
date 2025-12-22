package com.example.pemro.model;

public class Offer extends Postingan {
    private int experienceYears;
    private String portfolioLink;

    public Offer(int userId, String title, String description, int experienceYears, String portfolioLink) {
        super(userId, title, description, "OFFER", "OPEN");
        this.experienceYears = experienceYears;
        this.portfolioLink = portfolioLink;
    }

    // Poin F: Overriding metode dari parent
    @Override
    public String getDetailInfo() {
        return "Pengalaman: " + experienceYears + " Tahun | Portfolio: " + portfolioLink;
    }

    // Getter & Setter lainnya tetap...
    public int getExperienceYears() { return experienceYears; }
    public String getPortfolioLink() { return portfolioLink; }
}