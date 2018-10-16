package io.dev.tanners.wallpaperresources.models.links;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links implements Parcelable {
    private String self;
    private String html;
    private String download;
    private String download_location;

    public Links() { }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload_location() {
        return download_location;
    }

    public void setDownload_location(String download_location) {
        this.download_location = download_location;
    }

    protected Links(Parcel in) {
        self = in.readString();
        html = in.readString();
        download = in.readString();
        download_location = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeString(html);
        dest.writeString(download);
        dest.writeString(download_location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Links> CREATOR = new Parcelable.Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel in) {
            return new Links(in);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    /*
    "links":{
"self":"https://api.unsplash.com/photos/Dwu85P9SOIk",
"html":"https://unsplash.com/photos/Dwu85P9SOIk",
"download":"https://unsplash.com/photos/Dwu85P9SOIk/download""download_location":"https://api.unsplash.com/photos/Dwu85P9SOIk/download"
},
     */
}
