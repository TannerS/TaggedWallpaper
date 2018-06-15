package io.tanners.taggedwallpaper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.tanners.taggedwallpaper.db.FavoriteImagesContract.ImageEntry;
import android.provider.BaseColumns;

public class FavoriteImagesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wallpaper.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteImagesDbHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_TABLE_CREATE = "CREATE TABLE " +
                ImageEntry.TABLE_NAME + " (" +
                ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ImageEntry.COLUMN_NAME_ID + " INTEGER NOT NULL," +
                ImageEntry.COLUMN_NAME_IMAGE_WEB + " TEXT NOT NULL" +
                ");";

        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not needed
    }
}


