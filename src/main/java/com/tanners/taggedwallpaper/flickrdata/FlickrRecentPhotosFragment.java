package com.tanners.taggedwallpaper.flickrdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.flickrdata.photodata.FlickrPhotoContainer;
import com.tanners.taggedwallpaper.flickrdata.photodata.FlickrPhotoItem;
import java.util.List;

public class FlickrRecentPhotosFragment extends Fragment
{
    private Context context;
    private View view;
    private GridLayoutManager grid;
    private RecyclerView recycle_view;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        new CollectRecentPhotos().execute();
        grid = new GridLayoutManager(context, 2);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.flickr_fragment_recent, null, false);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return view;
    }

    public class CollectRecentPhotos extends AsyncTask<Void, Void, FlickrPhotoContainer>
    {
        private FlickrDataPhotosRecent photos;
        private ProgressDialog dialog;

        public CollectRecentPhotos()
        {
            photos = new FlickrDataPhotosRecent();
        }

        @Override
        protected FlickrPhotoContainer doInBackground(Void... v)
        {
            return photos.populateFlickrPhotos();
        }

        @Override
        protected void onPreExecute()
        {
            dialog = new ProgressDialog(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.MainBGColor)));
            dialog.setTitle("Gathering photos");
            dialog.setMessage("Please wait, this depends on your internet connection");
            dialog.setCancelable(false);
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
       }

        @Override
        protected void onPostExecute(FlickrPhotoContainer result)
        {
            super.onPostExecute(result);
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.flickr_grid_image_layout, null, false);

            if(result != null)
            {
                List<FlickrPhotoItem> flickr_objects = result.getPhotos().getPhoto();

                if (flickr_objects == null || (flickr_objects.size() == 0))
                {
                    NoImagesToast("Unable to get images");
                }
                else
                {
                    final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    FlickrRecycleImageAdapter adapter = new FlickrRecycleImageAdapter(context, flickr_objects, metrics);
                    recycle_view.setAdapter(adapter);
                }
            }
            else
                NoImagesToast("Unable to get images");

            dialog.dismiss();
        }

        private void NoImagesToast(String str)
        {
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
