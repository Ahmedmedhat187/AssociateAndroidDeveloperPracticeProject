package com.ahmed.androiddeveloperpracticeproject.models;

public class LearnerModel {
    String name;
    String country;
    int hours;
    int score;
    String badgeUrl;


    public LearnerModel(String name, String country, int hours, int score, String badgeUrl) {
        this.name = name;
        this.country = country;
        this.hours = hours;
        this.score = score;
        this.badgeUrl = badgeUrl;
    }

    public String getName() { return name; }
    public String getCountry() { return country; }
    public int getHours() { return hours; }
    public int getScore() { return score; }
    public String getBadgeUrl() { return badgeUrl; }


    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setHours(int hours) { this.hours = hours; }
    public void setScore(int score) { this.score = score; }
    public void setBadgeUrl(String badgeUrl) { this.badgeUrl = badgeUrl; }


    @Override
    public String toString() {
        return "LearnerModel{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", hours=" + hours +
                ", badgeUrl='" + badgeUrl + '\'' +
                '}';
    }
}
