package io.dev.tanners.wallpaperresources.callbacks.post.search;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

public interface OnPostSearch extends OnPost<PhotoSearch> {
    public void onPostCall(PhotoSearch mData);
}