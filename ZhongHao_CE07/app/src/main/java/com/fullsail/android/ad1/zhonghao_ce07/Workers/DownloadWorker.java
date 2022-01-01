// Hao Zhong
// AD1 - 202111
// DownloadWorker.java
package com.fullsail.android.ad1.zhonghao_ce07.Workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.fullsail.android.ad1.zhonghao_ce07.Utilities.ArticleDatabase;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class DownloadWorker extends Worker {

    public static final String OUTPUT_KEY = "output key";
    private final Context mContext;

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String jsonData = "";

        final String webAddress = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=bc2a850308044d81bb83069feded75cb";

        // HTTP URL connection reference.
        HttpURLConnection connection;

        try {
            // Create new URL
            URL url = new URL(webAddress);
            // Open connection
            connection = (HttpURLConnection)url.openConnection();
            // Perform connection operation
            connection.connect();
        }
        catch(Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // Input stream reference
        InputStream is = null;
        try {
            // Error check connection
            // Get the stream
            is = connection.getInputStream();
            // Convert the stream to a string (think about out utils lib)
            jsonData = IOUtils.toString(is, StandardCharsets.UTF_8);
        }
        catch(Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        finally {
            // If we have a stream, try to close it.
            try{
                assert is != null;
                is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            // If we have a connection disconnect it
            connection.disconnect();
        }

        if (jsonData.equals("")) {
            return Result.failure();
        }

        String title = "";
        String description = "";
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray articles = obj.getJSONArray("articles");
            ArticleDatabase aDB = ArticleDatabase.getInstance(mContext);

            Random rand = new Random();
            int indexRand = rand.nextInt(articles.length());
            JSONObject article = articles.getJSONObject(indexRand);

            title = article.getString("title");
            String urlToImage = article.getString("urlToImage");
            description = article.getString("description");

            aDB.insertArticle(title, urlToImage, description);
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.failure();
        }

        Data outputData = new Data.Builder().putStringArray(OUTPUT_KEY, new String[]{title, description}).build();
        return Result.success(outputData);
    }
}
