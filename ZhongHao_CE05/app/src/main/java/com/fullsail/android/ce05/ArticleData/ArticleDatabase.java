// Hao Zhong
// AD1 202111
// ArticleDatabase.java
package com.fullsail.android.ce05.ArticleData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ArticleDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ArticleContract.DATA_TABLE + " (" +
            ArticleContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ArticleContract.TITLE + " TEXT, " +
            ArticleContract.THUMBNAIL + " TEXT, " +
            ArticleContract.BODY + " TEXT)";

    private final SQLiteDatabase mDB;
    static private ArticleDatabase mInstance = null;

    static public ArticleDatabase getInstance(Context context) {
        if(mInstance==null) {
            mInstance = new ArticleDatabase(context);
        }
        return mInstance;
    }

    public ArticleDatabase(@Nullable Context context) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);

        mDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void insertArticle(String _title, String _thumbnail, String _body){
        ContentValues cv = new ContentValues();
        cv.put(ArticleContract.TITLE, _title);
        cv.put(ArticleContract.THUMBNAIL, _thumbnail);
        cv.put(ArticleContract.BODY, _body);
        mDB.insert(ArticleContract.DATA_TABLE, null, cv);
    }

    public Cursor query(String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){
        return mDB.query(ArticleContract.DATA_TABLE,
                projection,selection,selectionArgs,null,null,sortOrder);
    }
}
