package io.tanners.taggedwallpaper.viewmodels.categories;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;
import io.tanners.taggedwallpaper.viewmodels.ViewModel;

public class CategoriesViewModel extends ViewModel {
    private LiveData<List<CategoryItem>> mCategories;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CategoryItem>> getmCategories() {
        return mCategories;
    }
}
