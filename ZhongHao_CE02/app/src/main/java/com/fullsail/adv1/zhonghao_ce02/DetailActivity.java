// Hao Zhong
// AD1 - 202111
// DetailActivity.java
package com.fullsail.adv1.zhonghao_ce02;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fullsail.adv1.zhonghao_ce02.DataModel.DataBaseHelper;
import com.fullsail.adv1.zhonghao_ce02.DataModel.Employee;

public class DetailActivity extends AppCompatActivity {
    private static final String KEY_VIEWEMPLOYEE = "EXTRA DETAIL EMPLOYEE";
    private static final String KEY_EDITEMPLOYEE = "EXTRA OLD EMPLOYEE";

    private Employee mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView details = findViewById(R.id.textView_details);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            mEmployee = (Employee) bundle.getSerializable(KEY_VIEWEMPLOYEE);
            details.setText(mEmployee.getDetails());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent explicitForm = new Intent(this, FormActivity.class);
            explicitForm.setAction(getString(R.string.intentAction_edit));
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_EDITEMPLOYEE, mEmployee);
            explicitForm.putExtras(bundle);

            mLauncher.launch(explicitForm);
        } else if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(R.string.warning_delete);
            alert.setNegativeButton("Cancel", null);

            alert.setPositiveButton("Yes", (dialog, id) -> {
                DataBaseHelper database = DataBaseHelper.getInstance(this);
                database.deleteByID(mEmployee.getId());

                Toast.makeText(this, R.string.confirmation_deleted, Toast.LENGTH_SHORT).show();
                returnIntent();
            });

            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    final ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    returnIntent();
                }
            }
    );

    private void returnIntent() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}