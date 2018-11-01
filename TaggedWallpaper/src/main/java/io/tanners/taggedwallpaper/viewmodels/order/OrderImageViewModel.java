package io.tanners.taggedwallpaper.viewmodels.order;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public class OrderImageViewModel extends ImageViewModel<Photo> {
    private int allImagePageCount;

    public OrderImageViewModel(@NonNull Application application) {
        super(application);
        // default page
        allImagePageCount = 1;
    }

    public int getAllImagePageCount() {
        return allImagePageCount;
    }

    public void incrementImagePage()
    {
        this.allImagePageCount++;
    }
}
