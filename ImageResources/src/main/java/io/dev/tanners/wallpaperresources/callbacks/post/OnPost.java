package io.dev.tanners.wallpaperresources.callbacks.post;

/**
 * Callback base
 */
public interface OnPost<T> {
    public void onPostCall(T mData);
}