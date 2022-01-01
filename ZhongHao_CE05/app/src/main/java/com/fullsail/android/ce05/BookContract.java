// Hao Zhong
// AD1 202111
// BookContract.java
package com.fullsail.android.ce05;

import android.net.Uri;

public class BookContract {

    public static final String DATA_TABLE = "books";

    //Table columns
    public static final String TITLE = "title";
    public static final String THUMBNAIL = "thumbnail";
    public static final String DESCRIPTION = "description";

    //URI
    public static final String URI_AUTHORITY = "com.fullsail.ce05.provider";
    public static final String CONTENT_URI_STRING = "content://" + URI_AUTHORITY + "/";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING + DATA_TABLE);
}
