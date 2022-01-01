// Hao Zhong
// AD1 - 202111
// MainActivity.java
package com.fullsail.android.ad1.zhonghao_ce07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.fullsail.android.ad1.zhonghao_ce07.Workers.DownloadWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_CHANNEL = 0x10000001;
    private static final String CHANNEL_ID_CE07 = "com.fullsail.android.ad1.zhonghao_ce07";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest downloadRequest = new PeriodicWorkRequest.Builder(
                DownloadWorker.class,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                TimeUnit.MILLISECONDS).setConstraints(constraints).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("Unique Name", ExistingPeriodicWorkPolicy.KEEP, downloadRequest);
        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData("Unique Name")
                .observe(this, workInfos -> {
                    WorkInfo.State state = workInfos.get(workInfos.size()-1).getState();
                    if (state == WorkInfo.State.SUCCEEDED) {
                        onExpandedNotification();
                    }
                });
    }

    private void onExpandedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_CE07);

    }
}