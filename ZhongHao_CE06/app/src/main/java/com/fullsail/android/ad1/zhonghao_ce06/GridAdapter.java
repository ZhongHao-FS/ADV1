// Hao Zhong
// AD1 - 202111
// GridAdapter.java
package com.fullsail.android.ad1.zhonghao_ce06;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;


public class GridAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x1002;
    private final Context mContext;

    public GridAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return MainActivity.IMAGES.length;
    }

    @Override
    public Object getItem(int i) {
        if (i >= 0 && i < MainActivity.IMAGES.length) {
            return MainActivity.IMAGES[i];
        }
        return null;
    }

    @Override
    public long getItemId(int i) { return BASE_ID + i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String fileName = (String) getItem(i);

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView image = view.findViewById(R.id.gridItem_imageView);
        if (!fileName.isEmpty()) {
            byte[] blob = FileUtility.readByteArray(mContext, fileName);
            if (blob == null) {
                image.setImageResource(R.drawable.ic_launcher_background);
            } else {
                Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                image.setImageBitmap(bmp);
            }
        }

        return view;
    }
}
