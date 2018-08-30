package io.tanners.taggedwallpaper.model.results.photo;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoResult implements Parcelable
{
    public PhotoResult() {
    }

    protected PhotoResult(Parcel in) {
        largeImageURL = in.readString();
        webformatURL = in.readString();
        imageURL = in.readString();
        previewURL = in.readString();
        user = in.readString();
        user_id = in.readInt();
        id = in.readInt();
        tags = in.readString();
        userImageURL = in.readString();
        id_hash = in.readString();
    }

    public static final Creator<PhotoResult> CREATOR = new Creator<PhotoResult>() {
        @Override
        public PhotoResult createFromParcel(Parcel in) {
            return new PhotoResult(in);
        }

        @Override
        public PhotoResult[] newArray(int size) {
            return new PhotoResult[size];
        }
    };

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    private String largeImageURL;
    private String webformatURL;
    private String imageURL;
    private String previewURL;
    private String user;
    private int user_id;
    private int id;

    private String tags;
    private String userImageURL;

    public String getId_hash() {
        return id_hash;
    }

    public void setId_hash(String id_hash) {
        this.id_hash = id_hash;
    }

    private String id_hash;

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(largeImageURL);
        dest.writeString(webformatURL);
        dest.writeString(imageURL);
        dest.writeString(previewURL);
        dest.writeString(user);
        dest.writeInt(user_id);
        dest.writeInt(id);
        dest.writeString(tags);
        dest.writeString(userImageURL);
        dest.writeString(id_hash);
    }
}
