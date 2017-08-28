package com.tanners.taggedwallpaper.vision.response;

import java.util.ArrayList;

public class LabelAnnotations {
    private String mid;
    private String description;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String score;



}
