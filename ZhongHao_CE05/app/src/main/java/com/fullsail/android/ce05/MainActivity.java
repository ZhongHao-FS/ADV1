// Hao Zhong
// AD1 202111
// MainActivity.java
package com.fullsail.android.ce05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fullsail.android.ce05.ArticleData.APITask;
import com.fullsail.android.ce05.ArticleData.NetworkUtility;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements APITask.DownloadTask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(BookContract.CONTENT_URI, null, null, null, null);
        ListView listView = findViewById(R.id.list);
        if (cursor != null) {
            listView.setAdapter(new DataListAdapter(cursor));
        }

        if (NetworkUtility.isConnected(this)) {
            APITask loadArticles = new APITask(MainActivity.this);
            loadArticles.execute();
        } else {
            Toast.makeText(this, R.string.offline_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinished() {
        Toast.makeText(this, R.string.download_finish_message, Toast.LENGTH_SHORT).show();
    }

    private class DataListAdapter extends ResourceCursorAdapter {

        private DataListAdapter(Cursor c) {
            super(MainActivity.this, R.layout.listview_item, c, 0);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int titleClmIdx = cursor.getColumnIndex(BookContract.TITLE);
            String title = cursor.getString(titleClmIdx);
            TextView tv_bookName = view.findViewById(R.id.textView_bookName);
            tv_bookName.setText(title);

            int thumbnailClmIdx = cursor.getColumnIndex(BookContract.THUMBNAIL);
            String thumbnailLink = cursor.getString(thumbnailClmIdx);
            ImageView iv_thumbnail = view.findViewById(R.id.imageView);
            Picasso.get().load(thumbnailLink).placeholder(R.drawable.ic_launcher_foreground).fit().centerInside().into(iv_thumbnail);

            view.setOnClickListener(view1 -> {
                int desClmIdx = cursor.getColumnIndex(BookContract.DESCRIPTION);
                String desc = cursor.getString(desClmIdx);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(title);
                dialog.setMessage(desc);
                dialog.setNeutralButton("Ok", null);

                dialog.show();
            });

        }
    }
}