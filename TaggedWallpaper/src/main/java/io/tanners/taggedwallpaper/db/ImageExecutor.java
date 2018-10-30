package io.tanners.taggedwallpaper.db;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Used as a singleton device to work on a thread for db functionality
 */
public class ImageExecutor {
    // lock to help prevent race conditions
    private static final Object LOCK = new Object();
    // self static object
    private static ImageExecutor mImageExecutor;
    // executor for disk
    private final Executor mDiskIO;

    /**
     * Constructor
     *
     * @param diskIO
     */
    private ImageExecutor(Executor diskIO) {
        this.mDiskIO = diskIO;
    }

    /**
     * Get new or existing instance of ImageExecutor
     * @return
     */
    public static ImageExecutor getInstance() {
        if (mImageExecutor == null) {
            // used to prevent multiple threads from race conditions
            synchronized (LOCK) {
                // some threads may have been waiting behind the lock,
                // we don't wish on creating more then one
                if (mImageExecutor == null) {
                    mImageExecutor = new ImageExecutor(
                            // get single thread executor
                            Executors.newSingleThreadExecutor()
                    );
                }
            }
        }
        // return instance
        return mImageExecutor;
    }

    /**
     * Get disk executor
     *
     * @return
     */
    public Executor mDiskIO() {
        return mDiskIO;
    }
}