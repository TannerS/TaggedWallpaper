//package com.tanners.taggedwallpaper.adapters;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v4.view.GravityCompat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import com.tanners.taggedwallpaper.R;
//import com.tanners.taggedwallpaper.fragments.PhotoSearchFragment;
//import com.tanners.taggedwallpaper.interfaces.IFindFragment;
//import java.util.List;
//
//public class TagAdapter extends ArrayAdapter<String>
//{
//    private Context context;
//    private List<String> tags;
//
//    public TagAdapter(Context context, int resource, List<String> tags)
//    {
//        super(context, resource, tags);
//        this.context = context;
//        this.tags = tags;
//    }
//
//    @Override
//    public int getCount()
//    {
//        return tags != null ? tags.size() : 0;
//    }
//
//    @NonNull
//    @Override
//    public View getView(final int position, View convertView, @NonNull ViewGroup parent)
//    {
//        final FlickrViewHolder viewHolder;
//
//        if(convertView == null)
//        {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.flickr_menu_tags_layout, parent, false);
//            viewHolder = new FlickrViewHolder();
//            viewHolder.btn =  (Button) convertView.findViewById(R.id.flickr_tag_button);
//            convertView.setTag(viewHolder);
//        }
//        else
//        {
//            viewHolder = (FlickrViewHolder) convertView.getTag();
//        }
//
//        viewHolder.btn.setText(this.tags.get(position));
////
//        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                IFindFragment finder = ((IFindFragment) (context));
//
//                PhotoSearchFragment fragment = (PhotoSearchFragment) finder.findFragmentByTitle(PhotoSearchFragment.SEARCH);
//
//
//                if(fragment.getClass().equals(PhotoSearchFragment.class))
//                {
////                    view_pager.s.setCurrentItem(1);
//                    view_pager.
//                    drawer.closeDrawer(GravityCompat.START);
//                    fragment.searchByTag(tag);
//                }
//
//                // TODO call search page and search
//                Log.i("TAG", "CLICKED ON " + tags.get(position));
//
//
//
//
//
//
//
//
//            }
//        });
//
//        return convertView;
//    }
//
//    static private class FlickrViewHolder
//    {
//        Button btn;
//    }
//}
//
