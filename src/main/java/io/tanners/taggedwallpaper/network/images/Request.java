package io.tanners.taggedwallpaper.network.images;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.tanners.taggedwallpaper.data.results.photo.Photo;
import io.tanners.taggedwallpaper.network.ConnectionRequest;



public abstract class Request
{
    public static enum Requested {
        SEARCH("Search"),
        POPULAR("POPULAR");

        private String mapping;

        Requested(String mapping) {
            this.mapping = mapping;
        }

        public String mapping() {
            return mapping;
        }

    }

    protected ConnectionRequest mConnectionRequest;
    private String mApiRules = "&utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit";

    public Request(String mUrl)
    {
//         ?utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit
        mConnectionRequest = new ConnectionRequest(mUrl + mApiRules);
    }

    public Request(String mUrl, final String body)
    {
        this(mUrl);
        Log.i("REQUEST", "GET TAGS: " + mUrl);

        mConnectionRequest.addRequestHeader(new HashMap<String, String>() {{
            // TODO PKI for token
            put("Authorization", "Client-ID 53bec55730b75b73e5f615222f83e498e7645300c2b10949e6f8e25442a2fccc");
            put("Accept-Language", "en-US,en;q=0.5");
            put("Connection", "keep-alive");
            put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
            put("content-type", "text/html; charset=utf-8");
            put("content-type", "application/x-www-form-urlencoded; charset=utf-8");

            if(body != null && body.length() > 0)
                put("Content-Length", "" + body.getBytes().length);
            put("Content-Language", "" + "en-US");
        }});

        Log.i("REQUEST", ConnectionRequest.TYPES.GET.toString());

        mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);

        mConnectionRequest.addBasicBody(body);


//        mConnectionRequest.closeConnection();
    }


    public abstract List<Photo> getPhotos(Requested mapping);
}



