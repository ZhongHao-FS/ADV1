// Hao Zhong
// AD1 - 202111
// FormFragment.java
package com.fullsail.adv1.zhonghao_ce02.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fullsail.adv1.zhonghao_ce02.DataModel.Employee;
import com.fullsail.adv1.zhonghao_ce02.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private static final String TAG = "FormFragment.TAG";
    private static final String PREFERENCE_DATEFORMAT_KEY = "date_format_preference";

    private ReturnEmployeeListener listener;
    private String mDateFormat;
    private Date mHireDate;
    EditText editFName;
    EditText editLName;
    EditText editID;
    EditText editStatus;
    TextView tvDate;

    public interface ReturnEmployeeListener {
        void onReturnEmployee(Employee employee);
    }

    public FormFragment() {
        // Required empty public constructor
    }

    public static FormFragment newInstance(Employee _employee) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putSerializable(TAG, _employee);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ReturnEmployeeListener) {
            listener = (ReturnEmployeeListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editFName = view.findViewById(R.id.editText_firstName);
        editLName = view.findViewById(R.id.editText_lastName);
        editID = view.findViewById(R.id.editTextNumber);
        editStatus = view.findViewById(R.id.editText_status);
        tvDate = view.findViewById(R.id.textView_date);

        SharedPreferences dPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        mDateFormat = dPrefs.getString(PREFERENCE_DATEFORMAT_KEY, getString(R.string.default_dateFormat));

        assert getArguments() != null;
        if (getArguments().getSerializable(TAG) != null) {
            Employee employee = (Employee) getArguments().getSerializable(TAG);
            editFName.setText(employee.getFName());
            editLName.setText(employee.getLName());
            editID.setText(String.valueOf(employee.getId()));
            editID.setEnabled(false);
            editStatus.setText(employee.getStatus());
            tvDate.setText(DateFormat.format(mDateFormat, employee.getHDate()));
            mHireDate = employee.getHDate();
        }

        Button selectDate = view.findViewById(R.id.button);
        selectDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(i, i1, i2);
        tvDate.setText(DateFormat.format(mDateFormat, selectedDate));
        mHireDate = selectedDate.getTime();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_form, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save && validateInput()) {
            String fName = editFName.getText().toString();
            String lName = editLName.getText().toString();
            int id = Integer.parseInt(editID.getText().toString());
            String status = editStatus.getText().toString();

            listener.onReturnEmployee(new Employee(fName, lName, id, mHireDate, status));
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateInput() {
        String fName = editFName.getText().toString();
        if (fName.trim().isEmpty()) {
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        String lName = editLName.getText().toString();
        if (lName.trim().isEmpty()) {
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        String status = editStatus.getText().toString();
        if (status.trim().isEmpty()) {
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int id = Integer.parseInt(editID.getText().toString());
            if (id <= 0) {
                Toast.makeText(getContext(), R.string.warning_num, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException exception) {
            Toast.makeText(getContext(), R.string.warning_num, Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(mDateFormat, Locale.US);
        try {
            formatter.parse(tvDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}