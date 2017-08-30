package com.tanners.taggedwallpaper;

//import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanners.taggedwallpaper.adapters.FragmentAdapter;
//import com.tanners.taggedwallpaper.adapters.TagAdapter;
import com.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import com.tanners.taggedwallpaper.fragments.PhotoSearchFragment;
import com.tanners.taggedwallpaper.fragments.RecentPhotosFragment;
import com.tanners.taggedwallpaper.fragments.SimilarTagsFragment;
import com.tanners.taggedwallpaper.interfaces.IFindFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IFindFragment
{
    private DatabaseReference ref;
    private ListView nav_bar_list_view;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ViewPager view_pager;
    private List<FragmentAdapter.FragmentInfo> frags;

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
        setUpTabs();
        setUpFragmentAdapters();
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

    private void setUpFragmentAdapters()
    {
        frags = new ArrayList<FragmentAdapter.FragmentInfo>() {{
            add(new FragmentAdapter.FragmentInfo(RecentPhotosFragment.newInstance(), RecentPhotosFragment.RECENT));
            add(new FragmentAdapter.FragmentInfo(PhotoSearchFragment.newInstance(), PhotoSearchFragment.SEARCH));
            add(new FragmentAdapter.FragmentInfo(SimilarTagsFragment.newInstance(), SimilarTagsFragment.SIMILAR));
        }};

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), frags);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public Fragment findFragmentByTitle(String title) {

        int pos = 0;

        for(FragmentAdapter.FragmentInfo fragInfo : this.frags)
        {
            if(fragInfo.getTitle().equals(title))
            {
                drawer.closeDrawer(GravityCompat.START);
                view_pager.setCurrentItem(pos);
            }
            else
                pos++;
        }

        return null;
    }

    private class TagAdapter extends ArrayAdapter<String>
    {
        private Context context;
        private List<String> tags;

        private TagAdapter(Context context, int resource, List<String> tags)
        {
            super(context, resource, tags);
            this.context = context;
            this.tags = tags;
        }

        @Override
        public int getCount()
        {
            return tags != null ? tags.size() : 0;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
        {
            final FlickrViewHolder viewHolder;

            if(convertView == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.flickr_menu_tags_layout, parent, false);
                viewHolder = new FlickrViewHolder();
                viewHolder.btn =  (Button) convertView.findViewById(R.id.flickr_tag_button);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (FlickrViewHolder) convertView.getTag();
            }

            final String tag = this.tags.get(position);

            viewHolder.btn.setText(tag);
//
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    IFindFragment finder = ((IFindFragment) (context));

                    PhotoSearchFragment fragment = (PhotoSearchFragment) finder.findFragmentByTitle(PhotoSearchFragment.SEARCH);

                    if(fragment.getClass().equals(PhotoSearchFragment.class))
                    {
                        view_pager.setCurrentItem(1);
                        drawer.closeDrawer(GravityCompat.START);
                        fragment.searchByTag(tag);
                    }

                    // TODO call search page and search
                    Log.i("TAG", "CLICKED ON " + tag);
                }
            });

            return convertView;
        }

        private class FlickrViewHolder
        {
            Button btn;
        }
    }

}
