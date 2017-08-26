package com.tanners.taggedwallpaper.data.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos
{
    private List<PhotoItem> photo;

    public List<PhotoItem> getPhoto() {
        return photo;
    }
}
