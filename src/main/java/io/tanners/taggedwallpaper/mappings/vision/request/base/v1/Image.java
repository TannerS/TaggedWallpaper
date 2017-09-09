package io.tanners.taggedwallpaper.mappings.vision.request.base.v1;

import io.tanners.taggedwallpaper.mappings.vision.request.base.BaseImage;

public class Image extends BaseImage
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
