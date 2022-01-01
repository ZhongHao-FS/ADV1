// Hao Zhong
// AD1 - 202111
// APITask.java
package com.fullsail.adv1.zhonghao_ce01.utilities;

import android.os.AsyncTask;

import com.fullsail.adv1.zhonghao_ce01.Post;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class APITask extends AsyncTask<String, Void, ArrayList<Post>> {

    private final DownloadTask mDownLoadTask;

    public APITask(DownloadTask mDownLoadTask) {
        this.mDownLoadTask = mDownLoadTask;
    }

    public interface DownloadTask {
        void onPost(ArrayList<Post> posts);
    }

    @Override
    protected ArrayList<Post> doInBackground(String... strings) {
        String jsonData = "";
        ArrayList<Post> posts = new ArrayList<>();

        final String webAddress = String.format("https://reddit.com/r/%s/hot.json", strings[0]);
        HttpsURLConnection connection;

        try {
            // Create new URL
            URL url = new URL(webAddress);
            // Open connection
            connection = (HttpsURLConnection)url.openConnection();
            // Perform connection operation
            connection.connect();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

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
            JSONObject data1 = obj.getJSONObject("data");
            JSONArray children = data1.getJSONArray("children");

            for (int i = 0; i < children.length(); i++) {
                JSONObject child = children.getJSONObject(i);
                JSONObject data2 = child.getJSONObject("data");
                String title = data2.getString("title");
                String numComments = data2.getString("num_comments");

                posts.add(new Post(title, numComments));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }

    @Override
    protected void onPostExecute(ArrayList<Post> postList) {
        mDownLoadTask.onPost(postList);
    }
}
