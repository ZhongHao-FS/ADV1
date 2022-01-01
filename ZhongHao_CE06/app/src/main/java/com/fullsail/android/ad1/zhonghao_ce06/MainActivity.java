// Hao Zhong
// AD1 - 202111
// MainActivity.java
package com.fullsail.android.ad1.zhonghao_ce06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity implements ImageGridFragment.ImageClickListener {

    private static final String AUTHORITY = "com.fullsail.android.ad1.zhonghao_ce06";
    public static final String[] IMAGES = {
            "Df9sV7x.jpg", "nqnegVs.jpg", "JDCG1tP.jpg", "tUvlwvB.jpg", "2bTEbC5.jpg", "Jnqn9NJ.jpg",
            "xd2M3FF.jpg", "atWe0me.jpg", "UJROzhm.jpg", "4lEPonM.jpg", "vxvaFmR.jpg", "NDPbJfV.jpg",
            "ZPdoCbQ.jpg", "SX6hzar.jpg", "YDNldPb.jpg", "iy1FvVh.jpg", "OFKPzpB.jpg", "P0RMPwI.jpg",
            "lKrCKtM.jpg", "eXvZwlw.jpg", "zFQ6TwY.jpg", "mTY6vrd.jpg", "QocIraL.jpg", "VYZGZnk.jpg",
            "RVzjXTW.jpg", "1CUQgRO.jpg", "GSZbb2d.jpg", "IvMKTro.jpg", "oGzBLC0.jpg", "55VipC6.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, ImageGridFragment.newInstance(), null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class).setConstraints(constraints).build();

        WorkManager.getInstance(this).enqueue(workRequest);

        return true;
    }

    @Override
    public void onImageClicked(int index) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        File fileReference = FileUtility.getFileObject(this, IMAGES[index]);
        if (!fileReference.exists()) {
            return;
        }
        Uri imageUri = FileProvider.getUriForFile(this, AUTHORITY, fileReference);

        viewIntent.setDataAndType(imageUri, "image/jpeg");
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        this.startActivity(viewIntent);
    }
}