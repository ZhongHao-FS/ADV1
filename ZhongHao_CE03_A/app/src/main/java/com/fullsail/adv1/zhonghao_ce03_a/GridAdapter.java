// Hao Zhong
// JAV2 - 202111
// GridAdapter.java
package com.fullsail.adv1.zhonghao_ce03_a;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x1001;
    private final Context mContext;
    private final ArrayList<Uri> mUri;

    public GridAdapter(@NonNull Context context, @NonNull ArrayList<Uri> uriList) {
        mContext = context;
        mUri = uriList;
    }

    @Override
    public int getCount() {
        return mUri.size();
    }

    @Override
    public Object getItem(int i) {
        if (i >= 0 && i < mUri.size()) {
            return mUri.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return BASE_ID + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Uri uri = (Uri) getItem(i);

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, viewGroup, false);

        }

        ImageView image = view.findViewById(R.id.gridItem_imageView);
        if (uri != null) {
            image.setImageURI(uri);
        }

        return view;
    }
}
