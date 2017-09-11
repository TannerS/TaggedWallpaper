package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.network.ImageRequester;


public class SearchFragment extends Fragment {
    private View view;
    public static final String SEARCH = "Search Images";
    private ImagesAdapter mAdapter;
    private GridView mSearchGridview;
    private String query = "";
//    private final String mUrl = "https://api.unsplash.com/search/photos?query=mountains, nature, landscape&per_page=50&page=1&order_by=popular";
//    private final String mUrl = "https://api.unsplash.com/search/photos?query=" + query + "&per_page=50&page=1&order_by=popular";
    private final String mUrlBase = "https://api.unsplash.com/search/photos?query=";
    private final String mUrlFeatures = "&per_page=50&page=1&order_by=popular";
    private LinearLayout mSearchContainer;



    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        setUpToolBar(view);

        loadResources(view);

        return view;
    }

    private void loadResources(View view)
    {
        mSearchGridview = view.findViewById(R.id.universal_grideview);
        mSearchContainer = view.findViewById(R.id.search_description_container);

    }

    private void setUpToolBar(View view)
    {
        Toolbar mToolbar = view.findViewById(R.id.main_toolbar);
        mToolbar.setTitle("Search Here!");
//        .setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu, menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_search:
//
//                //       onCall();   //your logic
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Log.i("SEARCH", "DEBUG 1");
//        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
//        SearchView searchView = new SearchView(((MainActivity) mContext).getSupportActionBar().getThemedContext());
        final MenuItem mSearchBarMenuItem = menu.findItem(R.id.photo_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView search_view = (SearchView) mSearchBarMenuItem.getActionView();


        mSearchBarMenuItem.setIcon(R.drawable.ic_action_search_white);
        mSearchBarMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
        mSearchBarMenuItem.setTitle("TITLE");


        ComponentName cn = new ComponentName(getContext(), MainActivity.class);
        search_view.setSearchableInfo(searchManager.getSearchableInfo(cn));

//        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        search_view.setQueryHint("HINT HERE");
        search_view.setIconifiedByDefault(true);
        search_view.setQueryHint("Type something...");
//        search_view.setQuery("", false);
//        int searchTextId = search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);

//        TextView searchText = (TextView) search_view.findViewById(searchTextId);

//        if (searchText!=null) {
//            searchText.setTextColor(Color.WHITE);
//            searchText.setHintTextColor(Color.WHITE);
//        }

//        my_search_view.setIconified(false);
//        my_search_view.setIconifiedByDefault(false);



//        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

//        int id = search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) search_view.findViewById(id);
//        textView.setHint("Search location...");
//        textView.setHintTextColor(getResources().getColor(R.color.cardview_dark_background));
//        textView.setTextColor(getResources().getColor(R.color.colorAccent));

        final SearchView finalSearch_view = search_view;
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                final String tag = finalSearch_view.getQuery().toString();
                new Runnable() {
                    @Override
                    public void run() {
//                        MenuItemCompat.collapseActionView(search_bar);
//                        search_bar.collapseActionView();
//                        search_view.clearFocus();
//                        search_view.setQuery("", false);
//                        search_view.setFocusable(false);
//                        searchByTag(tag, TagImageRequest.OPEN_SEARCH);
                        searchByTag(tag);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return true;
    }

    public void searchByTag(String tag)
    {
        Log.i("SEARCH", "QUERY: " + tag);

        String mNewUrl = mUrlBase + tag + mUrlFeatures;

        Log.i("SEARCH", "QUERY: " + mNewUrl);

        mSearchContainer.setVisibility(View.GONE);
        mSearchGridview.setVisibility(View.VISIBLE);

        if(mAdapter != null)
            mAdapter.clearAdapter();

        new ImageRequester(getContext(), mAdapter, mSearchGridview, R.layout.grid_item, R.id.grid_image_background).execute(mNewUrl);
    }
//
//
//
//
//
//
//
//
//
//    private class GatherTaggedPhotos extends AsyncTask<String, Void, PhotoContainer>
//    {
//        private TagImageRequest imageRequest;
//        private ProgressDialog dialog;
//
//        private GatherTaggedPhotos()
//        {
////            imageRequest = new TagImageRequest(context, per_page, page);
//            imageRequest = new TagImageRequest(per_page, page);
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            dialog = new ProgressDialog(getActivity());
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.MainBGColor)));
//            dialog.setTitle("Gathering photos");
//            dialog.setMessage("Please wait, this depends on your internet connection");
//            dialog.setCancelable(false);
//            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
//            dialog.show();
//        }
//
//        @Override
////        protected List<PhotoItem> doInBackground(String... str)
//        protected PhotoContainer doInBackground(String... str)
//        {
//            return imageRequest.searchForImages(str[0]);
//        }
//
//        @Override
//        protected void onPostExecute(PhotoContainer result)
//        {
//            super.onPostExecute(result);
//
//            ArrayList<PhotoItem> images = (ArrayList<PhotoItem>) result.getPhotos().getPhoto();
//
//            // TODO NSFW check here
//
//
//            if (images == null || (images.size() <= 0))
//            {
//                NoImagesToast("Unable to get images");
//            }
//            else
//            {
//                Collections.shuffle(images);
//                adapter = new ImageAdapter(context, images);
//                recycle_view.setAdapter(adapter);
//            }
//
//            dialog.dismiss();
//        }
//
//        private void NoImagesToast(String str)
//        {
//            Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
//            toast.show();
//        }
//    }
}
