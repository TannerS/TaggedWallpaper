package io.tanners.taggedwallpaper.support.providers;

import android.content.SearchRecentSuggestionsProvider;

/**
 * https://developer.android.com/guide/topics/search/adding-recent-query-suggestions
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "io.tanners.taggedwallpaper.Util.providers.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
