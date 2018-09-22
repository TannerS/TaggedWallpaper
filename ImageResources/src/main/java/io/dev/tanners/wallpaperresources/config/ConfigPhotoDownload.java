package io.dev.tanners.wallpaperresources.config;

/**
 * To abide by the API guidelines, you need to trigger a GET request to this endpoint every time
 * your application performs a download of a photo. To understand what constitutes a download,
 * please refer to the ‘Triggering a download’ guideline.
 * This is purely an event endpoint used to increment the number of downloads a photo has.
 * You can think of it very similarly to the pageview event in Google Analytics—where you’re
 * incrementing a counter on the backend. This endpoint is not to be used to embed the photo
 * (use the photo.urls.* properties instead) or to direct the user to the downloaded photo
 * (use the photo.urls.full instead), it is for tracking purposes only.
 * Note: This is different than the concept of a view, which is tracked automatically when you hotlink an image
 */
public class ConfigPhotoDownload extends ConfigBase {
    public static final String GET_PHOTO_BY_ID_METHOD_PART1 = "photos";
    public static final String GET_PHOTO_BY_ID_METHOD_PART2= "download";
}
