// Hao Zhong
// AD1 - 202111
// FormActivity.java
package com.fullsail.adv1.zhonghao_ce02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.fullsail.adv1.zhonghao_ce02.DataModel.DataBaseHelper;
import com.fullsail.adv1.zhonghao_ce02.DataModel.Employee;
import com.fullsail.adv1.zhonghao_ce02.Fragments.FormFragment;

public class FormActivity extends AppCompatActivity implements FormFragment.ReturnEmployeeListener {
    private static final String KEY_EDITEMPLOYEE = "EXTRA OLD EMPLOYEE";

    DataBaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mDatabase = DataBaseHelper.getInstance(this);

        if (getIntent().getAction().equals(getString(R.string.intentAction_add))) {
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.formFragmentContainerView, FormFragment.newInstance(null)).commit();
        } else if (getIntent().getAction().equals(getString(R.string.intentAction_edit))) {
            Bundle bundle = getIntent().getExtras();
            Employee employee = (Employee) bundle.getSerializable(KEY_EDITEMPLOYEE);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.formFragmentContainerView, FormFragment.newInstance(employee)).commit();
        }
    }

    @Override
    public void onReturnEmployee(Employee employee) {
        Cursor cursor = mDatabase.getDataByID(employee.getId());
        if (cursor == null) {
            mDatabase.insertData(employee.getFName(), employee.getLName(), employee.getId(), employee.getStatus(), employee.getHDate());
        } else if (cursor.getCount() <= 0) {
            mDatabase.insertData(employee.getFName(), employee.getLName(), employee.getId(), employee.getStatus(), employee.getHDate());
            cursor.close();
        } else {
            mDatabase.updateByID(employee.getId(), employee.getFName(), employee.getLName(), employee.getStatus(), employee.getHDate());
            cursor.close();
        }

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}