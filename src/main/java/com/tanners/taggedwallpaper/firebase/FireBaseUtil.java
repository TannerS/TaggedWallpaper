package com.tanners.taggedwallpaper.firebase;

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
    private final String BAD_TAGS = "blocked_words";
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

        Log.i("new", "before fb call");


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
                        Log.i("new", "valu->>: " + flickr_group);
                        Log.i("new", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                        break;
                    }
                }
            }



            @Override
            public void onCancelled(FirebaseError error) {
                Log.i("new", "err" + error.toString());
            }
        });

        // we need data from the firebase call put it has a delay
        try {
            Thread.sleep(850);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("new","after fb call");
        //Log.i("new", "return flickr");
        return flickr_group;
    }

    public String getPhotoGroupIds()
    {
        /*
        fb.child(TAGS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                root = snapshot.getValue(HashMap.class);
              //  HashMap<String, HashMap<String, String>> temp = root.get(BAD_TAGS);
                str = new StringBuilder();
                HashMap<String, HashMap<String, String>> temp;


                Log.i("new", "KeySet: " + root.keySet());
                for(HashMap<String, HashMap<String, String>> categories : root.values())
                {
                    for(HashMap<String, String> groups : categories.values())
                    {

                                           String value = values[generator.nextInt(values.length)];
                        flickr_group = String.valueOf((root.get(tag)).get(value));



                     //   for(String groups_info : groups.values())
                      //  {

                          //  str.append(groups_info);
                          //  Log.i("new", "!!!!!!!!!!!!!!!!!: " + str);
                          //  str.append(",");
                      //  }
                    }
                }
                str.deleteCharAt(str.length()-1);

            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        //let the listner load whith its delays
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    */

        return str.toString();
    }


}
