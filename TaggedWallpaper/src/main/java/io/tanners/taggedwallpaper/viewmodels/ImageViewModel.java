package io.tanners.taggedwallpaper.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

/**
 * Used if needed for any sub class functionality and to separate logic
 */
public class ImageViewModel extends AndroidViewModel {
    protected MutableLiveData<ArrayList<Photo>> mPhotos;

    public ImageViewModel(@NonNull Application application) {
        super(application);
        mPhotos = new MutableLiveData<ArrayList<Photo>>();
        mPhotos.setValue(new ArrayList<Photo>());
    }

    public void addData(ArrayList<Photo> mPhotos) {
        ArrayList<Photo> temp = this.mPhotos.getValue();
        temp.addAll(mPhotos);
        this.mPhotos.setValue(temp);
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
    }
}
