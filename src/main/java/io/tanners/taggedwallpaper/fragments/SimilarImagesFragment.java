package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;

public class SimilarImagesFragment extends Fragment {
    private View view;
    public static final String SIMILAR = "Similar Images";
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
        view = inflater.inflate(R.layout.fragment_image_rec, container, false);
        return view;
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

}
