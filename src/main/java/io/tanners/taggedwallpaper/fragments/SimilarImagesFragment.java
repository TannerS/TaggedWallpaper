package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.tanners.taggedwallpaper.R;

public class SimilarImagesFragment extends Fragment {
    private View view;
    public static final String SIMILAR = "Similar Images";
    // user options for selection menu
//    final CharSequence[] imageOptions = { "Select Image", "Take Image", "Nevermind" };

    private static final String SELECT = "Similar Images";
    private static final String TAKE = "Take Images";
    private static final String EXIT = "Cancel";
    private final String[] imageOptions = {SELECT, TAKE, EXIT};
    private final int SELECT_IMAGE = 4;
    private final int TAKE_IMAGE = 2;




    // creates new instance
    public static SimilarImagesFragment newInstance() {
        return new SimilarImagesFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadResources();
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_similar, container, false);
//        loadResources();
        return view;
    }

    private void loadResources()
    {
        Button mImageUploadButton = (Button) view.findViewById(R.id.image_similar_select_button);

        mImageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageDialog();
            }
        });
    }

    /**
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     *
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void selectImage()
    {
        // brings up intent to display images from gallery to select
        Intent mSelectImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity
        if (mSelectImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(mSelectImageIntent, SELECT_IMAGE);
        }
    }


    private void takeImage()
    {
        // returns uri ot bitmap to onActivityResult when using MediaStore.ACTION_IMAGE_CAPTURE
        Intent mTakeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // https://developer.android.com/reference/android/content/Intent.html#FLAG_GRANT_READ_URI_PERMISSION
//        mTakeImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mTakeImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity
        if (mTakeImageIntent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivityForResult(mTakeImageIntent, TAKE_IMAGE);
        }
    }

    private void selectImageDialog()
    {
        // create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // set title
        builder.setTitle("Image Selection");
        // set listener for options
        builder.setItems(imageOptions, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface mDialog, int mItem) {
                // user can picked an already saved photo from gallery
                if (imageOptions[mItem].equals(SELECT))
                    selectImage();
                // user can upload newely taken photo
                else if (imageOptions[mItem].equals(TAKE))
                    takeImage();
                // user exited the display
                else if (imageOptions[mItem].equals(EXIT))
                    //close the alert dialogue if the user hits "cancel"
                    mDialog.dismiss();
                else
                {
                    // error
                }
            }
        });

        // show dialog
        builder.show();
    }




}
