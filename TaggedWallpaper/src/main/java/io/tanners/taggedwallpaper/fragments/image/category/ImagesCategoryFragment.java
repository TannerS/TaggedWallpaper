package io.tanners.taggedwallpaper.fragments.image.category;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.adapters.image.category.ImageCategoryAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

/**
 * Fragment to hold the Categories layout for image Categories
 */
public class ImagesCategoryFragment extends ImagesFragment {
    private ArrayList<CategoryItem> mCategories;
    public static final String CATEGORY = "Category";
    private final String FIREBASE_ID = "categories";

    /**
     * Create new Instance of class
     * @return
     */
    public static ImagesCategoryFragment newInstance() {
        return new ImagesCategoryFragment();
    }

    @Override
    protected void onScroll() {
        // not needed
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        // load recyclerview
        loadRecyclerView();
        // load categories
        LoadCategories();
        // return view
        return view;
    }

    @Override
    protected void loadViewModelListener(Observer<ArrayList<Photo>> mObserver) {
        // not used due to dynamic changes
    }

    @Override
    protected ImageViewModel getViewModel() {
        // not needed
        return null;
    }

    /**
     * init firebase database connection to load Categories
     *
     * @param listener
     */
    private void initCategoryListener(ValueEventListener listener)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FIREBASE_ID);
        myRef.addValueEventListener(listener);
    }

    /**
     * load Categories into recyclerview
     */
    private void LoadCategories()
    {
        initCategoryListener(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FIREBASE", "Failed to read value.", databaseError.toException());
                // display error
                displayError(databaseError.toException().getMessage());
            }

            // if data changes or is present at start
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                mCategories = new ArrayList<CategoryItem>();
                // get data from firebase
                HashMap<String, String> categoryItemsRaw = (HashMap<String, String>) dataSnapshot.getValue();
                // add new data
                // uses treemap to make it sorted
                if(categoryItemsRaw != null) {
                    TreeMap<String, String> categoryItems = new TreeMap<String, String>(categoryItemsRaw);
                    // reset
                    categoryItemsRaw = null;
                    // lop and add to new data sourcew ot organize by name
                    for (Map.Entry<String, String> entry : categoryItems.entrySet()) {
                        mCategories.add(new CategoryItem(entry.getKey(), entry.getValue()));
                    }
                    // set new data
                    mRecyclerView.setAdapter(new ImageCategoryAdapter(getContext(), mCategories));
                }
            }
        });
    }
}
