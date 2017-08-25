package com.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.tanners.taggedwallpaper.MainActivity;
import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.flickrdata.FlickrDataPhotosSearch;
import com.tanners.taggedwallpaper.flickrdata.FlickrPhotoSearchFragment;

import java.util.Collections;
import java.util.List;

public class TagAdapter extends ArrayAdapter<String>
{
    private Context context;
    private List<String> taglist;
    private FlickrPhotoSearchFragment search_frag;
    private int frag_count;

    public TagAdapter(Context context, int resource, List<String> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.taglist = objects;
        this.frag_count = 0;
    }

    @Override
    public int getCount()
    {
        return taglist.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.flickr_menu_tags_layout, parent, false);
            view_holder = new MainActivity.FlickrViewHolder();
            view_holder.btn =  (Button) convertView.findViewById(R.id.flickr_tag_button);
            convertView.setTag(view_holder);
        }
        else
        {
            view_holder = (MainActivity.FlickrViewHolder) convertView.getTag();
        }

        final String tag = this.taglist.get(position);
        view_holder.btn.setText(tag);

        view_holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(search_frag == null)
                    searchForFragment();
                view_pager.setCurrentItem(1);
                drawer.closeDrawer(GravityCompat.START);
                search_frag.searchByTag(tag, FlickrDataPhotosSearch.GROUP_SEARCH);
            }

        });

        return convertView;
    }

    private void searchForFragment()
    {
        List<Fragment> fragments = getFragments();

        for (Fragment f : fragments)
        {
            if (f.getClass().equals(FlickrPhotoSearchFragment.class))
                search_frag = (FlickrPhotoSearchFragment) fragments.get(frag_count);
            frag_count++;
        }
    }

    static private class FlickrViewHolder
    {
        Button btn;
    }
}

