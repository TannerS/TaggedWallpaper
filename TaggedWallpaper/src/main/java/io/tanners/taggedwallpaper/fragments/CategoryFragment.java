package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.CategoryImageAdapter;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;

/**
 * Fragment to hold the categories layout for image categories
 */
public class CategoryFragment extends Fragment {
    private ArrayList<CategoryItem> categories;
    private RecyclerView mCategoryList;
    public static final String CATEGORY = "Category";
    private final String FIREBASE_ID = "categories";
    private View view;
    private ProgressBar mProgressBar;

    /**
     * Create new Instance of class
     * @return
     */
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
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
        view = inflater.inflate(R.layout.fragment_category, container, false);
        loadResources();
        LoadCategories();
        loadCategoryList();
        return view;
    }

    /**
     * load needed objects/views
     */
    private void loadResources()
    {
        categories = new ArrayList<CategoryItem>();
        mProgressBar = view.findViewById(R.id.category_progressbar);

    }

    /**
     * load category items
     */
    private void loadCategoryList()
    {
        mCategoryList = (RecyclerView) view.findViewById(R.id.universal_grideview);
        mCategoryList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

    /**
     * init firebase database connection to load categories
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
     * load categories into recyclerview
     */
    private void LoadCategories()
    {
        initCategoryListener(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }

            // if data changes or is present at start
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // get data from firebase
                HashMap<String, String> categoryItemsRaw = (HashMap<String, String>) dataSnapshot.getValue();
                // check new contents
                if(categoryItemsRaw == null || categoryItemsRaw.size() <= 0)
                {
                    // error
                    loadImageError();
                    mProgressBar.setVisibility(View.GONE);
                    mCategoryList.setVisibility(View.GONE);
                }
                else {
                    // add new data
                    // uses treemap to make it sorted
                    TreeMap<String, String> categoryItems = new TreeMap<String, String>(categoryItemsRaw);
                    for (Map.Entry<String, String> entry : categoryItems.entrySet()) {
                        categories.add(new CategoryItem(entry.getKey(), entry.getValue()));
                    }
                    // set new data
                    mCategoryList.setAdapter(new CategoryImageAdapter(getContext(), categories));
                    // hide progress loading bar
                    mProgressBar.setVisibility(View.GONE);
                    // make gridview visible
                    (view.findViewById(R.id.grid_view_layout)).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * if firebase error occurs, display error
     */
    private void loadImageError()
    {
        // create snackbar message
        final Snackbar mErrorSnackBar = Snackbar.make(view.findViewById(R.id.fragment_category_container_id), "Error loading categories", Snackbar.LENGTH_INDEFINITE);
        mErrorSnackBar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorSnackBar.dismiss();
            }
        });
        // show error message
        mErrorSnackBar.show();
    }
}
