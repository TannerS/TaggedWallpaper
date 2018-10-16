package io.dev.tanners.wallpaperresources.models.Urls;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Urls  implements Parcelable {
    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;

    public Urls() { }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }


    protected Urls(Parcel in) {
        raw = in.readString();
        full = in.readString();
        regular = in.readString();
        small = in.readString();
        thumb = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(raw);
        dest.writeString(full);
        dest.writeString(regular);
        dest.writeString(small);
        dest.writeString(thumb);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Urls> CREATOR = new Parcelable.Creator<Urls>() {
        @Override
        public Urls createFromParcel(Parcel in) {
            return new Urls(in);
        }

        @Override
        public Urls[] newArray(int size) {
            return new Urls[size];
        }
    };

    /*
"urls":{
"raw":"https://images.unsplash.com/photo-1417325384643-aac51acc9e5d",
"full":"https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg",
"regular":"https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=1080&fit=max",
"small":"https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=400&fit=max",
"thumb":"https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=200&fit=max"
},
     */
}



