package io.tanners.taggedwallpaper.network.images;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.data.results.photo.Photo;
import io.tanners.taggedwallpaper.network.ConnectionRequest;



public abstract class Request
{
    protected ConnectionRequest mConnectionRequest;

    public Request(String mUrl)
    {
        // ?utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit
        mConnectionRequest = new ConnectionRequest(mUrl);
    }


    public abstract List<Photo> getPhotos();
}



