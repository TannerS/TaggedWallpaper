package io.tanners.taggedwallpaper.data.results.photo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * hold url data provided by results on photo / search api
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Urls {

    public Urls() {
    }

    public Urls(String small, String thumb) {
        this.small = small;
        this.thumb = thumb;
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

    private String small;
    private String thumb;
}
