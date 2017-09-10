package io.tanners.taggedwallpaper.mappings.results.photo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo
{
    private String id;
//    private int width;
//    private int height;

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

    //    private String Description;
    private Urls urls;
}
