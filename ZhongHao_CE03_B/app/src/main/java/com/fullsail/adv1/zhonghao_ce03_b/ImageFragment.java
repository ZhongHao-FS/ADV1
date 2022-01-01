// Hao Zhong
// JAV2 - 202111
// ImageFragment.java
package com.fullsail.adv1.zhonghao_ce03_b;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

    private static final String TAG = "ImageFragment.tag";


    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(Uri _imageUri) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(TAG, _imageUri);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getContext() != null) {
            Uri uri = requireArguments().getParcelable(TAG);
            ImageView image = view.findViewById(R.id.imageView);
            image.setImageURI(uri);
        }
    }
}