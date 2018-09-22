package io.dev.tanners.wallpaperresources.models.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.dev.tanners.wallpaperresources.models.User.profile_image.ProfileImage;
import io.dev.tanners.wallpaperresources.models.User.userlinks.UserLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String username;
    private String name;
    private UserLinks links;
    private ProfileImage profile_image;
    private String instagram_username;
    private String twitter_username;
}
