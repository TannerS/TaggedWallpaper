package com.tanners.taggedwallpaper.flickrdata;

import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanners.taggedwallpaper.util.FireBaseUtil;
import com.tanners.taggedwallpaper.mappings.photodata.PhotoContainer;
import com.tanners.taggedwallpaper.mappings.photodata.PhotoItem;
import com.tanners.taggedwallpaper.network.FlickrURLBuilder;
import com.tanners.taggedwallpaper.util.URLConnection;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlickrDataPhotosSearch
{
    private FlickrURLBuilder url;
    private int per_page;
    private int page;
    private FireBaseUtil fb_util;
    public static final int GROUP_SEARCH = 0;
    public static final int OPEN_SEARCH = 1;

    public FlickrDataPhotosSearch(Context context, int per_pages, int page)
    {
        url = new FlickrURLBuilder();
        this.page = page;
        this.per_page = per_pages;
        fb_util = new FireBaseUtil(context);
    }

    public FlickrDataPhotosSearch()
    {
        url = new FlickrURLBuilder();
        this.page = 1;
        this.per_page = 1000;
    }

    public List<PhotoItem> searchFlickr(String tag, int selection)
    {
        PhotoContainer flickr = null;
        List<PhotoItem> photos = new ArrayList<PhotoItem>();
        URLConnection connection = null;

        try
        {
            switch(selection)
            {
                case FlickrDataPhotosSearch.GROUP_SEARCH:
                    String group = fb_util.searchGroupByTag(tag);
                    connection = new URLConnection(url.getGroupPhotos(group, per_page, page));
                    break;

                case FlickrDataPhotosSearch.OPEN_SEARCH:
                    connection = new URLConnection(url.getAllPhotos(tag, per_page, page));
                    break;
            }
//
            if(connection.getHttpURLConnection() != null)
            {
                String response = IOUtils.toString(connection.getHttpURLConnection().getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                flickr = objectMapper.readValue(response, PhotoContainer.class);
            }

            if(flickr != null)
            {
                return flickr.getPhotos().getPhoto();
            }
            else
                return Collections.EMPTY_LIST;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
}



