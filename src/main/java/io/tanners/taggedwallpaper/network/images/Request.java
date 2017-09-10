package io.tanners.taggedwallpaper.network.images;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.tanners.taggedwallpaper.mappings.results.photo.Photo;
import io.tanners.taggedwallpaper.network.ConnectionRequest;



public abstract class Request
{
    protected ConnectionRequest mConnectionRequest;

    public Request(String mUrl)
    {
        // ?utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit
        mConnectionRequest = new ConnectionRequest(mUrl);
    }


    public abstract ArrayList<Photo> getPhotos();
}



