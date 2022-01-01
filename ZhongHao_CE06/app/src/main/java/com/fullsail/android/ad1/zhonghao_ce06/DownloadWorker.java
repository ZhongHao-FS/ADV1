// Hao Zhong
// AD1 - 202111
// DownloadWorker.java
package com.fullsail.android.ad1.zhonghao_ce06;

import static com.fullsail.android.ad1.zhonghao_ce06.MainActivity.IMAGES;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadWorker extends Worker {

    private final Context mContext;

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        final String URL_BASE = "https://i.imgur.com/";
        // Introduced this variable so that once all the images are downloaded, then if you click the menu button, the grid UI is not going to be broadcast and refreshed 30 times.
        // It will only be refreshed 1 time if everything is already downloaded to save system memory.
        boolean skipRefresh = true;

        for (String image : IMAGES) {
            File imageFile = FileUtility.getFileObject(mContext, image);
            if (imageFile.exists() && !skipRefresh) {
                Intent intent = new Intent(ImageGridFragment.BROADCAST_ACTION);
                mContext.sendBroadcast(intent);

                skipRefresh = true;
            } else {
                final String webAddress = URL_BASE + image;
                byte[] data;

                try {
                    // Create new URL
                    URL url = new URL(webAddress);
                    // Open connection
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    // Perform connection operation
                    connection.connect();

                    data = IOUtils.toByteArray(url);

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                if (data == null) {
                    continue;
                }

                FileUtility.writeObject(mContext, image, data);
                skipRefresh = false;
            }
        }
        Intent intent = new Intent(ImageGridFragment.BROADCAST_ACTION);
        mContext.sendBroadcast(intent);

        return Result.success();
    }
}
