package io.tanners.taggedwallpaper.network.images;

import android.os.Build;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;
import io.tanners.taggedwallpaper.data.results.photo.PhotosResultsWrapper;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public class ImageRequest extends Request<PhotoResult>
{
    /**
     * get images after restful call
     * @param headers
     * @param mUrl
     * @param body
     * @return
     * @throws IOException
     */
    @Override
    public List<PhotoResult> getResult(HashMap<String, String> headers, String mUrl, String body) throws IOException {
        // temp list of images
        List<PhotoResult> photos = null;
        // make connect to url
            mConnectionRequest = new ConnectionRequest(mUrl);
        // if set body does not exist
        if (body != null && body.length() > 0) {
            // put body length
            headers.put("Content-Length", "" + body.getBytes().length);
            // set as post to deliver body content
            mConnectionRequest.setRequestType(ConnectionRequest.TYPES.POST);
            // set body
            mConnectionRequest.addBasicBody(body);
        }
        else
        {
            // no body, use get
            mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);
            // set empty body
            mConnectionRequest.addBasicBody("");
        }
        // put needed header
        headers.put("Content-Language", "" + "en-US");
        mConnectionRequest.addRequestHeader(headers);
        // connect to url
        mConnectionRequest.connect();
        // get response
        if(mConnectionRequest.getConnection() != null) {
            try
            {
                String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream());
                ObjectMapper objectMapper = new ObjectMapper();
                // map objects from json
                photos = (objectMapper.readValue(response, PhotosResultsWrapper.class)).getHits();
                // close connection
                mConnectionRequest.closeConnection();
            }
            catch(java.net.UnknownHostException ex)
            {
                // TODO .
                return null;
            }
        }
        // return new photos
        return photos;
    }
}



