package io.tanners.taggedwallpaper.viewmodels.search;

import android.app.Application;
import android.support.annotation.NonNull;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public class SearchImageViewModel extends ImageViewModel<Photo> {
    private int searchImagePageCount;

    public SearchImageViewModel(@NonNull Application application) {
        super(application);
        // default page
        searchImagePageCount = 1;
    }

    public void incrementImageSearchPage()
    {
        this.searchImagePageCount++;
    }

    public int getSearchImagePageCount() {
        return searchImagePageCount;
    }
}
