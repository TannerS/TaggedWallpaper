package com.tanners.taggedwallpaper.vision.request;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BuildCVRequest {
//    private String baseUrl = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDDdQ1GQ_88s1Y5Rojcv5xBxQQ_pseGxEQ";
    public String buildRequestBody(final String imageUrl)
    {
//        ArrayList<Feature> features = new ArrayList<Feature>();
//        ArrayList<Feature> features = new ArrayList<Feature>(){{
//            add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
//            add(new Feature(Type.LABEL_DETECTION.value(), 5));
//        }};
//        features.add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
//        features.add(new Feature(Type.LABEL_DETECTION.value(), 5));
//        ArrayList<Request> request = new ArrayList<Request>() {{
//            add(new Request(new Image(new Source(imageUrl)), features));
//            add(new Request(new Image(new Source(imageUrl)), new ArrayList<Feature>(){{
//                add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
//                add(new Feature(Type.LABEL_DETECTION.value(), 5));
//            }}));
//        }};


//        request.add(new Request(new Image(new Source(imageUrl)), features));
//        Requests requests = new Requests(request);





        Requests requests = new Requests(new ArrayList<Request>() {{
            add(new Request(new Image(new Source(imageUrl)), new ArrayList<Feature>(){{
                add(new Feature(Type.SAFE_SEARCH_DETECTION.value(), 25));
                add(new Feature(Type.LABEL_DETECTION.value(), 5));
            }}));
        }});









        Gson gson = new Gson();
        String json = gson.toJson(requests);
        System.out.println(json);
        return json;

    }
}
