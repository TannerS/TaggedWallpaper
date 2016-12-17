package com.tanners.taggedwallpaper.flickrdata.photodata;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrPhotoContainer
{
    private FlickrPhotos photos;

    public FlickrPhotos getPhotos()
    {
        return photos;
    }

    public void setPhotos(FlickrPhotos photos)
    {
        this.photos = photos;
    }
}
