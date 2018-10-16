package io.dev.tanners.wallpaperresources.models.User.userlinks;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.dev.tanners.wallpaperresources.models.links.Links;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLinks extends Links implements Parcelable {
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

    protected UserLinks(Parcel in) {
        portfolio = in.readString();
        photos = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(portfolio);
        dest.writeString(photos);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserLinks> CREATOR = new Parcelable.Creator<UserLinks>() {
        @Override
        public UserLinks createFromParcel(Parcel in) {
            return new UserLinks(in);
        }

        @Override
        public UserLinks[] newArray(int size) {
            return new UserLinks[size];
        }
    };
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
