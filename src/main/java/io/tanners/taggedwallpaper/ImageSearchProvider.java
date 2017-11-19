package io.tanners.taggedwallpaper;

import android.content.SearchRecentSuggestionsProvider;

// https://developer.android.com/guide/topics/search/adding-recent-query-suggestions.html
public class ImageSearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "io.tanners.ImageSearchProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public ImageSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}

