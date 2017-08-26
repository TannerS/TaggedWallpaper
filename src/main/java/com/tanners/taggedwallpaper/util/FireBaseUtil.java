package com.tanners.taggedwallpaper.util;

import android.content.Context;
import android.util.Log;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.HashMap;
import java.util.Random;

public class FireBaseUtil
{
    private Context context;
    private Firebase fb;
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    private final String TAGS = "tags";
    private HashMap<String, HashMap<String, HashMap<String,String>>> root;
    private StringBuilder str;
    private String tag;
    private String flickr_group;

    public FireBaseUtil(Context context)
    {
        Firebase.setAndroidContext(context);
        fb = new Firebase(FIREBASE_HOME);
    }

    public String searchGroupByTag(String tag_)
    {
        this.tag = tag_;

        fb.child(TAGS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                root = snapshot.getValue(HashMap.class);

                for (String id : root.keySet()) {
                    if (tag.equals(id)) {
                        HashMap<String, HashMap<String, String>> categories = root.get(tag);
                        Random generator = new Random();
                        String[] values = new String[categories.keySet().size()];
                        categories.keySet().toArray(values);
                        String value = values[generator.nextInt(values.length)];
                        flickr_group = String.valueOf((root.get(tag)).get(value));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.i("new", "err" + error.toString());
            }
        });

//        // we need data from the firebase call put it has a delay
//        try {
//            Thread.sleep(850);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return flickr_group;
    }
}