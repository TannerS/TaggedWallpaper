package io.tanners.taggedwallpaper.network.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import io.tanners.taggedwallpaper.model.results.photo.PhotoResult;
import io.tanners.taggedwallpaper.model.results.photo.PhotosResultsWrapper;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public class ImageRequest
{
    private ConnectionRequest mConnectionRequest;

    /**
     * get images after restful call
     *
     * @param headers
     * @param mUrl
     * @param body
     * @return
     * @throws IOException
     */
    public List<PhotoResult> getResult(HashMap<String, String> headers, String mUrl, String body) throws IOException {
        // temp list of images
        List<PhotoResult> photos = null;
        try {
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
            // get response, check for null just in case
            if(mConnectionRequest.getConnection() != null) {

                String response = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    response = IOUtils.toString(
                            mConnectionRequest.getConnection().getInputStream(),
                            StandardCharsets.UTF_8.name());
                } else {
                    response = IOUtils.toString(
                            mConnectionRequest.getConnection().getInputStream(),
                            "utf-8");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                // map objects from json
                photos = (objectMapper.readValue(response, PhotosResultsWrapper.class)).getHits();
                // close connection
                mConnectionRequest.closeConnection();
            }
        } catch (IOException e) {
            // throw back to parent call
            throw e;
        }
        // return new photos
        return photos;
    }
}



