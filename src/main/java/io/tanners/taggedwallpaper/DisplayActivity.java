package io.tanners.taggedwallpaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    public final static String ARTIST = "ARTIST";
    public final static String FULLIMAGE = "FULLIMAGE";
    public final static String PREVIEW = "PREVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadToolBar();
        loadBottomNavigation();
    }

    private void loadToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_close);

    }

    private void loadBottomNavigation()
    {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.display_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_download:
                        Log.i("NAV", "DOWNLOAD");
                        return true;
                    case R.id.navigation_set:
                        Log.i("NAV", "SET");
                        return true;
                    case R.id.navigation_share:
                        Log.i("NAV", "SHARE");
                        return true;
                }
                return false;
            }

        });
    }

//
//    /**
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        int id = item.getItemId();
//
//        if (id == R.id.navigation_set) {
//            // Handle the camera action
//        } else if (id == R.id.navigation_download) {
//
//        } else if (id == R.id.navigation_share) {
//
//        }
//
//
//        return true;
//    }

//    /**
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }


}
