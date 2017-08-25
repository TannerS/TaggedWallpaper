package com.tanners.taggedwallpaper;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tanners.taggedwallpaper.adapters.FragmentAdapter;
import com.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import com.tanners.taggedwallpaper.clarifaidata.ClarifaiFragment;
import com.tanners.taggedwallpaper.flickrdata.FlickrDataPhotosSearch;
import com.tanners.taggedwallpaper.flickrdata.FlickrPhotoSearchFragment;
import com.tanners.taggedwallpaper.flickrdata.FlickrRecentPhotosFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private ArrayAdapter<String> firebase_tag_adapter;
    private ListView nav_bar_list_view;
    private int tag_selector;
    private final int FRAG_COUNT = 3;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TabLayout tab_layout;
    private final String FIREBASE_HOME = "https://smartwallpaper.firebaseio.com/";
    private Firebase fire_base;
    private ArrayList<String> tags;
    private FlickrViewHolder view_holder;
    private ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        setUpDrawer();
        setUpNavBar();
        tag_selector = 0;
        initFireBase();
        getFireBaseTags();
        setUpAdapter();
        setUpTabs();
        setUpFragments();
    }

    private void initFireBase()
    {
        Firebase.setAndroidContext(this);
        fire_base = new Firebase(FIREBASE_HOME);
    }

    private void getFireBaseTags()
    {
        fire_base.child("tags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tags = snapshot.getValue(ArrayList.class);
//                setUpAdapter(tags);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                //TODO add error handling
//                throw new
            }

        });
    }

//    private void setUpAdapter(Set<String> temp)
    private void setUpAdapter()
    {
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.activity_main, null, false);
        firebase_tag_adapter = new GenericTagAdapter(this, R.layout.activity_main, tags);
        nav_bar_list_view.setAdapter(firebase_tag_adapter);
    }

    private void setUpTabs()
    {
        view_pager = (ViewPager) findViewById(R.id.view_pager);

        view_pager.setOffscreenPageLimit(FRAG_COUNT);

        view_pager.setPageTransformer(true, new ZoomOutPageTransformer());


        tab_layout = (TabLayout) findViewById(R.id.tabs);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setUpFragments()
    {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addTab(new FlickrRecentPhotosFragment(), "Recent Tags");
        adapter.addTab(new FlickrPhotoSearchFragment(), "Search Tags");
        adapter.addTab(new ClarifaiFragment(), "Similar Tags");
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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
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

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item)
//    {
//        return false;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
//        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public ViewPager getViewPager()
    {
        if (null == view_pager)
        {
            view_pager = (ViewPager) findViewById(R.id.view_pager);
        }

        return view_pager;
    }

    public List<Fragment> getFragments()
    {
//        List<Fragment> fragments = getSupportFragmentManager().G.getFragments();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//
//        getSupportFragmentManager().registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
//        override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
//            f?.let { fList.add(it) }
//        }
//
//        override fun onFragmentDetached(fm: FragmentManager?, f: Fragment?) {
//            f?.let { fList.remove(it) }
//        }
//
//    }, false)

        if (fragments == null || fragments.isEmpty())
            return Collections.emptyList();
        return fragments;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

//    static private class FlickrViewHolder
//    {
//        Button btn;
//    }


}
