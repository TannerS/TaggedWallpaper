package io.tanners.taggedwallpaper.fragments.image.favorites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import io.tanners.taggedwallpaper.adapters.favorite.ImageFavoriteAdapter;
import io.tanners.taggedwallpaper.db.ImageEntry;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;
import io.tanners.taggedwallpaper.viewmodels.favorite.FavoriteImageViewModel;

public class ImagesFavoriteFragment extends ImagesFragment<ImageEntry> {
    // fragment title
    public static final String FAVORITE = "Favorites";

    /**
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadRecycler();
        loadViewModelListener(getObserver());
    }

    /**
     * @return
     */
    private Observer<List<ImageEntry>> getObserver()
    {
        return new Observer<List<ImageEntry>>() {
            /**
             * Will update adapter on change
             *
             * @param mPhotoEntries
             */
            @Override
            public void onChanged(@Nullable List<ImageEntry> mPhotoEntries) {
                // update adapter
                ((ImageFavoriteAdapter)mAdapter).updateAdapter(new ArrayList<ImageEntry>(mPhotoEntries));
            }
        };
    }

    /**
     * @return
     */
    public static ImagesFavoriteFragment newInstance() {
        return new ImagesFavoriteFragment();
    }

    /**
     * @param mObserver
     */
    @Override
    protected void loadViewModelListener(Observer<List<ImageEntry>> mObserver) {
        getViewModel().getmItems().observe(this, mObserver);
    }

    /**
     *
     */
    @Override
    protected void loadAdapter() {
        mAdapter = new ImageFavoriteAdapter(mContext);
    }

    /**
     * @return
     */
    @Override
    protected FavoriteImageViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FavoriteImageViewModel.class);
    }

    /**
     *
     */
    protected void loadRecycler()
    {
        super.loadRecycler();
        // attach adapter to list
        mRecyclerView.setAdapter(mAdapter);

    }
}
