package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import io.tanners.taggedwallpaper.adapters.RowImageAdapter;
import io.tanners.taggedwallpaper.data.categories.CategoryItem;


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
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     *
     */
    @Override
    public void onDetach() {
        super.onDetach();
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
        mCategoryList = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        mCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * init firebase database connection to load categories
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
                // uses treemap to make it sorted
                TreeMap<String, String> categoryItems = new TreeMap<String, String>();

                HashMap<String, String> categoryItemsRaw = (HashMap<String, String>) dataSnapshot.getValue();

                if(categoryItemsRaw == null || categoryItemsRaw.size() <= 0)
                {
                    loadImageError();
                    mProgressBar.setVisibility(View.GONE);
                    mCategoryList.setVisibility(View.GONE);
                }
                else {
                    categoryItems.putAll(categoryItemsRaw);

                    for (Map.Entry<String, String> entry : categoryItems.entrySet()) {
                        categories.add(new CategoryItem(entry.getKey(), entry.getValue()));
                    }

                    mCategoryList.setAdapter(new RowImageAdapter(getContext(), categories, R.layout.row_item));
                    mProgressBar.setVisibility(View.GONE);
                    mCategoryList.setVisibility(View.VISIBLE);
                }



                // TODO onclick here

                //        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                //            }
                //        });
            }
        });
    }

    /**
     * if firebase error occurs, dispaly error
     */
    private void loadImageError()
    {
        final Snackbar mErrorSnackBar = Snackbar.make(view.findViewById(R.id.fragment_category_container_id), "Error loading categories", Snackbar.LENGTH_INDEFINITE);

        mErrorSnackBar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorSnackBar.dismiss();
            }
        });

        mErrorSnackBar.show();

    }

//    /**
//     * Hows
//     */
//    public static class CategoryItem
//    {
//        public String getmUrl() {
//            return mUrl;
//        }
//
//        public void setmUrl(String mUrl) {
//            this.mUrl = mUrl;
//        }
//
//        public String getmTitle() {
//            return mTitle;
//        }
//
//        public void setmTitle(String mTitle) {
//            this.mTitle = mTitle;
//        }
//
//        public CategoryItem(String mTitle, String mUrl) {
//            this.mUrl = mUrl;
//            this.mTitle = mTitle;
//        }
//
//        private String mUrl;
//        private String mTitle;
//
//    }

}
