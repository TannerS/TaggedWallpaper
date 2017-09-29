package io.tanners.taggedwallpaper.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import io.tanners.taggedwallpaper.R;

public class SimilarImagesFragment extends Fragment {
    private View view;
    public static final String SIMILAR = "Similar Images";
    // user options for selection menu
    private static final String SELECT = "Similar Images";
    private static final String TAKE = "Take Images";
    private static final String MENU_TITLE = "Image Selection";
    private static final String EXIT = "Cancel";
    private final String[] imageOptions = {SELECT, TAKE, EXIT};
//    private final int SELECT_IMAGE = 4;
    private final int SELECT_IMAGE = 200;
//    private final int TAKE_IMAGE = 2;
    private ImageView mMainImage;

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
        loadResources();
        return view;
    }

    private void loadResources()
    {
        Button mImageUploadButton = (Button) view.findViewById(R.id.image_similar_select_button);

        mImageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("BUTTON", "debug 1");
                selectImageDialog();
            }
        });

        //mMainImage = view.findViewById(R.id.image_rec_image_preview);
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

    /**
     * allows user to select image in galley
     */
//    private void selectImage()
//    {
//        // brings up intent to display images from gallery to select
//        // MediaStore.Images.Media.EXTERNAL_CONTENT_URI: The content:// style URI for the "primary" external storage volume.
//        // https://developer.android.com/reference/android/content/Intent.html#ACTION_PICK
//        Intent mSelectImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        mSelectImageIntent.setType("image/*");
//        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity
//        if (mSelectImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(mSelectImageIntent, SELECT_IMAGE);
//        }
//    }


    /**
     * allows user to take a new image
     */
//    private void takeImage()
//    {
//        // returns uri ot bitmap to onActivityResult when using MediaStore.ACTION_IMAGE_CAPTURE
//        Intent mTakeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        mTakeImageIntent.setType("image/*");
//        // https://developer.android.com/reference/android/content/Intent.html#FLAG_GRANT_READ_URI_PERMISSION
////        mTakeImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        mTakeImageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity
//        if (mTakeImageIntent.resolveActivity(getActivity().getPackageManager()) != null)
//        {
//            startActivityForResult(mTakeImageIntent, TAKE_IMAGE);
//        }
//    }

    /**
     * shows user image options (how to get a image that will be used)
     * https://stackoverflow.com/questions/25524617/intent-to-choose-between-the-camera-or-the-gallery-in-android
     */
    private void selectImageDialog()
    {
        // create new intent to select photo
        Intent pickIntent = new Intent();
        // set type
        pickIntent.setType("image/*");
        // set action, this ACTION_GET_CONTENT is something to google the difference between it and ACTION_GET
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);

        Log.i("PHOTO", "DEBU -6: " + pickIntent.getAction());

        // crate new intent to take photo
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Log.i("PHOTO", "DEBU -5: " + takePhotoIntent.getAction());

        // title
        String pickTitle = "Select from...";
        // create chooser intent
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        // allow the other intents to be an option
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});
        // Verify the intent will resolve to at least one activity
        if (chooserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(chooserIntent, SELECT_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // intent had no errors
        // user selected the image
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {

//                Log.i("PHOTO", "DEBU -4");
//                String action = data.getAction();

//                if(action != null) {
//

                // TODO all below needs testing
                if(data.getExtras() != null)
                {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

//                    if(photo != null) {
                        loadImage(photo);
//                    }
                }
                else
                {
                    loadImage(data.getData());
                }


//                    if (action.equalsIgnoreCase(Intent.ACTION_GET_CONTENT)) {
//                        Log.i("PHOTO", "DEBU 1");
//
//                    } else if (action.equalsIgnoreCase(MediaStore.ACTION_IMAGE_CAPTURE)) {
//                        Log.i("PHOTO", "DEBU 2");
//                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//                            Log.i("PHOTO", "DEBU 3");
//                            loadImage(data.getData());

//                        } else {
//                            Log.i("PHOTO", "DEBU 4");
//                            Bitmap photo = (Bitmap) data.getExtras().get("data");
//
//                        }

//                    }
//                }
            }
        }



                // temp variable
//                Uri mImageUri = null;




                // newer android versions use a different method
                //if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    // get data passed back from intent (user image)







//
//                    mImageUri = data.getData();
//
//                Log.i("PHOTO", "DEBU 1");
//
//                    if(mImageUri != null)
//                    {
//                        Log.i("PHOTO", "DEBU 2");
//                        loadImage(mImageUri);
//                    }
//                    else
//                    {
//                        Log.i("PHOTO", "DEBU 3");
//                        Bitmap photo = (Bitmap) data.getExtras().get("data");
//                        loadImage(photo);
//                    }
//
//

                //}
//                else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                //else {
                    // get bitmap from intent (user image)

                //}


//            }
//            else if (requestCode == TAKE_IMAGE) {
//                // temp variable
//                Uri mImageUri = null;
//                // newer android versions use a different method
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//                    // get data passed back from intent (user image)
//                    mImageUri = data.getData();
//                }
////                else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
//                else {
//                    // get bitmap from intent (user image)
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                }
//            }
//        }

    }




    private void loadImage(Bitmap mImage)
    {
        // Load image into imageview with options
//        BitmapTransitionOptions transitionOptions = new BitmapTransitionOptions().crossFade();
//        RequestOptions cropOptions = new RequestOptions()
//                .centerCrop()
//                .error(R.drawable.ic_error_black_48dp)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        mImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//        Glide.with(this)
//                .load(stream.toByteArray())
//                    .apply(cropOptions)
////                .transition(transitionOptions)
//                .into(mMainImage);
    }

    private void loadImage(Uri mImage)
    {
        // Load image into imageview with options
//        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
//        RequestOptions cropOptions = new RequestOptions()
//                .centerCrop()
//                .error(R.drawable.ic_error_black_48dp)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
//
//        Glide.with(this)
//                .load(mImage)
//                .apply(cropOptions)
//                .transition(transitionOptions)
//                .into(mMainImage);
    }


}
