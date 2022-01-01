// Hao Zhong
// AD1 - 202111
// ImageGridFragment.java
package com.fullsail.android.ad1.zhonghao_ce06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String BROADCAST_ACTION =
            "com.fullsail.android.ad1.zhonghao_ce06.BROADCAST_ACTION";
    private ImageClickListener listener;
    private final UpdateReceiver receiver = new UpdateReceiver();

    public interface ImageClickListener {
        void onImageClicked(int index);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ImageClickListener) {
            listener = (ImageClickListener) context;
        }
    }

    public ImageGridFragment() { }

    public static ImageGridFragment newInstance() {

        return new ImageGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_grid, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION);

        requireActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();

        requireActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listener.onImageClicked(i);
    }

    class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_ACTION)) {
                GridView gallery = requireView().findViewById(R.id.grid);
                if (context != null) {
                    GridAdapter adapter = new GridAdapter(context);
                    gallery.setAdapter(adapter);
                    gallery.setOnItemClickListener(ImageGridFragment.this);
                }
            }
        }
    }
}