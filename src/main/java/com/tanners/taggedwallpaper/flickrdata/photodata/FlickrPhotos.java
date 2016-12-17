package com.tanners.taggedwallpaper.flickrdata.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotos
{
    private List<FlickrPhotoItem> photo;

    public List<FlickrPhotoItem> getPhoto() {
        return photo;
    }
}
