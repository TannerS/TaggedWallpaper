package io.dev.tanners.wallpaperresources.callbacks.post.search;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

/**
 * Callback after getting a search query
 */
public interface OnPostSearch extends OnPost<PhotoSearch> {
    public void onPostCall(PhotoSearch mData);
}