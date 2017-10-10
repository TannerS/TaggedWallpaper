package io.tanners.taggedwallpaper.network.images;

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
//    public ImageRequest(HashMap<String, String> headers, String mUrl, String body) {
//        super(headers, mUrl, body);
//    }

//    public ImageRequest() {
//        super();
//    }

    @Override
    public List<PhotoResult> getResult(HashMap<String, String> headers, String mUrl, String body) throws IOException {
        List<PhotoResult> photos = null;

        mConnectionRequest = new ConnectionRequest(mUrl);

//        if(mBody != null && mBody.length() > 0)
        if(body != null && body.length() > 0)
            headers.put("Content-Length", "" + body.getBytes().length);

        headers.put("Content-Language", "" + "en-US");
        mConnectionRequest.addRequestHeader(headers);
//        mConnectionRequest.addRequestHeader(mHeaders);
//        mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);
        mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);
        mConnectionRequest.addBasicBody(body);

        mConnectionRequest.connect();

        if(mConnectionRequest.getConnection() != null) {
            String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream());

            ObjectMapper objectMapper = new ObjectMapper();

            photos = (objectMapper.readValue(response, PhotosResultsWrapper.class)).getHits();

            mConnectionRequest.closeConnection();
        }

        return photos;
    }
}



