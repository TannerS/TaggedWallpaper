package io.dev.tanners.wallpaperresources;

import android.content.Context;
import android.net.Uri;
import io.dev.tanners.wallpaperresources.builder.Builder;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.network.ImageLoader;
import io.dev.tanners.wallpaperresources.network.ImageLoaderAll;
import io.dev.tanners.wallpaperresources.network.ImageLoaderSearch;
import io.dev.tanners.wallpaperresources.network.ImageLoaderSingle;

public class ImageRequester {
    private Context mContext;

    public ImageRequester(Context mContext) {
        this.mContext = mContext;
    }

    public void getPhotos(String page, String perPage, ConfigPhotosAll.Order order, ImageLoader.ImageRequest mCallback) {
        Uri.Builder mBuilder = Builder.getPhotosAllBuilder(perPage, page, order);
        ImageLoaderAll mImageLoader = new ImageLoaderAll(mContext);
        mImageLoader.loadLoader(mBuilder.build().toString(), mCallback);
    }

    public void getPhoto(String id, ImageLoader.ImageRequest mCallback) {
        Uri.Builder mBuilder = Builder.getPhotoByIdBuilder(id);
        ImageLoaderSingle mImageLoader = new ImageLoaderSingle(mContext);
        mImageLoader.loadLoader(mBuilder.build().toString(), mCallback);
    }

    public void getSearchPhoto(String query, String page, String perPage, ImageLoader.ImageRequest mCallback) {
        Uri.Builder mBuilder = Builder.getPhotoSearchBuilder(query, perPage, page);
        ImageLoaderSearch mImageLoader = new ImageLoaderSearch(mContext);
        mImageLoader.loadLoader(mBuilder.build().toString(), mCallback);
    }

    public void getDownloadPhoto(String query, ImageLoader.ImageRequest mCallback) {
        Uri.Builder mBuilder = Builder.getPhotoDownloadBuilder(query);
        ImageLoaderSearch mImageLoader = new ImageLoaderSearch(mContext);
        mImageLoader.loadLoader(mBuilder.build().toString(), mCallback);
    }

}
