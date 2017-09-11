package io.tanners.taggedwallpaper.network.images;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.tanners.taggedwallpaper.data.results.photo.Photo;
import io.tanners.taggedwallpaper.network.ConnectionRequest;

public class TagImageRequest extends Request
{
//    private ConnectionRequest mConnectionRequest;

    public TagImageRequest(String mUrl, final String body)
    {
        super(mUrl);

        Log.i("REQUEST", "GET TAGS: " + mUrl);

        // ?utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit
//        mConnectionRequest = new ConnectionRequest(mUrl);

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

    @Override
    public List<Photo> getPhotos()
    {
        List<Photo> photos = null;

        try
        {
            mConnectionRequest.connect();

            if(mConnectionRequest.getConnection() != null)
            {
//                if(mConnectionRequest.getConnection().getResponseCode() == HttpURLConnection.HTTP_OK)
//                {
                    String response = IOUtils.toString(mConnectionRequest.getConnection().getInputStream());

                Log.i("REQUEST", "RESPONSE: " + response);

                ObjectMapper objectMapper = new ObjectMapper();
//                     photos = objectMapper.readValue(response, Photo.class);
                    photos = objectMapper.readValue(response, new TypeReference<List<Photo>>(){});
//                    photos = Arrays.asList(objectMapper.readValue(response, Photo[].class));
//                TypeReference<List<User>> tRef = new TypeReference<List<User>>() {};

                Log.i("REQUEST", "SIZE: " + photos.size());
//                List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))

//                List<MyClass> myObjects = mapper.readValue(jsonInput, new TypeReference<List<MyClass>>(){});

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



