package io.tanners.taggedwallpaper.mappings.vision.request.base;

public enum Type
{
    SAFE_SEARCH_DETECTION("SAFE_SEARCH_DETECTION"),
    LABEL_DETECTION("LABEL_DETECTION");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String value() {
        return type;
    }
}
