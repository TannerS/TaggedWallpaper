//package com.tanners.taggedwallpaper.fragments;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.CursorLoader;
//import android.support.v7.app.AlertDialog;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//import com.bumptech.glide.Glide;
//import com.clarifai.api.RecognitionRequest;
//import com.clarifai.api.RecognitionResult;
//import com.clarifai.api.Tag;
//import com.tanners.taggedwallpaper.MainActivity;
//import com.tanners.taggedwallpaper.R;
////import com.tanners.taggedwallpaper.clarifaidata.ImageRec;
//import com.tanners.taggedwallpaper.network.images.ImageRequest;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//public class SimilarTagsFragment extends Fragment
//{
//    public static String SIMILAR = "Similar Tags";
//
//    private ImageRec cdata = null;
//    private Context context;
//    private ImageView image_view;
//    private Button selectButton;
//    private final String NO_TAGS = "No tags for this image";
//    private View view;
//    private boolean nsfw;
//    private ClarifaiTagAdapter adapter;
//
//    private final int REQUEST_EXTERNAL_STORAGE = 1;
//    private String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };
//
//    public static SimilarTagsFragment newInstance() {
//        return new SimilarTagsFragment();
//    }
//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        this.context = context;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_similar, container, false);
//        return view;
//    }
//
//
//    private String[] RequestNeededPermissions(String[] permissions)
//    {
//        ArrayList<String> needed_permissions = new ArrayList<String>();
//        // check version of android
//        // loop all request permissions
//        for (String permission : permissions)
//        {
//            // if permission is granted, DO NOTHING
//            if (ActivityCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
//            {
//                // if permission is NOT granted, put it in array list
//                needed_permissions.add(permission);
//            }
//        }
//        return needed_permissions.toArray(new String[needed_permissions.size()]);
//    }
//
//    /**
//     * after permissions are requested, user response goes here
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
//    {
//        switch(requestCode)
//        {
//            case REQUEST_EXTERNAL_STORAGE:
//
//                boolean decision = false;
//
//                for(int i = 0; i < permissions.length; i++)
//                {
//                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
//                    {
//                        decision = true;
//                    }
//                    else
//                    {
//                        decision = false;
//                        break;
//                    }
//                }
//
//                if (decision)
//                {
//                    cdata = new ImageRec(getActivity());
//
//                    selectButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            final Intent media_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(media_intent, cdata.getOKCode());
//                        }
//                    });
//                }
//                else
//                {
//                    selectButton.setEnabled(false);
//                    selectButton.setText("Permissions Denied");
//                }
//
//                break;
//        }
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
////        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        view = layoutInflater.inflate(R.layout.fragment_similar, null, false);
//
//        image_view = (ImageView) view.findViewById(R.id.image_view);
//        selectButton = (Button) view.findViewById(R.id.select_button);
//
//        String[] needed_permissions = RequestNeededPermissions(PERMISSIONS_STORAGE);
//
//        if(needed_permissions.length > 0)
//        {
//            // request all permissions in arraylist
//            requestPermissions(needed_permissions, REQUEST_EXTERNAL_STORAGE);
//        }
//        else
//        {
//            cdata = new ImageRec(getActivity());
//
//            selectButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Intent media_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(media_intent, cdata.getOKCode());
//                }
//            });
//        }
//
//        nsfw = false;
//    }
//
//    private String getAbsolutePathFromUri(Uri uri)
//    {
//        String[] properties = { MediaStore.Images.Media.DATA };
//        CursorLoader cursor_loader = new CursorLoader(getContext(), uri, properties, null, null, null);
//        Cursor cursor = cursor_loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent)
//    {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        if (requestCode == cdata.getOKCode() && resultCode == Activity.RESULT_OK)
//        {
//            Uri image_uri = intent.getData();
//
//            File image_file = new File(getAbsolutePathFromUri(image_uri));
//
//            if (image_uri != null)
//            {
//                Glide.with(this).load(image_uri).centerCrop().fitCenter().into(image_view);
//                selectButton.setText("Please wait");
//                selectButton.setEnabled(false);
//
//                NSFWCheck nsfw_checker = new NSFWCheck();
//                nsfw_checker.execute(image_file);
//
//                List<RecognitionResult> rec_results = null;
//
//                try {
//                    rec_results = nsfw_checker.get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//                // rec_results.get(0) : only one image sent over
//                //rec_results.get(0).getJsonResponse() : get response as json
//                // following this format : {"tag":{"classes":["sfw","nsfw"],"concept_ids":["ai_RT20lm2Q","ai_KxzHKtPl"],"probs":[0.9999136924743652,8.635851554572582e-05]}}
//                // use get method to get that object, but follow the order
//                // rec_results.get(0).getJsonResponse().getAsJsonObject("tag")
//                // rec_results.get(0).getJsonResponse().getAsJsonObject("tag")
//                // rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("probs")
//                // this returned and array so we need jsonarray
//                // rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("probs").getAsJsonArray()
//                // and we need the nsfw value (first element)
//                // rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("probs").getAsJsonArray().get(0)
//                // as a decimal
//                // rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("probs").getAsJsonArray().get(0).getAsBigDecimal()
//                // use big decimal for proper float point comparison
//
//                Log.i("NSFW2", rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("classes").getAsJsonArray().get(0).getAsString());
//
//                int nsfw_element_position = -1;
//
//                // this is needed since the position of "classes":["sfw","nsfw"] can change and also be "classes":["nsfw","sfw"]
//                // which throws off this order
//                // "probs":[0.9992555379867554,0.0007444904767908156]
//                if(rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("classes").getAsJsonArray().get(0).getAsString().equals("nsfw"))
//                {
//                    nsfw_element_position = 0;
//                }
//                else
//                {
//                    nsfw_element_position = 1;
//                }
//
//                BigDecimal nsfw_value = rec_results != null ? rec_results.get(0).getJsonResponse().getAsJsonObject("tag").get("probs").getAsJsonArray().get(nsfw_element_position).getAsBigDecimal() : null;
//
//                if(nsfw_value == null)
//                {
//                    displayAlertBox("Error tagging image");
//                }
//                else
//                {
//                    Log.i("NSFW2", String.valueOf(nsfw_value));
//
//                    int comparison_result = nsfw_value.compareTo(new BigDecimal(0.50));
//
//                    if( comparison_result == 0 )
//                    {
//                        displayAlertBox("Possible nsfw image, cannot tag");
//                        resetFeatures();
//                    }
//                    else if( comparison_result == 1 )
//                    {
//                        displayAlertBox("Possible nsfw image, cannot tag");
//                        resetFeatures();
//                    }
//                    else if( comparison_result == -1 )
//                    {
//                        // // nsfw value is <= 50
//                        GetTags tag_checker = new GetTags();
//                        tag_checker.execute(image_uri);
//                    }
//                }
//            }
//            else
//                noTagsToast(cdata.getLoadError());
//        }
//    }
//
//    private void resetFeatures()
//    {
//        selectButton.setEnabled(true);
//        selectButton.setText("Select a photo");
//        image_view.setImageResource(android.R.color.transparent);
//
//        if(adapter != null)
//        {
//            adapter.clear();
//        }
//    }
//
//    private void displayAlertBox(String message)
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage(message).setTitle("Error");
//        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog disclaimer = builder.create();
//
//        if(disclaimer.getWindow() != null) {
//            disclaimer.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.MainBGColor)));
//            disclaimer.show();
//        }
//    }
//
//    private class NSFWCheck extends AsyncTask<File, Void, List<RecognitionResult>>
//    {
//        @Override
//        protected List<RecognitionResult> doInBackground(File... image)
//        {
//            RecognitionRequest nsfw_request = new RecognitionRequest(image[0]);
//            nsfw_request.setModel("nsfw-v1.0");
//
//            return cdata.recognize(nsfw_request);
//        }
//
//        @Override
//        protected void onPostExecute(List<RecognitionResult> result)
//        {
//            super.onPostExecute(result);
//        }
//
//    }
//
//    private class GetTags extends AsyncTask<Uri, Void, RecognitionResult>
//    {
//        @Override
//        protected RecognitionResult doInBackground(Uri... image)
//        {
//            return cdata.recognizeBitmap(image[0]);
//        }
//
//        @Override
//        protected void onPostExecute(RecognitionResult result)
//        {
//            super.onPostExecute(result);
//
//            ListView listview = (ListView) view.findViewById(R.id.clarifaitagview);
//
//            List<String> tags =  new ArrayList<String>();
//
//            for(Tag tag : result.getTags()) {
//
//                tags.add(tag.getName());
//            }
//
//            if ((result.getTags() != null) || (result.getTags().size() != 0))
//            {
//                adapter = new ClarifaiTagAdapter(context, R.layout.clarifai_tags_layout, tags);
//                listview.setAdapter(adapter);
//                selectButton.setEnabled(true);
//                selectButton.setText("Select a photo");
//                return;
//            }
//            noTagsToast(NO_TAGS);
//        }
//    }
//
//    private void noTagsToast(String str)
//    {
//        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
//        toast.show();
//    }
//
//    public class ClarifaiTagAdapter extends ArrayAdapter<String>
//    {
//        private Context context;
//        private List<String> taglist;
//
//        public ClarifaiTagAdapter(Context context, int resource, List<String> objects) {
//            super(context, resource, objects);
//            this.context = context;
//            this.taglist = objects;
//        }
//
//        @Override
//        public int getCount() {
//            return taglist.size();
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ClarifaiViewHolder view_holder;
//
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            if (convertView == null)
//            {
//                convertView = layoutInflater.inflate(R.layout.clarifai_tags_layout, parent, false);
//                view_holder = new ClarifaiViewHolder();
//                view_holder.btn = (Button) convertView.findViewById(R.id.clarifai_tag_button);
//                convertView.setTag(view_holder);
//            }
//            else
//            {
//                view_holder = (ClarifaiViewHolder) convertView.getTag();
//            }
//
//            final String tag = this.taglist.get(position);
//
//            if (this.taglist != null)
//            {
//                view_holder.btn.setText(tag);
//
//                view_holder.btn.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
//                        int count = 0;
//
//                        for (Fragment f : fragments)
//                        {
//                            if (f.getClass().equals(PhotoSearchFragment.class))
//                            {
//                                PhotoSearchFragment temp = (PhotoSearchFragment) fragments.get(count);
//                                temp.searchByTag(tag, ImageRequest.OPEN_SEARCH);
//                                ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
//                            }
//                            count++;
//                        }
//                    }
//                });
//            }
//            return convertView;
//        }
//    }
//
//    static class ClarifaiViewHolder
//    {
//        Button btn;
//    }
//}
