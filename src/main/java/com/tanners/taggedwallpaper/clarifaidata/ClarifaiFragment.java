package com.tanners.taggedwallpaper.clarifaidata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.clarifai.api.RecognitionResult;
import com.tanners.taggedwallpaper.MainActivity;
import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.flickrdata.FlickrDataPhotosSearch;
import com.tanners.taggedwallpaper.flickrdata.FlickrPhotoSearchFragment;
import java.util.List;

public class ClarifaiFragment extends Fragment
{
    private ClarifaiData cdata = null;
    private Context context;
    private ImageView image_view;
    private Button selectButton;
    private final String NO_TAGS = "No tags for this image";
    private DrawerLayout drawer;
    private View view;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        cdata = new ClarifaiData(getActivity());
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.clarifai_fragment_main, null, false);
        image_view = (ImageView) view.findViewById(R.id.image_view);
        selectButton = (Button) view.findViewById(R.id.select_button);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent media_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(media_intent, cdata.getOKCode());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == cdata.getOKCode() && resultCode == Activity.RESULT_OK)
        {
            Uri image = intent.getData();

            if (image != null)
            {
                Glide.with(this).load(image).centerCrop().fitCenter().into(image_view);
                selectButton.setText("Please wait");
                selectButton.setEnabled(false);
                new GetTags().execute(image);
            }
            else
                noTagsToast(cdata.getLoadError());
        }
    }

    private class GetTags extends AsyncTask<Uri, Void, RecognitionResult>
    {
        @Override
        protected RecognitionResult doInBackground(Uri... image)
        {
            return cdata.recognizeBitmap(image[0]);
        }

        @Override
        protected void onPostExecute(RecognitionResult result)
        {
            super.onPostExecute(result);

            if (cdata.addTags(result))
            {
                selectButton.setEnabled(true);
                selectButton.setText("Select a photo");
                ListView listview = (ListView) view.findViewById(R.id.clarifaitagview);
                List<String> tags = null;
                // TODO conditions may not be needed
                if (cdata != null)
                {
                    if (cdata.getTags() != null)
                    {
                        tags = cdata.getTags();
                    } else
                        noTagsToast(NO_TAGS);
                } else
                    noTagsToast(NO_TAGS);

                if (tags.size() == 0)
                    noTagsToast(NO_TAGS);
                else {
                    ClarifaiTagAdapter adapter = new ClarifaiTagAdapter(context, R.layout.clarifai_tags_layout, tags);
                    listview.setAdapter(adapter);
                }
            }
            else
            {
                noTagsToast(cdata.getRecError());
                selectButton.setEnabled(true);
                selectButton.setText("Select a photo");
            }
        }
    }

    private void noTagsToast(String str)
    {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
        toast.show();
    }

    public class ClarifaiTagAdapter extends ArrayAdapter<String>
    {
        private Context context;
        private List<String> taglist;

        public ClarifaiTagAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.taglist = objects;
        }

        @Override
        public int getCount() {
            return taglist.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ClarifaiViewHolder view_holder;

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.clarifai_tags_layout, parent, false);
                view_holder = new ClarifaiViewHolder();
                view_holder.btn = (Button) convertView.findViewById(R.id.clarifai_tag_button);
                convertView.setTag(view_holder);
            }
            else
            {
                view_holder = (ClarifaiViewHolder) convertView.getTag();
            }

            final String tag = this.taglist.get(position);

            if (this.taglist != null)
            {
                view_holder.btn.setText(tag);

                view_holder.btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
                        int count = 0;

                        for (Fragment f : fragments)
                        {
                            if (f.getClass().equals(FlickrPhotoSearchFragment.class))
                            {
                                FlickrPhotoSearchFragment temp = (FlickrPhotoSearchFragment) fragments.get(count);
                                temp.searchByTag(tag, FlickrDataPhotosSearch.OPEN_SEARCH);
                                ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
                            }
                            count++;
                        }
                    }
                });
            }
            return convertView;
        }
    }

    static class ClarifaiViewHolder
    {
        Button btn;
    }
}

