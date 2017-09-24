package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class LatestImagesFragment extends ImageFragment implements ErrorCallBack{
    public static final String NEWEST = "Newest";
    // creates new instance
    public static LatestImagesFragment newInstance() {
        return new LatestImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        loadResources(view);

        // call base class
        loadRequest(this, new ApiBuilder(this.tag, 100, 1, ApiBuilder.OrderBy.LATEST));

        return view;
    }

    /**
     * callback for imagerequester
     */
    @Override
    public void displayError() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.fragment_images_container_id),
                "Error loading images",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }



//        public static void createAndDisplaySnackBar(View view, String message, int length, String mDismissMessage,  View.OnClickListener mCallback)


//        throw new RuntimeException(this.toString() + " must implement in subclass");
//        Toast.makeText(mContext, "Error loading images on " + this., Toast.LENGTH_LONG).show();
//        final Snackbar mErrorSnackBar = Snackbar.make(, , );
//
//        mErrorSnackBar.setAction("Close",
//
//        mErrorSnackBar.show();
//    }

}
