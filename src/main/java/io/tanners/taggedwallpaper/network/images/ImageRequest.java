package io.tanners.taggedwallpaper.network.images;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.tanners.taggedwallpaper.data.results.photo.Photo;
import io.tanners.taggedwallpaper.data.results.photo.SearchWrapper;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public class ImageRequest extends Request
{
    public ImageRequest(HashMap<String, String> headers, String mUrl, String body) {
        super(headers, mUrl, body);


    }

    @Override
//    public List<Photo> getPhotos(Requested mapping)
    public List<Photo> getPhotos()
    {
        List<Photo> photos = null;

        try
        {
            mConnectionRequest = new ConnectionRequest(mUrl);

            if(mBody != null && mBody.length() > 0)
                mHeaders.put("Content-Length", "" + mBody.getBytes().length);
            mHeaders.put("Content-Language", "" + "en-US");

            mConnectionRequest.addRequestHeader(mHeaders);

            mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);

            mConnectionRequest.addBasicBody(mBody);


            mConnectionRequest.connect();

            if(mConnectionRequest.getConnection() != null)
            {
                    String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream());

                Log.i("REQUEST", "RESPONSE: " + response);

                ObjectMapper objectMapper = new ObjectMapper();

//                switch(mapping)
//                {
//                    case SEARCH:
                        photos = (objectMapper.readValue(response, SearchWrapper.class)).getResults();
//                        break;
//                }
            }
        } catch (JsonMappingException | JsonParseException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            mConnectionRequest.closeConnection();
        }

        return photos;
    }
}



