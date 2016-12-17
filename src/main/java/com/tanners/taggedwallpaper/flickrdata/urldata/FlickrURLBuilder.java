package com.tanners.taggedwallpaper.flickrdata.urldata;

public class FlickrURLBuilder
{
    private final String APP_KEY = "&api_key=1adb86b105be09a175478f8a4f2945aa";
    private final String BASEURL = "https://api.flickr.com/services/rest/";
    private final String METHOD = "?method=";
    private final String FORMAT = "&format=json&nojsoncallback=1";
    private final String PER_PAGE = "&per_page=";
    private final String PAGE = "&page=";
    private final String EXTRAS = "&extras=url_n%2Curl_z%2Curl_m%2Curl_c%2Curl_b%2Curl_h%2Curl_k%2Curl_o";
    private final String FLICKR_GROUPS_METHOD = "flickr.groups.pools.getPhotos";
    private final String FLICKR_GROUPS_GROUP_ID = "&group_id=";
    private final String FLICKR_GROUPS_ID= "40961104@N00";
    private final String GET_INFO_METHOD = "flickr.people.getInfo";
    private final String GET_INFO_ID ="&user_id=";
    private final String FLICKR_SEARCH_METHOD = "flickr.photos.search";
    private final String FLICKR_SEARCH_PARAMETERS = "&privacy_filter=public&content_type=1&safe_search=1&media=photos";
    private final String FLICKR_SEARCH_EXTRA = "&extras=url_z%2C+url_t%2C+url_m%2C+url_n";
    private final String FLICKR_SEARCH_TAG = "&tags=";
    private final String FLICKR_INTERESTINGNESS = "flickr.interestingness.getList";

    public String getGroupPhotos(String group, int per_page, int page)
    {
        String page_ = PAGE + Integer.toString(page);
        String page_per_ = PER_PAGE + Integer.toString(per_page);
        return (BASEURL + METHOD + FLICKR_SEARCH_METHOD + APP_KEY + FLICKR_GROUPS_GROUP_ID + group + FLICKR_SEARCH_PARAMETERS + EXTRAS + page_per_ + page_ + FORMAT);
    }

    public String getAllPhotos(String tag, int per_page, int page)
    {
        String page_ = PAGE + Integer.toString(page);
        String page_per_ = PER_PAGE + Integer.toString(per_page);
        return (BASEURL + METHOD + FLICKR_GROUPS_METHOD + APP_KEY + FLICKR_GROUPS_GROUP_ID + FLICKR_GROUPS_ID + FLICKR_SEARCH_TAG + tag  + EXTRAS + page_per_ + page_ + FORMAT);
    }

    public String getUserInfo(String user_id )
    {
        String complete_user_id = GET_INFO_ID + user_id;
        return (BASEURL + METHOD + GET_INFO_METHOD + APP_KEY + complete_user_id + FORMAT);
    }

    public String getRecentPhotos(int per_page, int page)
    {
        String page_ = PAGE + Integer.toString(page);
        String page_per_ = PER_PAGE + Integer.toString(per_page);
        return (BASEURL + METHOD + FLICKR_INTERESTINGNESS + APP_KEY + EXTRAS + page_per_ + page_ + FORMAT);
    }
}
