package com.example.tyw.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by tangyiwu on 2016/11/24.
 */

public class ImageFragment extends Fragment {
    public static final String IMAGE_URL = "image_url";
    public String image_url;

    public static Fragment newFragment(String url) {
        Fragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_url = getArguments().getString(IMAGE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        displayImage(imageView);
        return imageView;
    }

    private void displayImage(ImageView imageView) {
        Glide.with(imageView.getContext()).load(image_url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }
}
