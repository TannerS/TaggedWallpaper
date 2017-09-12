//package io.tanners.taggedwallpaper.fragments;
//
//import android.app.ProgressDialog;
//import android.app.SearchManager;
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//import io.tanners.taggedwallpaper.R;
//import io.tanners.taggedwallpaper.mappings.photodata.PhotoContainer;
//import io.tanners.taggedwallpaper.network.images.ImageRequest;
//import io.tanners.taggedwallpaper.adapters.ImageAdapter;
//import io.tanners.taggedwallpaper.mappings.photodata.PhotoItem;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class PhotoSearchFragment extends Fragment
//{
//    public final static String SEARCH = "Search Tags";
//    private Context context;
//    private RecyclerView recycle_view;
//    private View view;
//    private ImageAdapter adapter;
//    private final int per_page = 100;
//    private final int page = 1;
//    private final int gridRows = 2;
//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        this.context = context;
//    }
//
//    public static PhotoSearchFragment newInstance() {
//        return new PhotoSearchFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        // TODO test as false
//        setHasOptionsMenu(true);
//        setUpList();
//    }
//
//    private void setUpList()
//    {
//        GridLayoutManager grid = new GridLayoutManager(context, gridRows);
//        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view_search);
//        recycle_view.setHasFixedSize(true);
//        recycle_view.setLayoutManager(grid);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.search_bar, menu);
//        final MenuItem search_bar = menu.findItem(R.id.menu_search);
////        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        final SearchView search_view = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                final String tag = search_view.getQuery().toString();
//                // TODO async
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        MenuItemCompat.collapseActionView(search_bar);
//                        search_bar.collapseActionView();
//                        search_view.clearFocus();
//                        search_view.setQuery("", false);
//                        search_view.setFocusable(false);
////                        searchByTag(tag, ImageRequest.OPEN_SEARCH);
//                        searchByTag(tag);
//                    }
//                }.run();
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_search, container, false);
//        return view;
//    }
//
//    public void searchByTag(String tag)
//    {
//        if(adapter != null)
//            adapter.clear();
//        new GatherTaggedPhotos().execute(tag);
//    }
//
////    private class GatherTaggedPhotos extends AsyncTask<String, Void, List<PhotoItem>>
//    private class GatherTaggedPhotos extends AsyncTask<String, Void, PhotoContainer>
//    {
//        private ImageRequest imageRequest;
//        private ProgressDialog dialog;
//
//        private GatherTaggedPhotos()
//        {
////            imageRequest = new ImageRequest(context, per_page, page);
//            imageRequest = new ImageRequest(per_page, page);
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
//}
