package io.tanners.taggedwallpaper.Util;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import io.tanners.taggedwallpaper.KeyNDK;

public class ApiBuilder {

    /**
     * used to determine which proper for which fragments of the same name
     */
    public static enum OrderBy {
        POPULAR("popular"),
        LATEST("latest");

        private String order;

        OrderBy(String order) {
            this.order = order;
        }

        public String order() {
            return order;
        }

    }

    private int mPerPage ;//https://api.unsplash.com/photos?per_page=50&page=1&order_by=newest";
    private int mPage; //https://api.unsplash.com/photos?per_page=50&page=1&order_by=newest";
//    private String mBase = "https://api.unsplash.com/search/photos";
    private String mBase = "https://pixabay.com/api/";
//    private String mApiRules = "&utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit";
//    private final String APIKEY = "Client-ID 53bec55730b75b73e5f615222f83e498e7645300c2b10949e6f8e25442a2fccc";
//    private String mAPIKEY = "";
    private final String mEXTRAS = "&image_type=photo&safesearch=true&response_group=high_resolution";
    private String mOrder = "popular";
    private String mTag;
//    private String mUrl;

    public String buildUrl()
    {
        //loadKey();

        // TAKES INTO ACCOUNT NO QUERY FOR GENERAL IMAGE CATEGORIES SUCH AS POPULAR AND LATEST
        if(mTag == null || mTag.length() == 0)
//            return mBase + "?key=" + mAPIKEY + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
            return mBase + "?key=" + new KeyNDK().getApiKey() + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
        else
//            return mBase + "?key=" + mAPIKEY + "&q=" + mTag + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
            return mBase + "?key=" + new KeyNDK().getApiKey() + "&q=" + mTag + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
    }

    public ApiBuilder(String tag, int mPerPage, int mPage, OrderBy order)
    {
        this.mTag = tag;
        this.mPerPage = mPerPage;
        this.mPage = mPage;
        this.mOrder = order.name();
    }
//
    public void setPage(int mPage)
    {
        this.mPage = mPage;
    }

    public void setPerPage(int mPerPage)
    {
        this.mPerPage = mPerPage;
    }

    /**
     * Eventually used for pagination
     */
    public void increasePage()
    {
        this.mPage++;
    }

    public HashMap<String, String> getHeaders() {
        return new HashMap<String, String>() {{
            put("Accept-Language", "en-US,en;q=0.5");
            put("Connection", "keep-alive");
            put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
            put("content-type", "text/html; charset=utf-8");
            put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
            put("Content-Language", "en-US");
        }};
    }
}
