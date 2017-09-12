package io.tanners.taggedwallpaper.data.results.photo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchWrapper {
    private int total;

    public SearchWrapper(int total, int total_pages, ArrayList<Photo> results) {
        this.total = total;
        this.total_pages = total_pages;
        this.results = results;
    }

    public SearchWrapper() {
    }


    private int total_pages;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Photo> getResults() {
        return results;
    }

    public void setResults(ArrayList<Photo> results) {
        this.results = results;
    }

    private ArrayList<Photo> results;
}
