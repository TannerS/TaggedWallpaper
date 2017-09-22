package io.tanners.taggedwallpaper.data.results.photo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotosResultsWrapper
{

    private int totalHits;

    public PhotosResultsWrapper() {
    }


    public PhotosResultsWrapper(int totalHits, ArrayList<PhotoResult> hits) {
        this.totalHits = totalHits;
        this.hits = hits;
    }

    ArrayList<PhotoResult> hits;
}
