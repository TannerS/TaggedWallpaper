package io.tanners.taggedwallpaper.viewmodels.order;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.ArrayList;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.viewmodels.ViewModel;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Photo>> mPhotos;
    private int allImagePageCount;
    private int searchImagePageCount;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        // default page
        allImagePageCount = 1;
        searchImagePageCount = 1;
        mPhotos = new MutableLiveData<ArrayList<Photo>>();
        mPhotos.setValue(new ArrayList<Photo>());
    }

    public void addData(ArrayList<Photo> mPhotos) {

            for(int i = 0; i < mPhotos.size(); i++)
                Log.i("ADAPTER", "DATA VIEW MODE: " + ((Photo)mPhotos.get(i)).getId());




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

        Log.i("VIEWMODEL", "ADDING MORE DATAL: " + mPhotos.size());

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

    public void incrementImageSearchPage()
    {
        this.searchImagePageCount++;
    }

    public int getSearchImagePageCount() {
        return searchImagePageCount;
    }

    public void setSearchImagePageCount(int searchImagePageCount) {
        this.searchImagePageCount = searchImagePageCount;
    }
}
