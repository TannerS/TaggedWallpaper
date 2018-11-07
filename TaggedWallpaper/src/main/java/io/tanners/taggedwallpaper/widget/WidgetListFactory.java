package io.tanners.taggedwallpaper.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
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
import io.tanners.taggedwallpaper.SearchActivity;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;

public class WidgetListFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<CategoryItem> mCategories;
    private final String FIREBASE_ID = "categories";
    private int mWidgetId;
    private Context mContext;

    /**
     * @param applicationContext
     */
    public WidgetListFactory(Context applicationContext, Intent mIntent) {
        mContext = applicationContext;
        // source: https://stackoverflow.com/questions/11350287/ongetviewfactory-only-called-once-for-multiple-widgets
        mWidgetId = Integer.valueOf(mIntent.getData().getSchemeSpecificPart());
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
            public void onCancelled(DatabaseError databaseError) { }

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
                }

                AppWidgetManager.getInstance(mContext).notifyAppWidgetViewDataChanged(
                        mWidgetId,
                        R.id.widget_gridview
                );
            }
        });
    }

    /**
     *
     */
    @Override
    public void onCreate() {
        LoadCategories();
    }

    /**
     * called on start and when notifyAppWidgetViewDataChanged is called
     */
    @Override
    public void onDataSetChanged() { }

    /**
     *
     */
    @Override
    public void onDestroy() { }

    /**
     * Get item count
     *
     * @return
     */
    @Override
    public int getCount() {
        return mCategories == null ? 0 : mCategories.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided position
     */
    @Override
    public RemoteViews getViewAt(int position) {
        // load views
        RemoteViews mSubRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.category_widget_item);
        // get item title
        String mTitle = mCategories.get(position).getmTitle();
        // set category text
        mSubRemoteViews.setTextViewText(R.id.category_widget_item, mCategories.get(position).getmTitle());
        // Fill in the onClick PendingIntent Template using the specific category for each item individually
        Bundle mInputExtras = new Bundle();
        mInputExtras.putString(SearchActivity.SEARCH_QUERY_INTENT_EXTRA_KEY, mTitle);
        Intent mFillInIntent = new Intent();
        mFillInIntent.putExtras(mInputExtras);
        mSubRemoteViews.setOnClickFillInIntent(R.id.category_widget_item, mFillInIntent);
        // return text
        return mSubRemoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    /**
     * 1 to reat all items the same
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }
}

