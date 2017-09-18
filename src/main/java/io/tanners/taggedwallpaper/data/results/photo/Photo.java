package io.tanners.taggedwallpaper.data.results.photo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * hold data provided by results on photo data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo
{
    private String id;

    public Photo() {

    }

    public Photo(String id, Urls urls) {
        this.id = id;
        this.urls = urls;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    private Urls urls;
}
