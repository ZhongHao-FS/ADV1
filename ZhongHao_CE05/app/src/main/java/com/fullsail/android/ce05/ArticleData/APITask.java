// Hao Zhong
// AD1 202111
// APITask.java
package com.fullsail.android.ce05.ArticleData;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APITask extends AsyncTask<Void, Void, Void> {

    final private DownloadTask mDownLoadTask;

    public APITask(DownloadTask _downLoadTask) {
        this.mDownLoadTask = _downLoadTask;
    }

    public interface DownloadTask {
        void onFinished();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String jsonData = "";

        final String webAddress = "https://newsapi.org/v2/top-headlines?country=us&apiKey=234075dc1ad74149be69d0d63cd1bfd5";

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
            return null;
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
            return null;
        }
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray articles = obj.getJSONArray("articles");
            ArticleDatabase aDB = ArticleDatabase.getInstance((Context) mDownLoadTask);

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String urlToImage = article.getString("urlToImage");
                String content = article.getString("content");

                aDB.insertArticle(title, urlToImage, content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        mDownLoadTask.onFinished();
    }
}
