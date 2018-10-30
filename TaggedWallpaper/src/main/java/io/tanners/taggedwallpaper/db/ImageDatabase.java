package io.tanners.taggedwallpaper.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import io.tanners.taggedwallpaper.db.config.DBConfig;

@Database(entities = {ImageEntry.class}, version = DBConfig.DATABASE_VERSION, exportSchema = false)
//@TypeConverters(TimestampConverter.class)
public abstract class ImageDatabase extends RoomDatabase {
    // used for synchronization
    private static final Object LOCK = new Object();
    // name of database
    private static ImageDatabase mInstance;

    /**
     * Get instance of database
     * @param context
     * @return
     */
    public static ImageDatabase getInstance(Context context) {
        // check if instance already exist
        if (mInstance == null) {
            // used for synchronization of threads by locking this code chunk
            // read more: https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
            synchronized (LOCK) {
                if (mInstance == null) {
                    // create database instance using Room
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ImageDatabase.class, DBConfig.DATABASE_NAME)
                            .build();
                }
            }
        }
        // return current instance
        return mInstance;
    }

    /**
     * Abstract method to return image dao
     *
     * @return ImageDao
     */
    public abstract ImageDao getImageDao();

}