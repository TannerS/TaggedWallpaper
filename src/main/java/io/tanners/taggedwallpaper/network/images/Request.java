package io.tanners.taggedwallpaper.network.images;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public abstract class Request<T>
{
    // restful connection (http)
    protected ConnectionRequest mConnectionRequest;
    public abstract List<T> getResult(HashMap<String, String> mHeaders, String mUrl, final String body) throws IOException;
}



