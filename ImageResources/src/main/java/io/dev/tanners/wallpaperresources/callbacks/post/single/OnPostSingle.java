package io.dev.tanners.wallpaperresources.callbacks.post.single;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

/**
 * Callback after getting a single photo call
 */
public interface OnPostSingle extends OnPost<Photo> {
    public void onPostCall(Photo mData);
}