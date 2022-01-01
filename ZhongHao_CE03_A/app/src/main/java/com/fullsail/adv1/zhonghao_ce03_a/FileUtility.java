// Hao Zhong
// JAV2 - 202111
// FileUtility.java
package com.fullsail.adv1.zhonghao_ce03_a;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class FileUtility {

    private static final String TAG = "New image file created: ";
    private static final String IMAGE_FOLDER = "photos";

    protected static File createImageFile(String _fileName, Context _context) {
        File imageFile = getImageFileReference(_fileName, _context);

        if (!imageFile.exists()) {
            try {
                boolean fileCreated = imageFile.createNewFile();
                Log.i(TAG, String.valueOf(fileCreated));
            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return imageFile;
    }

    protected static File getImageFileReference(String _fileName, Context _context) {
        File protectedStorage = _context.getExternalFilesDir(IMAGE_FOLDER);
        return new File(protectedStorage, _fileName);
    }
}
