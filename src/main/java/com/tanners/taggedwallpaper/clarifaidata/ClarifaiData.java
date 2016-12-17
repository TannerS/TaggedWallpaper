package com.tanners.taggedwallpaper.clarifaidata;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.clarifai.api.exception.ClarifaiException;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tanners.taggedwallpaper.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClarifaiData extends ClarifaiClient
{
    private final static String APP_ID = "Xijy3WCA1l4UwEb_zLDx10Kixh068oitW7PcHMv8";
    private final static String APP_SECRET = "U-e8tQDPSQxKl2MTvoQW1W1MbOE59oepZr8j92Gf";
    private final String ERROR_REC_IMAGE = "Unable to recognize image";
    private final String ERROR_LOAD_IMAGE = "Unable to load image";
    private final int OK_CODE = 1;
    private Context context;
    private Firebase fire_base;
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    private HashMap<String, String> questionable_tags;
    private HashMap<String, String> bad_tags;
    private ArrayList<String> approved_tags;
    private final String FIREBASE_BAD_TAGS = "blocked_words";

    public ClarifaiData(Context context)
    {
        super(APP_ID, APP_SECRET);
        this.context = context;
        initFireBase();
    }

    public int getOKCode()
    {
        return OK_CODE;
    }

    public boolean addTags(RecognitionResult result)
    {
        initFireBase();
        approved_tags = new ArrayList<String>();

        if (result != null)
        {
            if (result.getStatusCode() == RecognitionResult.StatusCode.OK)
            {
                for(Tag t : result.getTags())
                {
                    if(filerTags(t.getName()))
                    {
                        approved_tags.add(t.getName());
                    }
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public RecognitionResult recognizeBitmap(Uri uri)
    {
        try
        {
            Bitmap image = null;

            try
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.clarifai_fragment_main, null, false);
                ImageView image_view = (ImageView) view.findViewById(R.id.image_view);
                image = Picasso.with(this.context).load(uri).get();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 70, out);

            return recognize(new RecognitionRequest(out.toByteArray())).get(0);
        }
        catch (ClarifaiException e)
        {
            return null;
        }
    }

    public String getRecError()
    {
        return ERROR_REC_IMAGE;
    }

    public String getLoadError()
    {
        return ERROR_LOAD_IMAGE;
    }

    private void initFireBase()
    {
        Firebase.setAndroidContext(context);
        fire_base = new Firebase(FIREBASE_HOME);

        fire_base.child("blocked_words").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bad_tags = snapshot.getValue(HashMap.class);
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });
    }

    synchronized private boolean filerTags(String tag)
    {
        for(String bad_tag : bad_tags.values())
        {
            if(tag.equals(bad_tag))
            {
                return false;
            }
        }

        return true;
    }

    public List<String> getTags()
    {
        return approved_tags;
    }
}
