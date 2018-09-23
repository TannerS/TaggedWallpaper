package io.tanners.taggedwallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Load a splash screen during app load
 * this works by displaying the layout, and while app loads
 * it will end to load main activity
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set new intent
        Intent intent = new Intent(this, MainActivity.class);
        // load new intent
        startActivity(intent);
        // end current splash activity
        finish();
    }
}
