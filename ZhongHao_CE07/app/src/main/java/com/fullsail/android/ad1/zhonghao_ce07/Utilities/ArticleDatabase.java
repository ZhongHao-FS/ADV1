// Hao Zhong
// AD1 202111
// ArticleDatabase.java
package com.fullsail.android.ad1.zhonghao_ce07.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ArticleDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "articledb.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATA_TABLE = "articles";
    //Table columns
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String THUMBNAIL = "thumbnail";
    public static final String BODY = "body";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            DATA_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT, " +
            THUMBNAIL + " TEXT, " +
            BODY + " TEXT)";

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
        cv.put(TITLE, _title);
        cv.put(THUMBNAIL, _thumbnail);
        cv.put(BODY, _body);
        mDB.insert(DATA_TABLE, null, cv);
    }

    public Cursor query(String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){
        return mDB.query(DATA_TABLE,
                projection,selection,selectionArgs,null,null,sortOrder);
    }
}
