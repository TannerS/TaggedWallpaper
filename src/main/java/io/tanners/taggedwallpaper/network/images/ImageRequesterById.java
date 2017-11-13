//package io.tanners.taggedwallpaper.network.images;
//
//import android.os.AsyncTask;
//import android.support.design.widget.Snackbar;
//import android.view.View;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.tanners.taggedwallpaper.R;
//import io.tanners.taggedwallpaper.Util.ApiBuilder;
//import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
//import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
//import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;
//
//public class ImageRequesterById extends AsyncTask<Void, Void, List<PhotoResult>> {
//    private Request<PhotoResult > mRequest;
//    private ApiBuilder mBuilder;
//    private int mId;
//
//    public ImageRequesterById()
//    {
//        this.mRequest = new ImageRequest();
//    }
//
//    public ImageRequesterById(ApiBuilder mBuilder, int mId)
//    {
//        this();
//        this.mBuilder = mBuilder;
//        this.mId = mId;
//    }
//
//    @Override
//    protected List<PhotoResult> doInBackground(Void... params)
//    {
//        List<PhotoResult> photos = null;
//
//        try {
//            photos = mRequest.getResult(mBuilder.getHeaders(), mBuilder.buildImageUrlById(mId), null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return photos;
//    }
//
//    @Override
//    protected void onPostExecute(List<PhotoResult> photos) {
//        if(photos != null)
//        {
//
//        }
//        else
//        {
//            // display error snackbar
//            SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.fragment_images_container_id),
//                    "Error loading images",
//                    Snackbar.LENGTH_INDEFINITE,
//                    "Close");
//        }
//    }
//}
