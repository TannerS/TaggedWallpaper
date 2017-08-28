package com.tanners.taggedwallpaper.mappings.photodata;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoContainer
{
    private Photos photos;

    public Photos getPhotos()
    {
        return photos;
    }

    public void setPhotos(Photos photos)
    {
        this.photos = photos;
    }
}
