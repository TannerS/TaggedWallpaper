package io.tanners.taggedwallpaper.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

/**
 * Used if needed for any sub class functionality and to separate logic
 */
public class ImageViewModel extends AndroidViewModel {

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }
}
