package io.dev.tanners.wallpaperresources.callbacks.post.order;

import java.util.ArrayList;
import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

/**
 * Callback after getting a single photo call
 */
public interface OnPostAll extends OnPost<ArrayList<Photo>> {
    public void onPostCall(ArrayList<Photo> mData);
}