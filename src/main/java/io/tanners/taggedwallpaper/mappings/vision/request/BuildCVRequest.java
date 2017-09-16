package io.tanners.taggedwallpaper.mappings.vision.request;

import com.google.gson.Gson;

import io.tanners.taggedwallpaper.mappings.vision.request.base.Feature;
import io.tanners.taggedwallpaper.mappings.vision.request.base.Request;
import io.tanners.taggedwallpaper.mappings.vision.request.base.Requests;
import io.tanners.taggedwallpaper.mappings.vision.request.base.Type;
import io.tanners.taggedwallpaper.mappings.vision.request.base.v1.Image;
import io.tanners.taggedwallpaper.mappings.vision.request.base.v1.Source;
//import io.tanners.taggedwallpaper.mappings.vision.request.base.v1.Source;

import java.util.ArrayList;

public class BuildCVRequest {

    public String buildRequestBodyWithImage(final String imageUrl)
    {
        Requests requests = new Requests(new ArrayList<Request>() {{
            add(new Request(new io.tanners.taggedwallpaper.mappings.vision.request.base.v1.Image(new Source(imageUrl)), new ArrayList<Feature>(){{
                add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
                add(new Feature(Type.LABEL_DETECTION.value(), 5));
            }}));
        }});


        Gson gson = new Gson();
        String json = gson.toJson(requests);
        System.out.println(json);
        return json;

    }

    public String buildRequestBodyWithUrl(final String imageBase64)
    {
        Requests requests = new Requests(new ArrayList<Request>() {{
            add(new Request(new io.tanners.taggedwallpaper.mappings.vision.request.base.v2.Image(imageBase64), new ArrayList<Feature>(){{
                add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
                add(new Feature(Type.LABEL_DETECTION.value(), 5));
            }}));
        }});



        Gson gson = new Gson();
        String json = gson.toJson(requests);
        System.out.println(json);
        return json;

    }

    public String buildRequestBodyWithUrl(final ArrayList<String> images)
    {
//        Requests requests = new Requests(new ArrayList<Request>() {{
//            add(new Request(new BaseImage(new Source(imageUrl)), new ArrayList<Feature>(){{
//                add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
//                add(new Feature(Type.LABEL_DETECTION.value(), 5));
//            }}));
//        }});



//        Gson gson = new Gson();
//        String json = gson.toJson(requests);
//        System.out.println(json);
//        return json;

        return null;

    }
}
