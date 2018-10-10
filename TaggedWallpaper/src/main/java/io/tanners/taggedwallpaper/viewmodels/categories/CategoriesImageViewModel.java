package io.tanners.taggedwallpaper.viewmodels.categories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public class CategoriesImageViewModel extends ImageViewModel {
    private LiveData<List<CategoryItem>> mCategories;

    public CategoriesImageViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CategoryItem>> getmCategories() {
        return mCategories;
    }
}
