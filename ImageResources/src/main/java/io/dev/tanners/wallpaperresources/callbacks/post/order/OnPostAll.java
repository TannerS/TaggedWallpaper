package io.dev.tanners.wallpaperresources.callbacks.post.order;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;

//public interface OnPostAll extends OnPost<Photos> {
public interface OnPostAll extends OnPost<ArrayList<Photo>> {
//    public void onPostCall(Photos mData);
    public void onPostCall(ArrayList<Photo> mData);
}