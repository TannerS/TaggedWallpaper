package io.dev.tanners.wallpaperresources.models.User.profile_image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileImage {
    private String small;
    private String medium;
    private String large;
}
