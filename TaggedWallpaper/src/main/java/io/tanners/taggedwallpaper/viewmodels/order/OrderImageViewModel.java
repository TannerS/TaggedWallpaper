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

// TODO find way to add data without or with live data
    public void addData(List<Photo> mPhotos) {
        List<Photo> temp = this.mItems.getValue();
        temp.addAll(mPhotos);
        ((MutableLiveData)this.mItems).setValue(temp);
    }

    public int getAllImagePageCount() {
        return allImagePageCount;
    }

    public void incrementImagePage()
    {
        this.allImagePageCount++;
    }
}
