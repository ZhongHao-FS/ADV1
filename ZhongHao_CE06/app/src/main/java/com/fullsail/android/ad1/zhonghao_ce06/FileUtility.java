// Hao Zhong
// AD1 - 202111
// FileUtility.java
package com.fullsail.android.ad1.zhonghao_ce06;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileUtility {

    private static final String TAG = "FileUtility log:";
    private static final String IMAGE_FOLDER = "images";

    protected static File getFileObject(Context _context, String _fileName) {
        File protectedStorage = _context.getExternalFilesDir(IMAGE_FOLDER);

        return new File(protectedStorage, _fileName);
    }

    public static void writeObject(Context _context, String _fileName, Serializable _obj) {
        File file = getFileObject(_context, _fileName);
        File protectedStorage = _context.getExternalFilesDir(IMAGE_FOLDER);

        if(!file.exists()) {
            try {
                boolean mkd = protectedStorage.mkdirs();
                Log.i(TAG, "Directory is made: " + mkd);

                boolean create = file.createNewFile();
                Log.i(TAG, "File is created: " + create);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(_obj);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readByteArray(Context _context, String _fileName) {
        byte[] blob = null;
        File file = getFileObject(_context, _fileName);

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            if (obj != null) {
                blob = (byte[]) obj;
            }
            ois.close();
        } catch (ClassNotFoundException | IOException | ClassCastException e) {
            e.printStackTrace();
        }
        return blob;
    }
}
