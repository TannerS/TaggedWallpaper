package io.tanners.taggedwallpaper.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.Date;
import io.tanners.taggedwallpaper.db.config.DBConfig;

/**
 * Entry for database. Since the photo class is in a separate library,
 * we extend it here to add more functionality and to use it
 */
@Entity(tableName = DBConfig.TABLE_NAME)
@TypeConverters(TimestampConverter.class)
public class ImageEntry implements Parcelable {
    @PrimaryKey
    @NonNull
    protected String id;
    protected String imageUrl;
    protected Date timestamp;
    protected String desc;

    public ImageEntry() { }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    protected ImageEntry(Parcel in) {
        id = in.readString();
        imageUrl = in.readString();
        long tmpTimestamp = in.readLong();
        timestamp = tmpTimestamp != -1 ? new Date(tmpTimestamp) : null;
        desc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeLong(timestamp != null ? timestamp.getTime() : -1L);
        dest.writeString(desc);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ImageEntry> CREATOR = new Parcelable.Creator<ImageEntry>() {
        @Override
        public ImageEntry createFromParcel(Parcel in) {
            return new ImageEntry(in);
        }

        @Override
        public ImageEntry[] newArray(int size) {
            return new ImageEntry[size];
        }
    };
}
