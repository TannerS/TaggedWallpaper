package io.tanners.taggedwallpaper.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Used if needed for any sub class functionality and to separate logic
 */
public class ImageViewModel<T> extends AndroidViewModel {
    protected LiveData<List<T>> mItems = new MutableLiveData<>();

    public ImageViewModel(@NonNull Application application) {
        super(application);
        ((MutableLiveData<List<T>>) mItems).setValue(new ArrayList<T>());
    }

    public void addData(List<T> mPhotos) {
        ((MutableLiveData<List<T>>) this.mItems).setValue(mPhotos);
    }

    public LiveData<List<T>> getmItems() {
        return mItems;
    }
}
