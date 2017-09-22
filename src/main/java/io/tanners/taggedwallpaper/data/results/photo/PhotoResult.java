package io.tanners.taggedwallpaper.data.results.photo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PhotoResult
{
    private String largeImageUrl;
    private String fullHDURL;

    public PhotoResult(String largeImageUrl, String fullHDURL, String imageURL, String previewURL, String user) {
        this.largeImageUrl = largeImageUrl;
        this.fullHDURL = fullHDURL;
        this.imageURL = imageURL;
        this.previewURL = previewURL;
        this.user = user;
    }

    public PhotoResult() {
    }

    private String imageURL;
    private String previewURL;
    private String user;
//    private String id_hash;
//    private String userImageURL;

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getFullHDURL() {
        return fullHDURL;
    }

    public void setFullHDURL(String fullHDURL) {
        this.fullHDURL = fullHDURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
//    private String userImageURL;

}
