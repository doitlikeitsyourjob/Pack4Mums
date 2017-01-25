package com.doitlikeitsyourjob.pack4mums;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MainMenuDbAdapter {

    //MAINLIST
    public static final String KEY_LISTID = "_id";
    //public static final String KEY_LISTCODE = "listcode";
    public static final String KEY_LISTNAME = "listname";
    public static final String KEY_FAV = "fav";

    //MAINITEMLIST
    public static final String KEY_ROWID = "_id";
    public static final String KEY_MAINLISTID = "listid";
    public static final String KEY_MAINITEMID = "itemid";
    public static final String KEY_CHECK = "checktick";
    public static final String KEY_DELETE = "deleterow";
    public static final String KEY_STAR = "starred";

    //ITEMLIST
    public static final String KEY_ITEMID = "_id";
    public static final String KEY_ITEMNAME = "itemname";
    public static final String KEY_ITEMBUYLINK = "itembuylink";


    private static final String TAG = "MainMenuDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    //DATABASE
    private static final String DATABASE_NAME = "Pack";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    //TABLES
    private static final String SQLITE_TABLE_MAINLISTS = "MainList";
    private static final String SQLITE_TABLE_MAINITEMLISTS = "MainItemList";
    private static final String SQLITE_TABLE_ITEMLISTS = "ItemList";

    private static final String CREATE_MAINLISTS =
            "CREATE TABLE if not exists " + SQLITE_TABLE_MAINLISTS + " (" +
                    KEY_LISTID + " integer PRIMARY KEY autoincrement," +
            //        KEY_LISTCODE + "," +
                    KEY_LISTNAME + "," +
                    KEY_FAV + "," +
                    " UNIQUE (" + KEY_LISTNAME  +"));"; //+ "," + KEY_LISTCODE

    private static final String CREATE_MAINITEMLISTS =
            "CREATE TABLE if not exists " + SQLITE_TABLE_MAINITEMLISTS + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_MAINLISTID + ", " +
                    KEY_MAINITEMID + ", " +
                    KEY_CHECK + ", " +
                    KEY_DELETE + ", " +
                    KEY_STAR + ", " +
                    " UNIQUE ( " + KEY_MAINLISTID +"," +KEY_MAINITEMID+" ));";

    private static final String CREATE_ITEMLISTS =
            "CREATE TABLE if not exists " + SQLITE_TABLE_ITEMLISTS + " (" +
                    KEY_ITEMID + " integer PRIMARY KEY autoincrement," +
                    KEY_ITEMNAME + "," +
                    KEY_ITEMBUYLINK + "," +
                    " UNIQUE (" + KEY_ITEMNAME +"));";


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create MainList
            Log.w(TAG, CREATE_MAINLISTS);
            db.execSQL(CREATE_MAINLISTS);

            //Create MainItemList
            Log.w(TAG, CREATE_MAINITEMLISTS);
            db.execSQL(CREATE_MAINITEMLISTS);

            //ItemList
            Log.w(TAG, CREATE_ITEMLISTS);
            db.execSQL(CREATE_ITEMLISTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE_MAINLISTS);
            onCreate(db);
        }
    }

    public MainMenuDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public MainMenuDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //MAINLISTS
    public boolean deleteAllMainLists() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE_MAINLISTS, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }
    public long createMainLists(String name, Integer fav) {
        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_LISTCODE, code);
        initialValues.put(KEY_LISTNAME, name);
        initialValues.put(KEY_FAV, fav);

        return mDb.insert(SQLITE_TABLE_MAINLISTS, null, initialValues);
    }

    //MAINITEMLISTS
    public long createMainItemLists(Integer listid, Integer itemid, Integer checktick, Integer deleterow, Integer starred) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MAINLISTID, listid);
        initialValues.put(KEY_MAINITEMID, itemid);
        initialValues.put(KEY_CHECK, checktick);
        initialValues.put(KEY_DELETE, deleterow);
        initialValues.put(KEY_STAR, starred);

        return mDb.insert(SQLITE_TABLE_MAINITEMLISTS, null, initialValues);
    }

    //ITEMLISTS
    public long createItemLists(String itemname, String itembuylink ) {
        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_ITEMCODE, itemcode);
        initialValues.put(KEY_ITEMNAME, itemname);
        initialValues.put(KEY_ITEMBUYLINK, itembuylink);

        return mDb.insert(SQLITE_TABLE_ITEMLISTS, null, initialValues);
    }

    //MAINLISTS
    public void insertMenuLists() {

        createMainLists("Holiday - General",0);
        createMainLists("Holiday - Beach",0);
        createMainLists("Holiday - Winter",0);
        createMainLists("Holiday - Summer",0);
        createMainLists("Holiday - Snow Skiing",0);
        createMainLists("Holiday - Camping",0);
        createMainLists("Visiting Friends1",0);
        createMainLists("Visiting Friends2",0);
        createMainLists("Visiting Friends3",0);
        createMainLists("Day Out1",0);
        createMainLists("Day Out2",0);
        createMainLists("Day Out3",0);
        createMainLists("Day Out4",0);
        createMainLists("Hospital Bag1",0);
        createMainLists("Hospital Bag2",0);
        createMainLists("Hospital Bag3",0);
        createMainLists("Operation1",0);
        createMainLists("Picnic1",0);
        createMainLists("Work1",0);
        createMainLists("Swimming",0);
    }

    //MAINITEMLISTS
    public void insertMenuItemLists() {
        createMainItemLists(1, 1, 1, 0, 0);
        createMainItemLists(1, 2, 0, 0, 0);
        createMainItemLists(1, 3, 1, 0, 0);
        createMainItemLists(1, 4, 0, 0, 0);
        createMainItemLists(1, 5, 1, 0, 0);
        createMainItemLists(2, 1, 0, 0, 0);
        createMainItemLists(3, 1, 0, 0, 0);
    }

    //ITEMLISTS
    public void insertItemLists() {
        createItemLists(mCtx.getString(R.string.item_r1c1),mCtx.getString(R.string.item_r1c2));
        createItemLists(mCtx.getString(R.string.item_r2c1),mCtx.getString(R.string.item_r2c2));
        createItemLists(mCtx.getString(R.string.item_r3c1),mCtx.getString(R.string.item_r3c2));
        createItemLists(mCtx.getString(R.string.item_r4c1),mCtx.getString(R.string.item_r4c2));
        createItemLists(mCtx.getString(R.string.item_r5c1),mCtx.getString(R.string.item_r5c2));
    }

    //SELECTS
    public Cursor fetchMainListsByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE_MAINLISTS, new String[] {KEY_LISTID, //KEY_LISTCODE,
                     KEY_LISTNAME, KEY_FAV},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE_MAINLISTS, new String[] {KEY_LISTID, //KEY_LISTCODE,
                     KEY_LISTNAME, KEY_FAV },
                    KEY_LISTNAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }


    public Cursor fetchItemListsByName(String inputText, Integer listid) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            //mCursor = mDb.query(SQLITE_TABLE_ITEMLISTS, new String[] {KEY_ITEMID, KEY_ITEMNAME, KEY_ITEMBUYLINK}, null, null, null, null, null);
            final String MY_QUERY = "SELECT distinct a._id, a.itemname, (Select count(b.listid) from MainItemList b WHERE a._id = b.itemid AND b.listid = " + listid + ") as [itembuylink] FROM ItemList a  order by 2 asc";
            mCursor =  mDb.rawQuery(MY_QUERY, new String[]{});
        }
        else {
            final String MY_QUERY = "SELECT distinct a._id, a.itemname, (Select count(b.listid) from MainItemList b WHERE a._id = b.itemid AND b.listid = " + listid + ") as [itembuylink] FROM ItemList a WHERE a.itemname LIKE '%" + inputText + "%' order by 2 asc";
            mCursor =  mDb.rawQuery(MY_QUERY, new String[]{});
            //mCursor = mDb.query(true, SQLITE_TABLE_ITEMLISTS, new String[] {KEY_ITEMID, KEY_ITEMNAME, KEY_ITEMBUYLINK}, KEY_ITEMNAME + " like '%" + inputText + "%'", null, null, null, null, null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //ITEM LIST
    public Cursor fetchALLItemsForList(Integer listcode){

        //String stringlistcode =  Integer.toString(listcode);
        final String MY_QUERY = "SELECT distinct a._id, a.itemname, (Select count(b.listid) from MainItemList b WHERE a._id = b.itemid AND b.listid = " + listcode + ") as [itembuylink] FROM ItemList a  order by 2 asc";
        Cursor mCursor =  mDb.rawQuery(MY_QUERY, new String[]{});

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }




    public Cursor fetchAll() {

        Cursor mCursor = mDb.query(SQLITE_TABLE_MAINLISTS, new String[] {KEY_LISTID, //KEY_LISTCODE,
                 KEY_LISTNAME, KEY_FAV},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllFav() {

        Cursor mCursor = mDb.query(SQLITE_TABLE_MAINLISTS, new String[] {KEY_LISTID, //KEY_LISTCODE,
                 KEY_LISTNAME, KEY_FAV},
                KEY_FAV + " =1", null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllMainLists() {

        Cursor mCursor = mDb.query(SQLITE_TABLE_MAINLISTS, new String[] {KEY_LISTID, //KEY_LISTCODE,
                 KEY_LISTNAME, KEY_FAV},
                KEY_FAV + " =0", null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //ITEMS
    public Cursor fetchItemsForList(Integer listcode){

        //String stringlistcode =  Integer.toString(listcode);
        final String MY_QUERY = "SELECT * FROM ItemList a INNER JOIN MainItemList b ON a._id=b.itemid WHERE b.listid=" + listcode + "";
        //a._id, a.itemcode, a.itemname, a.itembuylink, b.checktick, b.starred
        Cursor mCursor =  mDb.rawQuery(MY_QUERY, new String[]{});

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //INSERTS
    public void insertMainList(String name) {
        //ContentValues cv = new ContentValues();
        //mDb.update(SQLITE_TABLE, cv, "_id="+p, null);

        createMainLists(name,1);

        //String strSQL = "INSERT INTO MainList VALUSET fav = 0 WHERE listcode = " + name + "";
        //mDb.execSQL(strSQL);
    }

    public Cursor fetchListName(Integer listid) {

        final String MY_QUERY = "SELECT * from MainList WHERE _id = '" + listid + "'";
        Cursor mCursor =  mDb.rawQuery(MY_QUERY, new String[]{});

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchItemId(String itemname) {

        final String MY_QUERY = "SELECT _id from ItemList WHERE itemname = '" + itemname + "'";
        Cursor mCursor =  mDb.rawQuery(MY_QUERY, new String[]{});

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertNewItemList(String itemname) {
        String strSQL = "INSERT INTO ItemList (itemname, itembuylink) VALUES ('" + itemname + "','<a href=https://www.amazon.co.uk/gp/search?ie=UTF8&camp=1634&creative=6738&index=baby&keywords=" + itemname.replace(" ", "%20") + "&linkCode=ur2&tag=doitlikeitsyo-21>buy</a>')";
        mDb.execSQL(strSQL);
    }

    public void ItemList_AddItem(Integer listid, Integer itemid) {
        String strSQL = "INSERT INTO MainItemList (listid, itemid, checktick, deleterow, starred) VALUES (" + listid + "," + itemid + ",0,0,0)";
        mDb.execSQL(strSQL);
    }

    public void ItemList_RemoveItem(Integer listid, Integer itemid) {
        String strSQL = "DELETE FROM MainItemList WHERE listid = " + listid + " and itemid = " + itemid;
        mDb.execSQL(strSQL);
    }

    //UPDATES
    public void updateFavListFav(Integer code) {
        //ContentValues cv = new ContentValues();
        //mDb.update(SQLITE_TABLE, cv, "_id="+p, null);

        String strSQL = "UPDATE MainList SET fav = 0 WHERE _id = " + String.valueOf(code)+ "";
        mDb.execSQL(strSQL);
    }

    public void updateEditList(Integer code, String listname) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_LISTNAME, listname);
        cv.put(KEY_FAV, 1);
        mDb.update(SQLITE_TABLE_MAINLISTS, cv, "_id="+code, null);

        //String strSQL = "UPDATE MainList SET fav = 0 WHERE _id = " + String.valueOf(code)+ "";
        //mDb.execSQL(strSQL);
    }


    public void updateMainListFav(Integer code) {
        //ContentValues cv = new ContentValues();
        //mDb.update(SQLITE_TABLE, cv, "_id="+p, null);

        String strSQL = "UPDATE MainList SET fav = 1 WHERE _id = " + String.valueOf(code) + "";
        mDb.execSQL(strSQL);
    }

    public void updateItemCheckBox_Check(Integer ListId, Integer ItemId) {

        String strSQL = "UPDATE MainItemList SET checktick = 1 WHERE listId = " + String.valueOf(ListId) + " and itemid = " + String.valueOf(ItemId);
        mDb.execSQL(strSQL);

    }

    public void updateItemCheckBox_Uncheck(Integer ListId, Integer ItemId) {

        String strSQL = "UPDATE MainItemList SET checktick = 0 WHERE listId = " + String.valueOf(ListId) + " and itemid = " + String.valueOf(ItemId);
        mDb.execSQL(strSQL);

    }



    //DELETES
    public void deleteList(Long code) {
        //ContentValues cv = new ContentValues();
        //mDb.update(SQLITE_TABLE, cv, "_id="+p, null);

        String strSQL = "DELETE FROM MainList WHERE _id = " + String.valueOf(code)+ "";
        mDb.execSQL(strSQL);
    }

}

/*Decom
    public Cursor fetchMainList(Integer p) {

        Cursor mCursor = mDb.query(SQLITE_TABLE_MAINLISTS, new String[] {KEY_ROWID,
                        KEY_LISTCODE, KEY_LISTNAME, KEY_FAV},
                KEY_ROWID + " =" + p, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

 */