package com.tanners.taggedwallpaper.mappings.vision.request.v1;

public class Source
{
    public Source(String imageUri) {
        this.imageUri = imageUri;
    }

    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

