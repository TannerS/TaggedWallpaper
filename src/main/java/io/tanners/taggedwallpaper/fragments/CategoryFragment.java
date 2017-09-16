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
import io.tanners.taggedwallpaper.adapters.CategoryAdapter;
import io.tanners.taggedwallpaper.data.CategoryItem;


public class CategoryFragment extends Fragment {
    private ArrayList<CategoryItem> categories;
    private RecyclerView mCategoryList;
    public static final String CATEGORY_DATABASE_NAME = "categories";
    private View view;
    private static final String CATEGORIES = "Categories";

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
        DatabaseReference myRef = database.getReference(CATEGORY_DATABASE_NAME);
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
                categoryItems.putAll(((HashMap<String, String>) dataSnapshot.getValue()));

                for (Map.Entry<String, String> entry : categoryItems.entrySet()) {
                    categories.add(new CategoryItem(entry.getKey(), entry.getValue()));
                }

                mCategoryList.setAdapter(new CategoryAdapter(getActivity(), categories, R.layout.row_item));

                //        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                //            }
                //        });
            }
        });
    }

}
