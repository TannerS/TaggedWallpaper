package io.dev.tanners.wallpaperresources;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import io.dev.tanners.wallpaperresources.builder.ImageUriBuilder;
import io.dev.tanners.wallpaperresources.callbacks.post.download.OnPostDownload;
import io.dev.tanners.wallpaperresources.callbacks.post.order.OnPostAll;
import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.callbacks.post.single.OnPostSingle;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.loader.rest.ImageLoaderAllLatest;
import io.dev.tanners.wallpaperresources.loader.rest.ImageLoaderAllPopular;
import io.dev.tanners.wallpaperresources.loader.rest.ImageLoaderDownloadHotLink;
import io.dev.tanners.wallpaperresources.loader.rest.ImageLoaderSearch;
import io.dev.tanners.wallpaperresources.loader.rest.ImageLoaderSingle;
import io.dev.tanners.wallpaperresources.support.ImageDownloadHotlinkAsync;

public class ImageRequester {
    private Context mContext;

    public ImageRequester(Context mContext) {
        this.mContext = mContext;
    }

    public void getPhotos(String page, String perPage, ConfigPhotosAll.Order order, OnPostAll mCallback) {
        // build base url
        Uri.Builder mBuilder = ImageUriBuilder.getPhotosAllBuilder(perPage, page, order);
        // need to break up due to load ids have to be unique
        switch(order) {
            case LATEST:
                (new ImageLoaderAllLatest(mContext)).loadLoader(mBuilder.build().toString(), mCallback);
                break;
            case POPULAR:
                (new ImageLoaderAllPopular(mContext)).loadLoader(mBuilder.build().toString(), mCallback);
                break;
        }
    }

    public void getPhoto(String id, OnPostSingle mCallback) {
        Uri.Builder mBuilder = ImageUriBuilder.getPhotoByIdBuilder(id);
        ImageLoaderSingle mImageLoader = new ImageLoaderSingle(mContext);
        mImageLoader.loadLoader(mBuilder.build().toString(), mCallback);
    }

    public void getSearchPhoto(String query, String page, String perPage, OnPostSearch mCallback) {
        Uri.Builder mBuilder = ImageUriBuilder.getPhotoSearchBuilder(query, perPage, page);
        ImageLoaderSearch mImageLoader = new ImageLoaderSearch(mContext);
        mImageLoader.loadLoader(mBuilder.build().toString(), mCallback);
    }

    public void getDownloadPhoto(String id, String url, String albumName, OnPostDownload mCallback) {
        ImageLoaderDownloadHotLink mImageLoader = new ImageLoaderDownloadHotLink(mContext);
        mImageLoader.loadLoader(url, albumName, mCallback);
        getDownloadPhotoHotLink(id);
    }

    /**
     * As part of the api, this must be sent out
     *
     * https://unsplash.com/documentation#track-a-photo-download
     *
     * This is on a asynctask since we do not care about rotation or etc
     * for this like we would worry about in normal cases to use a loader
     *
     * @param id
     */
    private void getDownloadPhotoHotLink(String id) {
        Uri.Builder mBuilder = ImageUriBuilder.getPhotoDownloadBuilder(id);
        String mUrl = mBuilder.build().toString();
        // start async for request
        new ImageDownloadHotlinkAsync().execute(mUrl);
    }
}
