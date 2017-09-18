package io.tanners.taggedwallpaper.network.images;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

import io.tanners.taggedwallpaper.data.results.photo.Photo;
import io.tanners.taggedwallpaper.data.results.photo.SearchWrapper;

public class ImageRequest extends Request
{
//    public ImageRequest(String mUrl) {
//        super(mUrl);
//    }

    public ImageRequest(String mUrl, String body) {
        super(mUrl, body);
    }

    @Override
    public List<Photo> getPhotos(Requested mapping)
    {
        List<Photo> photos = null;

        try
        {
            mConnectionRequest.connect();

            if(mConnectionRequest.getConnection() != null)
            {
                    String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream());

                Log.i("REQUEST", "RESPONSE: " + response);

                ObjectMapper objectMapper = new ObjectMapper();

                switch(mapping)
                {
//                    case POPULAR:
//                        photos = objectMapper.readValue(response, new TypeReference<List<Photo>>(){});
//                        break;
                    case SEARCH:
                        photos = (objectMapper.readValue(response, SearchWrapper.class)).getResults();
                        break;
                }
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



