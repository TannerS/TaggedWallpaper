package io.dev.tanners.wallpaperresources.builder;

import android.net.Uri;
import io.dev.tanners.wallpaperresources.config.ConfigBase;
import io.dev.tanners.wallpaperresources.config.ConfigPhotoById;
import io.dev.tanners.wallpaperresources.config.ConfigPhotoDownload;
import io.dev.tanners.wallpaperresources.config.ConfigPhotoSearch;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;

/**
 * Build image Uri
 */
public class ImageUriBuilder {
    private static Uri.Builder getBaseUri() {
        return (new Uri.Builder())
                .scheme(
                        ConfigBase.Protocol.HTTPS.protocol()
                )
                .authority(ConfigBase.BASE_URL);
    }

    /**
     * Build Uri for all getting all photos
     *
     * @param perPage
     * @param page
     * @param type
     * @return
     */
    public static Uri.Builder getPhotosAllBuilder(String perPage, String page, ConfigPhotosAll.Order type) {
        String mType = type.order();

        if(mType == null || mType.length() == 0) {
            mType = ConfigPhotosAll.QUERY_ORDER_VALUE;
        }

        return getBaseUri()
                .appendPath(ConfigPhotosAll.GET_PHOTO_METHOD)
                .appendQueryParameter(ConfigPhotosAll.QUERY_PAGE_KEY, page)
                .appendQueryParameter(ConfigPhotosAll.QUERY_PER_PAGE_KEY, perPage)
                .appendQueryParameter(ConfigPhotosAll.QUERY_ORDER_KEY, mType);
    }

    /**
     * Build Uri for all getting photo by id
     *
     * @param id
     * @return
     */
    public static Uri.Builder getPhotoByIdBuilder(String id) {
        return getBaseUri()
                .appendPath(ConfigPhotoById.GET_PHOTO_BY_ID_METHOD)
                .appendPath(id);
    }

    /**
     * Build Uri for all getting an image download
     *
     * @param id
     * @return
     */
    public static Uri.Builder getPhotoDownloadBuilder(String id) {
        return getBaseUri()
                .appendPath(ConfigPhotoDownload.GET_PHOTO_BY_ID_METHOD_PART1)
                .appendPath(id)
                .appendPath(ConfigPhotoDownload.GET_PHOTO_BY_ID_METHOD_PART2);
    }

    /**
     * Build Uri for search queries
     *
     * @param query
     * @param perPage
     * @param page
     * @return
     */
    public static Uri.Builder getPhotoSearchBuilder(String query, String perPage, String page) {
        return getBaseUri()
                .appendPath(ConfigPhotoSearch.GET_SEARCH_PHOTOS_METHOD_P1)
                .appendPath(ConfigPhotoSearch.GET_SEARCH_PHOTOS_METHOD_P2)
                .appendQueryParameter(ConfigPhotoSearch.QUERY_QUERY_KEY, query)
                .appendQueryParameter(ConfigPhotoSearch.QUERY_PER_PAGE_KEY, perPage)
                .appendQueryParameter(ConfigPhotoSearch.QUERY_PAGE_KEY, page);
    }
}
