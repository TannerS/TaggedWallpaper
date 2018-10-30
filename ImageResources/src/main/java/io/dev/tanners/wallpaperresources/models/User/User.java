package io.dev.tanners.wallpaperresources.models.User;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.dev.tanners.wallpaperresources.models.User.profile_image.ProfileImage;
import io.dev.tanners.wallpaperresources.models.User.userlinks.UserLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable {
    private String id;
    private String username;
    private String name;
    private UserLinks links;
    private ProfileImage profile_image;
    private String instagram_username;
    private String twitter_username;

    public User() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserLinks getLinks() {
        return links;
    }

    public void setLinks(UserLinks links) {
        this.links = links;
    }

    public ProfileImage getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImage profile_image) {
        this.profile_image = profile_image;
    }

    public String getInstagram_username() {
        return instagram_username;
    }

    public void setInstagram_username(String instagram_username) {
        this.instagram_username = instagram_username;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        name = in.readString();
        links = (UserLinks) in.readValue(UserLinks.class.getClassLoader());
        profile_image = (ProfileImage) in.readValue(ProfileImage.class.getClassLoader());
        instagram_username = in.readString();
        twitter_username = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeValue(links);
        dest.writeValue(profile_image);
        dest.writeString(instagram_username);
        dest.writeString(twitter_username);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /*
 "user": {
      "id": "pXhwzz1JtQU",
      "username": "poorkane",
      "name": "Gilbert Kane",
      "portfolio_url": "https://theylooklikeeggsorsomething.com/",
      "bio": "XO",
      "location": "Way out there",
      "total_likes": 5,
      "total_photos": 74,
      "total_collections": 52,
      "instagram_username": "instantgrammer",
      "twitter_username": "crew",
      "profile_image": {
        "small": "https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32",
        "medium": "https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64",
        "large": "https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128"
      },
     */
}
