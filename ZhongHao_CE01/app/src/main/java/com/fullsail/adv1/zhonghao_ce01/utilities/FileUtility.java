// Hao Zhong
// AD1 - 202111
// FileUtility.java
package com.fullsail.adv1.zhonghao_ce01.utilities;

import android.content.Context;
import android.util.Log;

import com.fullsail.adv1.zhonghao_ce01.Post;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class FileUtility {

    private static final String TAG = "FileUtility log:";
    public static File getFileObject(Context _context, String _folderPath, String _fileName) {
        File folderPath;
        File storageLocation = _context.getFilesDir();
        folderPath = new File(storageLocation, _folderPath);

        File file = new File(folderPath, _fileName);

        if(!file.exists()) {
            try {
                boolean mkd = folderPath.mkdirs();
                Log.i(TAG, "Directory is made: " + mkd);

                boolean create = file.createNewFile();
                Log.i(TAG, "File is created: " + create);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static void writeObject(Context _context, String _folderPath, String _fileName, Serializable _obj) {

        File file = getFileObject(_context, _folderPath, _fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(_obj);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Post> readArrayObject(Context _context, String _folderPath, String _fileName) {
        ArrayList<Post> arrayList = new ArrayList<>();
        File file = getFileObject(_context, _folderPath, _fileName);

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            if (obj != null) {
                arrayList = (ArrayList<Post>) obj;
            }
            ois.close();
        } catch (ClassNotFoundException | IOException | ClassCastException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
