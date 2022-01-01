// Hao Zhong
// AD1 - 202111
// MainActivity.java
package com.fullsail.adv1.zhonghao_ce01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fullsail.adv1.zhonghao_ce01.utilities.APITask;
import com.fullsail.adv1.zhonghao_ce01.utilities.FileUtility;
import com.fullsail.adv1.zhonghao_ce01.utilities.NetworkUtility;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, APITask.DownloadTask {

    private ListView mListView;
    private ArrayList<Post> mPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = (String) adapterView.getItemAtPosition(i);

        if (NetworkUtility.isConnected(this)) {
            APITask task = new APITask(MainActivity.this);
            task.execute(selection);

            displayListView();
            saveRedditLocal(selection);
        } else {
            Toast.makeText(this, R.string.offline_toast, Toast.LENGTH_SHORT).show();
            readLocalFile(selection);
            displayListView();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if (NetworkUtility.isConnected(this)) {
            APITask task = new APITask(MainActivity.this);
            task.execute(getString(R.string.defaultSearch));

            displayListView();
            saveRedditLocal(getString(R.string.defaultSearch));
        } else {
            Toast.makeText(this, R.string.offline_toast, Toast.LENGTH_SHORT).show();
            readLocalFile(getString(R.string.defaultSearch));
            displayListView();
        }
    }

    @Override
    public void onPost(ArrayList<Post> posts) {
        mPosts = posts;
        if (mPosts != null) {
            displayListView();
        }
    }

    private void displayListView() {
        if (mPosts == null) {
            Toast.makeText(this, R.string.noData_toast, Toast.LENGTH_SHORT).show();
        } else {
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mPosts);
            mListView.setAdapter(adapter);
        }
    }

    private void saveRedditLocal(String _fileName) {
        FileUtility.writeObject(this, getString(R.string.folderPath),  _fileName + ".txt", mPosts);
    }

    private void readLocalFile(String _fileName) {
        mPosts = FileUtility.readArrayObject(this, getString(R.string.folderPath), _fileName + ".txt");
    }
}