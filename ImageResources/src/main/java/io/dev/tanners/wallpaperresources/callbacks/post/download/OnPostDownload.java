package io.dev.tanners.wallpaperresources.callbacks.post.download;

import java.io.File;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.loader.rest.download.RestDownloadLoader;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;

public interface OnPostDownload extends OnPost<Download> {
    public void onPostCall(Download mData);
    public void onPostCall(RestDownloadLoader.RestDownloadLoaderReturn mData);
}