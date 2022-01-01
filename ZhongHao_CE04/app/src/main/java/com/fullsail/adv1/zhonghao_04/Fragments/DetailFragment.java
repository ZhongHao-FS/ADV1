// Hao Zhong
// AD1 - 202111
// DetailFragment.java
package com.fullsail.adv1.zhonghao_04.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullsail.adv1.zhonghao_04.Contact;
import com.fullsail.adv1.zhonghao_04.R;

public class DetailFragment extends Fragment {

    private static final String TAG = "ContactListFragment.tag";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Contact _contact) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();

        args.putSerializable(TAG, _contact);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Contact person = (Contact) getArguments().getSerializable(TAG);

            TextView tv_fullName = view.findViewById(R.id.textView_fullName);
            tv_fullName.setText(person.getFullName());

            TextView tv_priNum = view.findViewById(R.id.textView_primary_num);
            tv_priNum.setText(person.getPriNumber());

            TextView tv_secNum = view.findViewById(R.id.textView_secondary_num);
            tv_secNum.setText(person.getSecNumber());

            ImageView iv_profile = view.findViewById(R.id.imageView_picture);
            if (person.getBlob() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(person.getBlob(), 0, person.getBlob().length);
                iv_profile.setImageBitmap(bmp);
            } else {
                iv_profile.setImageResource(R.drawable.ic_baseline_person_64);
            }
        }
    }
}