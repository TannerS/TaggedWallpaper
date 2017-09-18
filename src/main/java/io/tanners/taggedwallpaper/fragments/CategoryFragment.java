package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Map;
import java.util.TreeMap;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.RowImageAdapter;


public class CategoryFragment extends Fragment {
    private ArrayList<CategoryItem> categories;
    private RecyclerView mCategoryList;
    public static final String CATEGORY = "Category";
    private final String FIREBASE_ID = "categories";
    private View view;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        loadResources();
        LoadCategories();
        loadCategoryList();


        Log.d("LOAD", "DEBUG 1");


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


    private void loadResources()
    {
        categories = new ArrayList<CategoryItem>();
    }

    private void loadCategoryList()
    {
        mCategoryList = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        mCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
    }

    private void initCategoryListener(ValueEventListener listener)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FIREBASE_ID);
        myRef.addValueEventListener(listener);
    }

    private void LoadCategories()
    {
        initCategoryListener(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                TreeMap<String, String> categoryItems = new TreeMap<String, String>();


                // TODO error handling here if no categories
                HashMap<String, String> categoryItemsRaw = (HashMap<String, String>) dataSnapshot.getValue();

                if(categoryItemsRaw == null)
                {
                    // todo handle error
                    Log.d("LOAD", "ERROR ON FIREBASE");
                }
                else {


                    categoryItems.putAll(categoryItemsRaw);

                    for (Map.Entry<String, String> entry : categoryItems.entrySet()) {
                        categories.add(new CategoryItem(entry.getKey(), entry.getValue()));
                        Log.d("FIREBASE", entry.getKey() + " " + entry.getValue());

                    }

                    mCategoryList.setAdapter(new RowImageAdapter(getActivity(), categories, R.layout.row_item));

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

    // TODO where sohuld this belong?
    public static class CategoryItem
    {
        public String getmUrl() {
            return mUrl;
        }

        public void setmUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public CategoryItem(String mTitle, String mUrl) {
            this.mUrl = mUrl;
            this.mTitle = mTitle;
        }

        private String mUrl;
        private String mTitle;

    }

}
