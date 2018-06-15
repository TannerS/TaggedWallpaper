package io.tanners.taggedwallpaper.db;

import android.provider.BaseColumns;

public class FavoriteImagesContract {

    private FavoriteImagesContract() {};

    public static class ImageEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "wallpaper";
        //        public static final String TABLE_NAME = "images";
        public static final String COLUMN_NAME_ID = "image_id";
//        public static final String COLUMN_NAME_IMAGE_LARGE = "large_image_url";
        public static final String COLUMN_NAME_IMAGE_WEB = "web_format_url";
//        public static final String COLUMN_NAME_IMAGE_HD = "full_hd_url";
//        public static final String COLUMN_NAME_IMAGE_PREVIEW = "preview_url";
//        public static final String COLUMN_NAME_IMAGE = "image_url";
    }
}
//    getWebformatURL()

//
//            "largeImageURL": "https://pixabay.com/get/e831b0082ff61c3e815c4601ef4e4e94fe76e7d711b616449df2c4_1280.jpg",
//                    "webformatHeight": 640,
//                    "webformatWidth": 480,
//                    "likes": 11,
//                    "imageWidth": 2250,
//                    "id": 141772,
//                    "user_id": 41330,
//                    "imageURL": "https://pixabay.com/get/e831b0082ff61c3e815c4601ef4e4e94fe76e7d711b616449df2c4.jpg",
//                    "views": 2608,
//                    "comments": 2,
//                    "pageURL": "https://pixabay.com/en/waterfall-falla-cascades-water-jungle-fo-141772/",
//                    "imageHeight": 3000,
//                    "webformatURL": "https://pixabay.com/get/e831b0082ff61c3e815c4601ef4e4e94fe76e7d711b616449df2c4_640.jpg",
//                    "id_hash": "141772",
//                    "type": "photo",
//                    "previewHeight": 150,
//                    "tags": "waterfall falla cascades water jungle forest rocks tropical laos waterfall waterfall jungle jungle jungle jungle jungle laos",
//                    "downloads": 1040,
//                    "user": "41330",
//                    "favorites": 12,
//                    "imageSize": 2313311,
//                    "previewWidth": 112,
//                    "userImageURL": "",
//                    "fullHDURL": "https://pixabay.com/get/e831b0082ff61c3e815c4601ef4e4e94fe76e7d711b616449df2c4_1920.jpg",
//                    "previewURL": "https://cdn.pixabay.com/photo/2013/06/27/17/20/waterfall-141772_150.jpg"
//                    },