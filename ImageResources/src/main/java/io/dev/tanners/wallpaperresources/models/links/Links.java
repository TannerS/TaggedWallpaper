package io.dev.tanners.wallpaperresources.models.links;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
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

    /*
    "links":{
"self":"https://api.unsplash.com/photos/Dwu85P9SOIk",
"html":"https://unsplash.com/photos/Dwu85P9SOIk",
"download":"https://unsplash.com/photos/Dwu85P9SOIk/download""download_location":"https://api.unsplash.com/photos/Dwu85P9SOIk/download"
},
     */
}
