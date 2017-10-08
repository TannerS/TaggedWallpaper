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
    private String mAPIKEY = "";
    private final String mEXTRAS = "&image_type=photo&safesearch=true&response_group=high_resolution";
    private String mOrder = "popular";
    private String mTag;
    private String mUrl;

    public ApiBuilder()
    {
       // loadKey();
    }

    public String buildRestfulUrl()
    {
        //loadKey();

        // TAKES INTO ACCOUNT NO QUERY FOR GENERAL IMAGE CATEGORIES SUCH AS POPULAR AND LATEST
        if(mTag == null || mTag.length() == 0)
            return mBase + "?key=" + mAPIKEY + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
        else
            return mBase + "?key=" + mAPIKEY + "&q=" + mTag + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
    }

    public ApiBuilder(String tag, int mPerPage, int mPage, OrderBy order)
    {
        this.mTag = tag;
        this.mPerPage = mPerPage;
        this.mPage = mPage;
        this.mOrder = order.name();
    }

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
//            put("Authorization", APIKEY);
            put("Accept-Language", "en-US,en;q=0.5");
            put("Connection", "keep-alive");
            put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
            put("content-type", "text/html; charset=utf-8");
            put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
            put("Content-Language", "en-US");
//            put("Accept-Version", "v1");
        }};
    }

    // TODO make this load async when app first loads???
//    public void loadKey()
//    {
//        Log.i("FIREBASE", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//        //final AtomicBoolean done = new AtomicBoolean(false);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("keys/pixabay");
//        String value = null;
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                mAPIKEY = dataSnapshot.getValue(String.class);
//                Log.i("FIREBASE", "KEY: " + mAPIKEY);
//               // return mBase + "?key=" + mAPIKEY + "&per_page=" + mPerPage + "&page=" + mPage +  "&order=" + mOrder + mEXTRAS;
//               // done.set(true);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
//       // while (!done.get());
//
//        // since there is only one child
////        myRef.addChildEventListener(new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
////                mAPIKEY = dataSnapshot.getValue(String.class);
////            }
////
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
////
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {}
////
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {}
////        });
//
//    }

}
