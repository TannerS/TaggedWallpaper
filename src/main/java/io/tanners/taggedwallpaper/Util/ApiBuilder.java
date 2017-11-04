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

    private int mPerPage ;
    private int mPage;
    private String mBase = "https://pixabay.com/api/";
//    private final String mEXTRAS = "&image_type=photo&safesearch=true&response_group=high_resolution";
    private final String mEXTRAS = "&image_type=photo&safesearch=true";
    private String mOrder = "popular";
    private String mTag;

    /**
     * builds restful api
     * @return
     */
    public String buildUrl()
    {
        if(mTag == null || mTag.length() == 0)
            return mBase + "?key=" + new KeyNDK().getApiKey() + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
        else
            return mBase + "?key=" + new KeyNDK().getApiKey() + "&q=" + mTag + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
    }

    public ApiBuilder(String tag, int mPerPage, int mPage, OrderBy order)
    {
        this.mTag = tag;
        this.mPerPage = mPerPage;
        this.mPage = mPage;
        this.mOrder = order.name();
    }

    /**
     * current page of restful api
     * @param mPage
     */
    public void setPage(int mPage)
    {
        this.mPage = mPage;
    }

    /**
     * how many images per page (per rest call)
     * @param mPerPage
     */
    public void setPerPage(int mPerPage)
    {
        this.mPerPage = mPerPage;
    }

    /**
     * Eventually used for pagination
     * able to get next pages of images for download
     */
    public void increasePage()
    {
        this.mPage++;
    }

    /**
     * default headers
     * @return
     */
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
