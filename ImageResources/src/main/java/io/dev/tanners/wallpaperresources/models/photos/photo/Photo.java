package io.dev.tanners.wallpaperresources.models.photos.photo;

import android.os.Parcel;
import android.os.Parcelable;

import io.dev.tanners.wallpaperresources.models.Urls.Urls;
import io.dev.tanners.wallpaperresources.models.User.User;
import io.dev.tanners.wallpaperresources.models.links.Links;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo implements Parcelable {
    private String id;
    private String color;
    private String description;
    private Urls urls;
    private Links links;
    private User user;

    public Photo() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    protected Photo(Parcel in) {
        id = in.readString();
        color = in.readString();
        description = in.readString();
        urls = (Urls) in.readValue(Urls.class.getClassLoader());
        links = (Links) in.readValue(Links.class.getClassLoader());
        user = (User) in.readValue(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(color);
        dest.writeString(description);
        dest.writeValue(urls);
        dest.writeValue(links);
        dest.writeValue(user);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
