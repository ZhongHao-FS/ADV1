// Hao Zhong
// AD1 - 202111
// ContactListAdapter.java
package com.fullsail.adv1.zhonghao_04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ContactListAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Contact> mContacts;

    public ContactListAdapter(@NonNull Context context, @NonNull ArrayList<Contact> objects) {
        mContext = context;
        mContacts = objects;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < mContacts.size()) {
            return mContacts.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        Contact contact = (Contact) getItem(i);

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        if (contact != null) {
            vh.tv_fLName.setText(contact.getFirstLastName());
            vh.tv_number.setText(contact.getPriNumber());

            if (contact.getBlob() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(contact.getBlob(), 0, contact.getBlob().length);
                vh.iv_thumbnail.setImageBitmap(bmp);
            } else {
                vh.iv_thumbnail.setImageResource(R.drawable.ic_baseline_person_64);
            }
        }

        return view;
    }

    static class ViewHolder {
        final TextView tv_fLName;
        final TextView tv_number;
        final ImageView iv_thumbnail;

        public ViewHolder(View _layout) {
            tv_fLName = _layout.findViewById(R.id.textView_fLName);
            tv_number = _layout.findViewById(R.id.textView_number);
            iv_thumbnail = _layout.findViewById(R.id.imageView_thumbnail);
        }
    }
}
