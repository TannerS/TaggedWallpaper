package io.tanners.taggedwallpaper.viewmodels.order;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.viewmodels.ViewModel;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Photo>> mPhotos;
    private int currentRestCallPage;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        // default page
        currentRestCallPage = 1;
        mPhotos = new MutableLiveData<ArrayList<Photo>>();
    }

    public MutableLiveData<ArrayList<Photo>> getmPhotos() {
        return mPhotos;
    }

    public void setmPhotos(MutableLiveData<ArrayList<Photo>> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public ArrayList<Photo> getmPhotosValue() {
        return mPhotos.getValue();
    }

    public void setmPhotosValue(ArrayList<Photo> mPhotos) {
        this.mPhotos.setValue(mPhotos);

        Log.d("DATA_LOAD", "AFTER LOAD TO VIEW MODEL SIZE: " + String.valueOf(mPhotos.size()));


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

        Log.d("COUNTER", String.valueOf(this.currentRestCallPage));
    }
}
