package com.tanners.taggedwallpaper.mappings.vision.request;

public class Image
{
    public Image(Source source) {
        this.source = source;
    }

    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
