package com.tanners.taggedwallpaper.mappings.vision.request;

public class Feature
{
    public Feature(String type, int maxResults) {
        this.type = type;
        this.maxResults = maxResults;
    }

    public Feature(String type) {
        this.type = type;
        this.maxResults = 1;
    }

    private String type;
    private int maxResults;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
