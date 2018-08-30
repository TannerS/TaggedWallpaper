package io.tanners.taggedwallpaper.model.results.photo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotosResultsWrapper
{
    private int totalHits;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public ArrayList<PhotoResult> getHits() {
        return hits;
    }

    public void setHits(ArrayList<PhotoResult> hits) {
        this.hits = hits;
    }

    public PhotosResultsWrapper() {
    }

    public PhotosResultsWrapper(int totalHits, ArrayList<PhotoResult> hits) {
        this.totalHits = totalHits;
        this.hits = hits;
    }

    ArrayList<PhotoResult> hits;
}
