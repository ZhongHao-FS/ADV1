// Hao Zhong
// AD1 - 202111
// MainActivity.java
package com.fullsail.adv1.zhonghao_ce02;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fullsail.adv1.zhonghao_ce02.DataModel.DataBaseHelper;
import com.fullsail.adv1.zhonghao_ce02.DataModel.Employee;
import com.fullsail.adv1.zhonghao_ce02.Fragments.EmployeeListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, EmployeeListFragment.EmployeeClickListener {
    private static final String PREFERENCE_DATEFORMAT_KEY = "date_format_preference";
    private static final String KEY_VIEWEMPLOYEE = "EXTRA DETAIL EMPLOYEE";

    ArrayList<Employee> mEmployees = new ArrayList<>();
    Context context;
    DataBaseHelper mDatabase;
    String mColumnName;
    boolean mASC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerLeft = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterLeft = ArrayAdapter.createFromResource(this, R.array.spinner_sortingMethod, R.layout.support_simple_spinner_dropdown_item);
        adapterLeft.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerLeft.setAdapter(adapterLeft);
        spinnerLeft.setOnItemSelectedListener(this);

        Spinner spinnerRight = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapterRight = ArrayAdapter.createFromResource(this, R.array.spinner_sortingOrder, R.layout.support_simple_spinner_dropdown_item);
        adapterRight.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRight.setAdapter(adapterRight);
        spinnerRight.setOnItemSelectedListener(this);

        context = this;
        mDatabase = DataBaseHelper.getInstance(this);
        loadSQL();

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.listFragmentContainerView, EmployeeListFragment.newInstance(mEmployees)).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = (String) adapterView.getItemAtPosition(i);

        switch (selection) {
            case "Sort by Status":
                mColumnName = "status";
                break;
            case "Sort by ID":
                mColumnName = "_id";
                break;
            case "Ascending":
                mASC = true;
                break;
            case "Descending":
                mASC = false;
        }

        refreshList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mColumnName = "status";
        mASC = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent explicitForm = new Intent(context, FormActivity.class);
            explicitForm.setAction(getString(R.string.intentAction_add));

            mLauncher.launch(explicitForm);
        } else if (item.getItemId() == R.id.settings) {
            Intent explicitSettings = new Intent(context, SettingsActivity.class);
            mLauncher.launch(explicitSettings);
        }

        return super.onOptionsItemSelected(item);
    }

    final ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    refreshList();
                }
            }
    );

    private void loadSQL() {
        Cursor cursor = mDatabase.getSortedData(mColumnName, mASC);
        mEmployees = new ArrayList<>();

        if (cursor == null) {
            return;
        }
        while (cursor.moveToNext()) {
            int fNameIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_FIRSTNAME);
            String fName = cursor.getString(fNameIndex);

            int lNameIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_LASTNAME);
            String lName = cursor.getString(lNameIndex);

            int idIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_ID);
            int id = cursor.getInt(idIndex);

            int statusIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_STATUS);
            String status = cursor.getString(statusIndex);

            int hireDateIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_HIREDATE);
            String hireDateString = cursor.getString(hireDateIndex);

            SharedPreferences dPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            String dFormat = dPrefs.getString(PREFERENCE_DATEFORMAT_KEY, getString(R.string.default_dateFormat));
            SimpleDateFormat formatter = new SimpleDateFormat(dFormat, Locale.US);

            Date hireDate = new Date();
            try {
                hireDate = formatter.parse(hireDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mEmployees.add(new Employee(fName, lName, id, hireDate, status));
        }

        cursor.close();
    }

    private void refreshList() {
        loadSQL();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.listFragmentContainerView, EmployeeListFragment.newInstance(mEmployees)).commit();
    }

    @Override
    public void onEmployeeClicked(int index) {
        Intent explicitDetail = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_VIEWEMPLOYEE, mEmployees.get(index));
        explicitDetail.putExtras(bundle);
        mLauncher.launch(explicitDetail);
    }
}