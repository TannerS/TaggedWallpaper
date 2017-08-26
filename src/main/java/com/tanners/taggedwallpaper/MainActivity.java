package com.tanners.taggedwallpaper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanners.taggedwallpaper.adapters.FragmentAdapter;
import com.tanners.taggedwallpaper.adapters.TagAdapter;
import com.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import com.tanners.taggedwallpaper.fragments.SimilarTagsFragment;
import com.tanners.taggedwallpaper.fragments.PhotoSearchFragment;
import com.tanners.taggedwallpaper.fragments.RecentPhotosFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference ref;
    private ListView nav_bar_list_view;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        setUpDrawer();
        setUpNavBar();
        initFireBase();
        getFireBaseTags();
//        setUpAdapter();
        setUpTabs();
        setUpFragments();
    }

    private void initFireBase()
    {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // TODO either tags or SmartWallpaper
        ref = database.getReference("tags");
    }

    @SuppressWarnings("unchecked")
    private void getFireBaseTags()
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setUpAdapter(dataSnapshot.getValue(ArrayList.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //TODO add error handling
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    private void setUpAdapter(List<String> tags)
    {
        nav_bar_list_view.setAdapter(new TagAdapter(this, R.layout.activity_main, tags));
    }

    private void setUpTabs()
    {
        view_pager = (ViewPager) findViewById(R.id.view_pager);

        int FRAG_COUNT = 3;
        view_pager.setOffscreenPageLimit(FRAG_COUNT);

        view_pager.setPageTransformer(true, new ZoomOutPageTransformer());


        TabLayout tab_layout = (TabLayout) findViewById(R.id.tabs);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setUpFragments()
    {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        adapter.addTab(RecentPhotosFragment.newInstance(), RecentPhotosFragment.RECENT);
        adapter.addTab(PhotoSearchFragment.newInstance(), PhotoSearchFragment.SEARCH);
        adapter.addTab(SimilarTagsFragment.newInstance(), SimilarTagsFragment.SIMILAR);

        view_pager.setAdapter(adapter);
    }

    private void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);
    }

    private void setUpDrawer()
    {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpNavBar()
    {
        nav_bar_list_view = (ListView) findViewById(R.id.nav_bar_adapter);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        if (view_pager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();


            // TODO test
            if (drawer.isDrawerOpen(GravityCompat.START))
                drawer.closeDrawer(GravityCompat.START);
            else
                super.onBackPressed();



        } else {
            // Otherwise, select the previous step.
            view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

//    public List<Fragment> getFragments()
//    {
//
//
//
//
//
////        List<Fragment> fragments = getSupportFragmentManager().G.getFragments();
////        List<Fragment> fragments = getSupportFragmentManager().getFragments();
////
////        getSupportFragmentManager().registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
////        override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
////            f?.let { fList.add(it) }
////        }
////
////        override fun onFragmentDetached(fm: FragmentManager?, f: Fragment?) {
////            f?.let { fList.remove(it) }
////        }
////
////    }, false)
////
////        if (fragments == null || fragments.isEmpty())
////            return Collections.emptyList();
////        return fragments;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}
