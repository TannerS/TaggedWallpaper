package com.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.async.CollectPhotos;

public class RecentPhotosFragment extends Fragment
{
    public final static String RECENT = "Recent Tags";

    private Context context;
    private View view;
    private RecyclerView recycle_view;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        GridLayoutManager grid = new GridLayoutManager(context, 2);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(grid);

        new CollectPhotos(context, recycle_view).execute();

    }

    public static RecentPhotosFragment newInstance() {
        return new RecentPhotosFragment();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent, container, false);
        return view;
    }


}
