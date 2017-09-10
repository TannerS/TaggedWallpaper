package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class SearchFragment extends Fragment {
    private View view;
    public static final String SEARCH = "Search Images";

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
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        Log.i("SEARCH", "DEBUG 1");
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);

        final MenuItem mSearchBarMenuItem = menu.findItem(R.id.photo_search);
        mSearchBarMenuItem.setIcon(R.drawable.ic_action_search_white);
        mSearchBarMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView search_view = null;


//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            Log.i("SEARCH", "DEBUG 2");

//            searchView=(SearchView)menuItem.getActionView();
                search_view = (SearchView) mSearchBarMenuItem.getActionView();
        }
        else
        {
            Log.i("SEARCH", "DEBUG 3");

//            searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
            search_view = (SearchView) MenuItemCompat.getActionView(mSearchBarMenuItem);
        }

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        search_view.setQueryHint("HINT HERE");
        search_view.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        Log.i("SEARCH", "DEBUG 4");





//        int id = search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) search_view.findViewById(id);
//        textView.setHint("Search location...");
//        textView.setHintTextColor(getResources().getColor(R.color.cardview_dark_background));
//        textView.setTextColor(getResources().getColor(R.color.colorAccent));


//        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                final String tag = search_view.getQuery().toString();
//                // TODO async
////                new Runnable() {
////                    @Override
////                    public void run() {
////                        MenuItemCompat.collapseActionView(search_bar);
////                        search_bar.collapseActionView();
////                        search_view.clearFocus();
////                        search_view.setQuery("", false);
////                        search_view.setFocusable(false);
//////                        searchByTag(tag, TagImageRequest.OPEN_SEARCH);
////                        searchByTag(tag);
////                    }
////                }.run();
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    public void searchByTag(String tag)
    {
        Log.i("SEARCH", "QUERY: " + tag);
//        if(adapter != null)
//            adapter.clear();
//        new PhotoSearchFragment.GatherTaggedPhotos().execute(tag);
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
