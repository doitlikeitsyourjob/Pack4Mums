package com.doitlikeitsyourjob.pack4mums;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PackListDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_CODE = "code";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LINK = "link";

    private static final String TAG = "PackListDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "PackList";
    private static final String SQLITE_TABLE = "Lists";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CODE + "," +
                    KEY_NAME + "," +
                    KEY_DESCRIPTION + "," +
                    KEY_LINK + "," +
                    " UNIQUE (" + KEY_CODE +"));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public PackListDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public PackListDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createListItems(String code, String name,
                                String description, String link) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CODE, code);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_LINK, link);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllListItems() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchListItemsByCode(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CODE, KEY_NAME, KEY_DESCRIPTION, KEY_LINK},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CODE, KEY_NAME, KEY_DESCRIPTION, KEY_LINK},
                    KEY_CODE + "= '" + inputText + "'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllListItems() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_CODE, KEY_NAME, KEY_DESCRIPTION, KEY_LINK},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeLists() {
        createListItems("Item1","https://www.amazon.co.uk/gp/search?ie=UTF8&camp=1634&creative=6738&index=baby&keywords=Sleeping%20Bag&linkCode=ur2&tag=doitlikeitsyo-21","Description1","");
        createListItems("Item2","Item2Name","Description2","");
        createListItems("Item3","Item3Name","Description3","");
        createListItems("Item4","Item4Name","Description4","");
        createListItems("Item5","Item5Name","Description5","");
        createListItems("Item6","Item6Name","Description6","");
    }

}