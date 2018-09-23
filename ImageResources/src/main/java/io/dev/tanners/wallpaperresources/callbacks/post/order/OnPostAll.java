package io.dev.tanners.wallpaperresources.callbacks.post.order;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;

public interface OnPostAll extends OnPost<Photos> {
    public void onPostCall(Photos mData);
}