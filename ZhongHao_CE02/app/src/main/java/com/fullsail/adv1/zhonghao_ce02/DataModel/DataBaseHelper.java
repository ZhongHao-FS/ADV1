// Hao Zhong
// AD1 - 202111
// DataBaseHelper.java
package com.fullsail.adv1.zhonghao_ce02.DataModel;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "EmployeeDatabase";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_HIREDATE = "hire_date";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_FIRSTNAME + " TEXT, " +
            COLUMN_LASTNAME + " TEXT, " +
            COLUMN_STATUS + " TEXT, " +
            COLUMN_HIREDATE + " TEXT)";

    private static final String DATABASE_FILE = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PREFERENCE_DATEFORMAT_KEY = "date_format_preference";
    private static final String DEFAULT_DATEFORMAT = "MM/dd/yyyy";
    private final SQLiteDatabase database;
    private final String mDateFormat;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);

        database = getWritableDatabase();

        SharedPreferences dPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mDateFormat = dPrefs.getString(PREFERENCE_DATEFORMAT_KEY, DEFAULT_DATEFORMAT);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    private static DataBaseHelper mInstance = null;

    public static DataBaseHelper getInstance(Context _context) {
        if(mInstance == null) {
            mInstance = new DataBaseHelper(_context);
        }
        return mInstance;
    }

    public void insertData(String _fName, String _lName, int _id, String _status, Date _date) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, _fName);
        cv.put(COLUMN_LASTNAME, _lName);
        cv.put(COLUMN_ID, _id);
        cv.put(COLUMN_STATUS, _status);
        cv.put(COLUMN_HIREDATE, String.valueOf(DateFormat.format(mDateFormat, _date)));

        database.insert(TABLE_NAME, null, cv);
    }

    @Nullable
    public Cursor getDataByID(int _id) {
        String selection = COLUMN_ID + " = " + _id;
        return database.query(TABLE_NAME, null, selection, null, null, null, null);
    }

    public Cursor getSortedData(String _columnName, boolean _asc) {
        String orderBy;
        if (_asc) {
            orderBy = _columnName + " ASC";
        } else {
            orderBy = _columnName + " DESC";
        }

        return database.query(TABLE_NAME, null, null, null, null, null, orderBy);
    }

    public void updateByID(int _id, String _fName, String _lName, String _status, Date _date) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, _fName);
        cv.put(COLUMN_LASTNAME, _lName);
        cv.put(COLUMN_STATUS, _status);
        cv.put(COLUMN_HIREDATE, String.valueOf(DateFormat.format(mDateFormat, _date)));

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(_id) };

        database.update(TABLE_NAME, cv, selection, selectionArgs);
    }

    public void deleteByID(int _id) {
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(_id) };

        database.delete(TABLE_NAME, selection, selectionArgs);
    }

    public void deleteAll() { database.delete(TABLE_NAME, null, null); }
}
