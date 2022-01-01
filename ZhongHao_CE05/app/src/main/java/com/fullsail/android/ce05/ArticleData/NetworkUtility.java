// Hao Zhong
// AD1 - 202111
// NetworkUtility.java
package com.fullsail.android.ce05.ArticleData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {
    public static boolean isConnected(Context _context){
        ConnectivityManager mgr = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if(info != null){
                return info.isConnected();
            }
        }
        return false;
    }

}
