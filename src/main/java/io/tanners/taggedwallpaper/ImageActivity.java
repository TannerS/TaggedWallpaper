package io.tanners.taggedwallpaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import java.util.ArrayList;

import io.tanners.taggedwallpaper.adapters.FragmentAdapter;
import io.tanners.taggedwallpaper.fragments.LatestImagesFragment;
import io.tanners.taggedwallpaper.fragments.PopularImagesFragment;
import io.tanners.taggedwallpaper.interfaces.IGetTag;

// https://developer.android.com/training/implementing-navigation/ancestral.html
public class ImageActivity extends TabbedActivity implements IGetTag {
    public final static String TAG = "TAG";
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        tag = getIntent().getStringExtra(TAG);



//        mFragAmount = 2;
        setUpToolBar(R.id.image_toolbar);
        // set up fragment tabs
        setUpTabs(R.id.image_view_pager, R.id.image_tab_layout, 2);
        setUpFragmentAdapters(new ArrayList<FragmentAdapter.FragmentInfo>() {{
            add(new FragmentAdapter.FragmentInfo(LatestImagesFragment.newInstance(), LatestImagesFragment.NEWEST));
            add(new FragmentAdapter.FragmentInfo(PopularImagesFragment.newInstance(), PopularImagesFragment.POPULAR));
        }});

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();

        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // https://developer.android.com/training/implementing-navigation/ancestral.html
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);

//                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                    // This activity is NOT part of this app's task, so create a new task
//                    // when navigating up, with a synthesized back stack.
//                    TaskStackBuilder.create(this)
//                            // Add all of this activity's parents to the back stack
//                            .addNextIntentWithParentStack(upIntent)
//                            // Navigate up to the closest parent
//                            .startActivities();
//                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
//                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Provides a way to provide the same functionality to pass and connect to this intent
     * @param context
     * @param query
     */
    public static void openIntentForQuery(Context context, String query)
    {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(ImageActivity.TAG, query);
        context.startActivity(intent);
    }

    @Override
    public String getTag() {
        return this.tag;
    }
}
