// Hao Zhong
// JAV2 - 202111
// PhotoGridFragment.java
package com.fullsail.adv1.zhonghao_ce03_a;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class PhotoGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "PhotoGridFragment.TAG";
    private PhotoClickListener listener;

    public interface PhotoClickListener {
        void onPhotoClicked(int index);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PhotoClickListener) {
            listener = (PhotoClickListener) context;
        }
    }

    public PhotoGridFragment() { }

    public static PhotoGridFragment newInstance(ArrayList<Uri> _galleryURIs) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TAG, _galleryURIs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_photo_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gallery = view.findViewById(R.id.grid);
        if (getArguments() != null && getContext() != null) {
            ArrayList<Uri> uriList = requireArguments().getParcelableArrayList(TAG);
            GridAdapter adapter = new GridAdapter(getContext(), uriList);
            gallery.setAdapter(adapter);
            gallery.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listener.onPhotoClicked(i);
    }
}