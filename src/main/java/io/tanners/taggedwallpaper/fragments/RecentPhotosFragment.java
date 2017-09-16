package io.tanners.taggedwallpaper.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
//import com.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.ImageAdapter;
//import com.tanners.taggedwallpaper.async.CollectPhotos;
import io.tanners.taggedwallpaper.mappings.photodata.PhotoContainer;
import io.tanners.taggedwallpaper.mappings.photodata.PhotoItem;
import io.tanners.taggedwallpaper.network.vision.ConnectionRequest;
import io.tanners.taggedwallpaper.util.EndpointRestBuilder;

import java.io.IOException;
import java.util.List;

public class RecentPhotosFragment extends Fragment {
    public final static String RECENT = "Recent Tags";
    private final int PERPAGE = 200;
    private final int PAGES = 1;

    private Context context;
    private View view;
    private RecyclerView recycle_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GridLayoutManager grid = new GridLayoutManager(context, 2);

        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);

        new CollectPhotos().execute();
    }

    public static RecentPhotosFragment newInstance() {
        return new RecentPhotosFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent, container, false);
        return view;
    }

    private class CollectPhotos extends AsyncTask<Void, Void, PhotoContainer> {
        //        private FlickrDataPhotosRecent photos;
        private ProgressDialog dialog;
        //        private EndpointRestBuilder builder;
        private PhotoContainer container;
        //        private ConnectionRequest connection;
//        private RecyclerView recycle_view;
//        private Context context;
        //
//        public CollectPhotos(Context context, RecyclerView recycle_view)
//        {
//            this.recycle_view = recycle_view;
//            this.container = new PhotoContainer();
//            this.context = context;
//        }

        @Override
        protected void onPreExecute() {
            initDialog();
//            builder = new EndpointRestBuilder();
        }

        @Override
        protected PhotoContainer doInBackground(Void... v) {
//            PhotoContainer container = null;

            try {
                // TODO switch statment
                ConnectionRequest connection = new ConnectionRequest((new EndpointRestBuilder()).getRecentPhotos(PERPAGE, PAGES));
                String responseStr = connection.getResponse();
                Gson gson = new Gson();
                container = gson.fromJson(responseStr, PhotoContainer.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return container;
        }

        @Override
        protected void onPostExecute(PhotoContainer result) {
            super.onPostExecute(result);

//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = layoutInflater.inflate(R.layout.flickr_grid_image_layout, null, false);

            if (result != null) {
                List<PhotoItem> photoItems = result.getPhotos().getPhoto();

                // TODO nsfw check

                if (photoItems == null || (photoItems.size() == 0)) {
                    displayToast("Unable to get images");
                } else {
//                    final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//                    ImageAdapter adapter = new ImageAdapter(context, photoItems);
                    ImageAdapter adapter = new ImageAdapter(context, photoItems);
//                    adapter = new ImageAdapter(context, photoItems, );
                    recycle_view.setAdapter(adapter);
                }
            } else
                displayToast("Unable to get images");

            dialog.dismiss();
        }


        private void initDialog() {
            dialog = new ProgressDialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.MainBGColor)));
            dialog.setTitle("Gathering photos");
            dialog.setMessage("Please wait, this depends on your internet connection");
            dialog.setCancelable(false);
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        }

        private void displayToast(String str) {
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
            toast.show();
        }


    }
}
