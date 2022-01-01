// Hao Zhong
// JAV2 - 202111
// MainActivity.java
package com.fullsail.adv1.zhonghao_ce03_b;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpeg");
        mPickLauncher.launch(intent);

        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> mPickLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();

                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .add(R.id.fragmentContainerView, ImageFragment.newInstance(imageUri), null).commit();
                }
            }
    );
}