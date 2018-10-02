package io.tanners.taggedwallpaper.viewmodels.order.popular;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.viewmodels.ViewModel;
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;

//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;

public class PopularOrderViewModel extends OrderViewModel {
    public PopularOrderViewModel(@NonNull Application application) {
        super(application);
    }
}
