package io.dev.tanners.wallpaperresources.models.User.userlinks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.dev.tanners.wallpaperresources.models.links.Links;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLinks extends Links {
    private String portfolio;
    private String photos;

    public UserLinks() { }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    /*
    "links":{
   "self":"https://api.unsplash.com/users/exampleuser",
   "html":"https://unsplash.com/exampleuser",
   "photos":"https://api.unsplash.com/users/exampleuser/photos",
   "likes":"https://api.unsplash.com/users/exampleuser/likes",
   "portfolio":"https://api.unsplash.com/users/exampleuser/portfolio
},
     */
}
