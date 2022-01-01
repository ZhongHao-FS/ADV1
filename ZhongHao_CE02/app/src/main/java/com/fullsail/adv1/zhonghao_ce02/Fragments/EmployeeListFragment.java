// Hao Zhong
// AD1 - 202111
// EmployeeListFragment.java
package com.fullsail.adv1.zhonghao_ce02.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fullsail.adv1.zhonghao_ce02.DataModel.Employee;
import com.fullsail.adv1.zhonghao_ce02.R;

import java.util.ArrayList;

public class EmployeeListFragment extends ListFragment {

    private static final String TAG = "EmployeeListFragment.TAG";
    private EmployeeClickListener listener;

    public interface EmployeeClickListener {
        void onEmployeeClicked(int index);
    }

    public EmployeeListFragment() {
        // Required empty public constructor
    }

    public static EmployeeListFragment newInstance(ArrayList<Employee> _employees) {
        Bundle args = new Bundle();
        args.putSerializable(TAG, _employees);

        EmployeeListFragment fragment = new EmployeeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof EmployeeClickListener) {
            listener = (EmployeeClickListener) context;
        }
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

        ArrayList<Employee> employees = (ArrayList<Employee>) requireArguments().getSerializable(TAG);
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                employees
        );
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        listener.onEmployeeClicked(position);
    }
}