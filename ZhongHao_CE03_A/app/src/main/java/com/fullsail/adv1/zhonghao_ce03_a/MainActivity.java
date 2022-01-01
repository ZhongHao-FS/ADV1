// Hao Zhong
// JAV2 - 202111
// MainActivity.java
package com.fullsail.adv1.zhonghao_ce03_a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PhotoGridFragment.PhotoClickListener {

    private final ArrayList<Uri> mPhotoURIList = new ArrayList<>();
    private static final String IMAGE_PREFIX = "myShot";
    private static String IMAGE_EXTENSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMAGE_EXTENSION = (mPhotoURIList.size() + 1) + ".jpg";

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, PhotoGridFragment.newInstance(mPhotoURIList), null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.camera) {

            File imageFileReference = FileUtility.createImageFile(IMAGE_PREFIX + IMAGE_EXTENSION, this);
            if (imageFileReference != null) {
                Uri imageURI = FileProvider.getUriForFile(this, "com.fullsail.adv1.zhonghao_ce03_a", imageFileReference);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                ActivityCompat.startActivityForResult(this,
                        intent, 0, null);
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File imageFileReference = FileUtility.getImageFileReference(IMAGE_PREFIX + IMAGE_EXTENSION, this);
        Uri imageURI = FileProvider.getUriForFile(this, "com.fullsail.adv1.zhonghao_ce03_a", imageFileReference);

        mPhotoURIList.add(imageURI);
        updatePhotoGrid();
    }

    private void updatePhotoGrid() {
        IMAGE_EXTENSION = (mPhotoURIList.size() + 1) + ".jpg";

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, PhotoGridFragment.newInstance(mPhotoURIList), null).commit();
    }

    @Override
    public void onPhotoClicked(int index) {
        if (getIntent() != null && getIntent().getAction().equals(Intent.ACTION_PICK)) {
            Intent resultIntent = new Intent();
            resultIntent.setDataAndType(mPhotoURIList.get(index), "image/jpeg");
            resultIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            setResult(RESULT_OK, resultIntent);

            finish();
        } else {
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            viewIntent.setDataAndType(mPhotoURIList.get(index), "image/jpeg");
            viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            this.startActivity(viewIntent);
        }
    }
}