// Hao Zhong
// AD1 202111
// DataProvider.java
package com.fullsail.android.ce05;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fullsail.android.ce05.ArticleData.ArticleContract;
import com.fullsail.android.ce05.ArticleData.ArticleDatabase;

public class DataProvider extends ContentProvider {

    private static final int TABLE_MATCH = 10;

    ArticleDatabase mDatabase;
    UriMatcher mMatcher;

    public DataProvider() {
    }

    @Override
    public boolean onCreate() {
        mDatabase = ArticleDatabase.getInstance(getContext());
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(ArticleContract.URI_AUTHORITY, ArticleContract.DATA_TABLE, TABLE_MATCH);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriType = mMatcher.match(uri);
        if(uriType == TABLE_MATCH) {
            return mDatabase.query(projection,selection,selectionArgs,sortOrder);
        }
        throw new IllegalArgumentException("Uri did not match... check your authority or table name.");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int uriType = mMatcher.match(uri);
        if(uriType == TABLE_MATCH) {
            return "vnd.android.cursor.dir/vnd." + ArticleContract.URI_AUTHORITY + "." + ArticleContract.DATA_TABLE;
        }
        throw new IllegalArgumentException("Uri did not match... check your authority or table name.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
