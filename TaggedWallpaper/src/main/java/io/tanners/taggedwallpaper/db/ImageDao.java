package io.tanners.taggedwallpaper.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import io.tanners.taggedwallpaper.db.config.DBConfig;
import static io.tanners.taggedwallpaper.db.config.DBConfig.GET_ALL_IMAGES_QUERY;

@Dao
public interface ImageDao {
    /**
     * Load all favorite images in db
     *
     * @return
     */
    @Query(GET_ALL_IMAGES_QUERY)
    LiveData<List<ImageEntry>> loadAllPhotoEntries();

    /**
     * Insert favorite image
     *
     * @param mPhotoEntry
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhotoEntry(ImageEntry mPhotoEntry);

    /**
     * Update image object in db by replacing it
     *
     * @param mPhotoEntry
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePhotoEntry(ImageEntry mPhotoEntry);

    /**
     * Delete a favorite image
     *
     * @param mPhotoEntry
     */
    @Delete
    void deletePhotoEntry(ImageEntry mPhotoEntry);

    /**
     * Get favorite image by id
     *
     * @param id
     * @return
     */
    @Query(DBConfig.GET_IMAGE_BY_ID_QUERY)
    ImageEntry loadPhotoEntryById(String id);
}



