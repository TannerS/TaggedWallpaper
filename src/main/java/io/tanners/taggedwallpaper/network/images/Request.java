package io.tanners.taggedwallpaper.network.images;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public abstract class Request<T>
{
    protected ConnectionRequest mConnectionRequest;
//    protected String mUrl;
//    protected String mBody;
//    protected HashMap<String, String> mHeaders;

//    public Request(HashMap<String, String> mHeaders, String mUrl, final String body)
//    {
//        this.mUrl = mUrl;
//        this.mBody = body;
//        this.mHeaders = mHeaders;
//    }

//    public Request()
//    {
//        this.mUrl = null;
//        this.mBody = null;
//        this.mHeaders = null;
//    }


//    public abstract List<T> getResult() throws IOException;
    public abstract List<T> getResult(HashMap<String, String> mHeaders, String mUrl, final String body) throws IOException;
}



