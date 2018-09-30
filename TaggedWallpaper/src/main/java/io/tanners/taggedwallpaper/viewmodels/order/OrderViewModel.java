package io.tanners.taggedwallpaper.viewmodels.order;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import io.tanners.taggedwallpaper.viewmodels.ViewModel;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<Photos> mPhotos;
    private int currentRestCallPage;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        // default page
        currentRestCallPage = 1;
    }

    public MutableLiveData<Photos> getmPhotos() {
        return mPhotos;
    }

    public void setmPhotos(MutableLiveData<Photos> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public Photos getmPhotosData() {
        return mPhotos.getValue();
    }

    public void setmPhotosData(Photos mPhotos) {
        this.mPhotos.setValue(mPhotos);
    }

    public int getCurrentRestCallPage() {
        return currentRestCallPage;
    }

    public void setCurrentRestCallPage(int currentRestCallPage) {
        this.currentRestCallPage = currentRestCallPage;
    }

    public void incrementPage()
    {
        this.currentRestCallPage++;
    }
}
