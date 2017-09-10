package io.tanners.taggedwallpaper.network.images;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import io.tanners.taggedwallpaper.mappings.results.photo.Photo;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public class TagImageRequest extends Request
{
//    private ConnectionRequest mConnectionRequest;

    public TagImageRequest(String mUrl, String body)
    {
        super(mUrl);
        // ?utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit
//        mConnectionRequest = new ConnectionRequest(mUrl);

        mConnectionRequest.addRequestHeader(new HashMap<String, String>() {{
            // TODO PKI for token
            put("Authorization", "Client-ID 5");
            put("Accept-Language", "en-US,en;q=0.5");
            put("Connection", "keep-alive");
            put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
//            put("content-type", "text/html; charset=utf-8");
        }});

        mConnectionRequest.setRequestType(ConnectionRequest.TYPES.GET);

        mConnectionRequest.addBasicBody(body);


//        mConnectionRequest.closeConnection();
    }

    @Override
    public ArrayList<Photo> getPhotos()
    {
        ArrayList<Photo> photos = new ArrayList<Photo>();

        try
        {
            mConnectionRequest.connect();

            if(mConnectionRequest.getConnection() != null)
            {
//                if(mConnectionRequest.getConnection().getResponseCode() == HttpURLConnection.HTTP_OK)
//                {
                    String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream());
                    ObjectMapper objectMapper = new ObjectMapper();
                    // photos = objectMapper.readValue(response, Photo.class);
                    photos = objectMapper.readValue(response, ArrayList.class);
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



