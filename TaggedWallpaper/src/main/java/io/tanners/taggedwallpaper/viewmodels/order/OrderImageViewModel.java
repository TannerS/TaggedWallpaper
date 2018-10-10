package io.tanners.taggedwallpaper.viewmodels.order;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public class OrderImageViewModel extends ImageViewModel {
    private MutableLiveData<ArrayList<Photo>> mPhotos;
    private int allImagePageCount;

    public OrderImageViewModel(@NonNull Application application) {
        super(application);
        // default page
        allImagePageCount = 1;
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

    public int getAllImagePageCount() {
        return allImagePageCount;
    }

    public void setAllImagePageCount(int allImagePageCount) {
        this.allImagePageCount = allImagePageCount;
    }

    public void incrementImagePage()
    {
        this.allImagePageCount++;
    }
}
