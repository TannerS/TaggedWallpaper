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
import com.squareup.picasso.Picasso;
import com.tanners.taggedwallpaper.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRec extends ClarifaiClient
{
    private final static String APP_ID = "Xijy3WCA1l4UwEb_zLDx10Kixh068oitW7PcHMv8";
    private final static String APP_SECRET = "U-e8tQDPSQxKl2MTvoQW1W1MbOE59oepZr8j92Gf";
    private Context context;
    private ArrayList<String> approved_tags;

    public ImageRec(Context context)
    {
        super(APP_ID, APP_SECRET);
        this.context = context;
    }

    public int getOKCode()
    {
        int OK_CODE = 1;
        return OK_CODE;
    }

    public boolean addTags(RecognitionResult result)
    {
        approved_tags = new ArrayList<String>();

        if (result != null)
        {
            if (result.getStatusCode() == RecognitionResult.StatusCode.OK)
            {
                for(Tag t : result.getTags())
                {
                    // TODO google vision API used here?? or NLP to filter out bad words??
                    approved_tags.add(t.getName());
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

    // TODO computer vision api here
    public RecognitionResult recognizeBitmap(Uri uri)
    {
        try
        {
            Bitmap image = null;

            try
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.fragment_similar, null, false);
                ImageView image_view = (ImageView) view.findViewById(R.id.image_view);
                image = Picasso.with(this.context).load(uri).get();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            // TODO if u use glide it may auto shrink, not sure about picasso
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
        return "Unable to recognize image";
    }

    public String getLoadError()
    {
        return "Unable to load image";
    }

    public List<String> getTags()
    {
        return approved_tags;
    }
}
