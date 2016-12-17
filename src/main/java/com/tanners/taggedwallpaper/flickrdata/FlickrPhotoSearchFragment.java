package com.tanners.taggedwallpaper.flickrdata;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.flickrdata.photodata.FlickrPhotoItem;
import java.util.Collections;
import java.util.List;

public class FlickrPhotoSearchFragment extends Fragment
{
    private Context context;
    private GridLayoutManager grid;
    private RecyclerView recycle_view;
    private View view;
    private FlickrRecycleImageAdapter adapter;
    private int per_page;
    private int page;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity().getApplicationContext();
        per_page = 1000;
        page = 1;
        grid = new GridLayoutManager(context, 2);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.flickr_fragment_search, null, false);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view_search);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
        final MenuItem search_bar = menu.findItem(R.id.menu_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        final SearchView search_view = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // would only work if on a seperate thread
            @Override
            public boolean onQueryTextSubmit(String query) {
                final String tag = search_view.getQuery().toString();
                new Runnable() {
                    @Override
                    public void run() {
                        MenuItemCompat.collapseActionView(search_bar);
                        search_bar.collapseActionView();
                        search_view.clearFocus();
                        search_view.setQuery("", false);
                        search_view.setFocusable(false);
                        searchByTag(tag, FlickrDataPhotosSearch.OPEN_SEARCH);
                    }
                }.run();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return view;
    }

    public void searchByTag(String tag, int selection)
    {
        if(adapter != null)
            adapter.clear();
        new CollectTaggedPhotos(recycle_view, context, selection).execute(tag);
    }

    private class CollectTaggedPhotos extends AsyncTask<String, Void, List<FlickrPhotoItem>>
    {
        private FlickrDataPhotosSearch flickr_object;
        private RecyclerView recycler_view;
        private Context context;
        private ProgressDialog dialog;
        private int selection;

        public CollectTaggedPhotos(RecyclerView recycler_view, Context context, int selection)
        {
            flickr_object = new FlickrDataPhotosSearch(context, per_page, page);
            this.recycler_view = recycler_view;
            this.context = context;
            this.selection = selection;
        }

        @Override
        protected void onPreExecute()
        {
            dialog = new ProgressDialog(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.MainBGColor)));
            dialog.setTitle("Gathering photos");
            dialog.setMessage("Please wait, this depends on your internet connection");
            dialog.setCancelable(false);
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        }

        @Override
        protected List<FlickrPhotoItem> doInBackground(String... str)
        {
            return flickr_object.searchFlickr(str[0], selection);
        }

        @Override
        protected void onPostExecute(List<FlickrPhotoItem> result)
        {
            super.onPostExecute(result);
            Collections.shuffle(result);

            if (result == null || (result.size() <= 0))
            {
                NoImagesToast("Unable to get images");
            }
            else
            {
                final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                adapter = new FlickrRecycleImageAdapter(context, result, metrics);
                recycler_view.setAdapter(adapter);
            }

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
