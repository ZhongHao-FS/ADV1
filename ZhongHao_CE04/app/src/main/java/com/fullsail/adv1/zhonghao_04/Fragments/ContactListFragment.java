// Hao Zhong
// AD1 - 202111
// ContactListFragment.java
package com.fullsail.adv1.zhonghao_04.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fullsail.adv1.zhonghao_04.Contact;
import com.fullsail.adv1.zhonghao_04.ContactListAdapter;
import com.fullsail.adv1.zhonghao_04.R;

import java.util.ArrayList;

public class ContactListFragment extends ListFragment {

    private static final String TAG = "ContactListFragment.tag";
    private ContactClickListener mListener;

    public interface ContactClickListener {
        void onContactClicked(String _id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ContactClickListener) {
            mListener = (ContactClickListener) context;
        }
    }

    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment newInstance(ArrayList<Contact> _contactList) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle arg = new Bundle();

        arg.putSerializable(TAG, _contactList);
        fragment.setArguments(arg);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getContext() != null) {
            ArrayList<Contact> contacts = (ArrayList<Contact>) getArguments().getSerializable(TAG);
            setListAdapter(new ContactListAdapter(getContext(), contacts));
        }

    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (getArguments() != null) {
            ArrayList<Contact> contacts = (ArrayList<Contact>) getArguments().getSerializable(TAG);
            mListener.onContactClicked(contacts.get(position).getId());
        }
    }
}