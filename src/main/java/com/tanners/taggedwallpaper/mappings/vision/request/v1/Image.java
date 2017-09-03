package com.tanners.taggedwallpaper.mappings.vision.request.v1;

import com.tanners.taggedwallpaper.mappings.vision.request.v1.Source;

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
