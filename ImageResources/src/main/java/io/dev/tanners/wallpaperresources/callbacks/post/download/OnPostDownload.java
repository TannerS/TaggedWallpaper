package io.dev.tanners.wallpaperresources.callbacks.post.download;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;

public interface OnPostDownload extends OnPost<Download> {
    public void onPostCall(Download mData);
}