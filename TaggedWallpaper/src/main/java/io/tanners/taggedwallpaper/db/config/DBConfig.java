package io.tanners.taggedwallpaper.db.config;

public class DBConfig {
    // table now
    public final static String TABLE_NAME = "wallpaper";
    // used for query order_by field column
    public final static String ORDER_BY_FIELD = "timestamp";
    // query for getting all movies
    public final static String GET_ALL_IMAGES_QUERY = "SELECT * FROM" + " " + TABLE_NAME + " " + "ORDER BY" + " " + ORDER_BY_FIELD;
    // query for getting a single movie
    public final static String GET_IMAGE_BY_ID_QUERY = "SELECT * FROM" + " " + TABLE_NAME + " " + "WHERE id" + " = " + ":id";
    // name of database
    public static final String DATABASE_NAME = "tagged_wallpaper";
    // current version of db
    public static final int DATABASE_VERSION = 1;
}
