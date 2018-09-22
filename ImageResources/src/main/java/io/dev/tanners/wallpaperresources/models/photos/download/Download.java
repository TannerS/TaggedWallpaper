package io.dev.tanners.wallpaperresources.models.photos.download;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Download {
    private String url;
}
