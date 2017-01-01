package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainMenuListViewCursorAdaptorActivity extends Activity {

    private MainMenuDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapterFav;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //initialiseDBList();

        dbHelper = new MainMenuDbAdapter(this);
        dbHelper.open();

        //If DB Has 0 rows in main list, Initialise DB
        Cursor CursorCnt = dbHelper.fetchAll();
        Integer mCount = CursorCnt.getCount();

        if(mCount==0){
            initialiseDBLists();
        }

       //dbHelper.close();

        //mCount.moveToFirst();
        //int count =  mCount.getInt(); //.getInt(0);
        //mCount.close();
        //if (dbHelper == null) {

        //}

        //Generate ListView from SQLite Database
        displayListViewFav();
        displayListView();
        setupUIEvents();
        //dbHelper.close();

    }

    private void initialiseDBLists() {
        //Remove All Previous Data
        dbHelper.deleteAllMainLists();
        //Insert All Lists
        dbHelper.insertMenuLists();
        dbHelper.insertMenuItemLists();
        dbHelper.insertItemLists();
    }
        //Cursor mCount = dh.rawQuery("select count(*) from tblList", null);
        //mCount.moveToFirst();
        //int count = mCount.getInt(0);
        //mCount.close();

        //if (count == 0) {

    private void displayListViewFav() {

        Cursor cursor = dbHelper.fetchAllFav();

        //If nothing in FavList, Hide Label and Button
        Integer mCount = cursor.getCount();
        if(mCount==0){
            Button btn = (Button) findViewById(R.id.btnFavShowHide);
            btn.setVisibility(View.GONE);

            TextView txt = (TextView) findViewById(R.id.txtFav);
            txt.setVisibility(View.GONE);
        }
        else
        {
            Button btn = (Button) findViewById(R.id.btnFavShowHide);
            btn.setVisibility(View.VISIBLE);

            TextView txt = (TextView) findViewById(R.id.txtFav);
            txt.setVisibility(View.VISIBLE);
        }

        // The desired columns to be bound
        String[] columns = new String[] {
                MainMenuDbAdapter.KEY_LISTID,
                MainMenuDbAdapter.KEY_LISTNAME,
                MainMenuDbAdapter.KEY_FAV
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.code,
                R.id.btnMenuFavList,
                R.id.btnFavShowHide
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapterFav = new SimpleCursorAdapter(
                this, R.layout.menu_fav_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listViewFav);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapterFav);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {

                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                // Get the cursor, positioned to the corresponding row in the result set
                String code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getApplicationContext(), PackListViewCursorAdaptorActivity.class);
                //startActivity(intent);

            }
        });

    }

    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllMainLists();

        // The desired columns to be bound
        String[] columns = new String[] {
                MainMenuDbAdapter.KEY_LISTID, //KEY_LISTCODE
                MainMenuDbAdapter.KEY_LISTNAME,
                MainMenuDbAdapter.KEY_FAV
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.code,
                R.id.btnMenuList,
                R.id.btnFavShowHide
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.menu_all_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listViewMain);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //listView.setOnItemClickListener(new OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView<?> listView, View view,
            //                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                //Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                //String info = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                //Toast toast = Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT);
                //toast.show();

                // Get the state's capital from this row in the database.
                //String countryCode =
                //        cursor.getString(cursor.getColumnIndexOrThrow("code"));
                //Toast.makeText(getApplicationContext(),
                //        countryCode, Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(getApplicationContext(), PackListViewCursorAdaptorActivity.class);
                //startActivity(intent);

        //    }
        //});

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchMainListsByName(constraint.toString());
            }
        });

    }

    void setupUIEvents() {

        Button btnCreateNewList = (Button) findViewById(R.id.btnCreateNewList);
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handleBtnCreateNewListClick();
        }
        });

        Button thebtnFavShowhide = (Button) findViewById(R.id.btnFavShowHide);
        thebtnFavShowhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnFavShowHideClick();
            }
        });

        Button thebtnAllListShowhide = (Button) findViewById(R.id.btnAllListShowHide);
        thebtnAllListShowhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnAllListShowHideClick();
            }
        });
}

    //Create new List
    void handleBtnCreateNewListClick() {
        Intent intent = new Intent(this, MenuCreateNewList.class);
        startActivity(intent);
    }

    //Show/Hide Fav and All Lists
    void handleBtnFavShowHideClick() {

        if (findViewById(R.id.listViewFav).getVisibility() == View.VISIBLE)
        {
            ListView ListViewFav = (ListView) findViewById(R.id.listViewFav);
            ListViewFav.setVisibility(View.GONE);

            Button btnFavShow = (Button) findViewById(R.id.btnFavShowHide);
            btnFavShow.setText("v");
            //btnFavShow.setBackgroundResource(R.drawable.show50);

        }
        else
        {
            ListView ListViewFav = (ListView) findViewById(R.id.listViewFav);
            ListViewFav.setVisibility(View.VISIBLE);

            Button btnFavShow = (Button) findViewById(R.id.btnFavShowHide);
            btnFavShow.setText("ʌ");
            //btnFavShow.setBackgroundResource(R.drawable.hide50);
        }

        //String info = String (ListViewFav.getVisibility());
        //Toast toast = Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT)
        //toast.show();
        //interstitial.show();
    }

    void handleBtnAllListShowHideClick() {

        if (findViewById(R.id.listViewMain).getVisibility() == View.VISIBLE)
        {
            ListView ListViewAll = (ListView) findViewById(R.id.listViewMain);
            ListViewAll.setVisibility(View.GONE);

            EditText filter = (EditText) findViewById(R.id.myFilter);
            filter.setVisibility(View.GONE);

            Button btnAllListShow = (Button) findViewById(R.id.btnAllListShowHide);
            //btnAllListShow.setBackgroundResource(R.drawable.show50);
            btnAllListShow.setText("v");

        }
        else
        {
            ListView ListViewAll = (ListView) findViewById(R.id.listViewMain);
            ListViewAll.setVisibility(View.VISIBLE);

            EditText filter = (EditText) findViewById(R.id.myFilter);
            filter.setVisibility(View.VISIBLE);

            Button btnAllListShow = (Button) findViewById(R.id.btnAllListShowHide);
           // btnAllListShow.setBackgroundResource(R.drawable.hide50);
            btnAllListShow.setText("ʌ");
        }
    }

    //Fav/UnFav -> Moves List between Fav and All Lists
    public void clickHandlerFavListAllFavToggle(View v) {
        ListView lv = (ListView) findViewById(R.id.listViewFav);
        int position = lv.getPositionForView(v);
        //String info = String.valueOf(position);

        Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        Integer ListCode = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));  //listcode
        //String ListCode = cursor.getString(cursor.getColumnIndexOrThrow("listcode"));

        //Cursor cursor = dbHelper.fetchMainList(position);
        //String Code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
        //Toast toast = Toast.makeText(getApplicationContext(), ListCode, Toast.LENGTH_SHORT);
        //toast.show();

        dbHelper.updateFavListFav(ListCode);

        displayListViewFav();
        displayListView();

    }

    public void clickHandlerMainListAllFavToggle(View v) {
        ListView lv = (ListView) findViewById(R.id.listViewMain);
        int position = lv.getPositionForView(v);
        //String info = String.valueOf(position);

        Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        //String ListCode = cursor.getString(cursor.getColumnIndexOrThrow("listcode"));
        Integer ListCode = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); //listcode

        //Toast toast = Toast.makeText(getApplicationContext(), ListCode, Toast.LENGTH_SHORT);
        //toast.show();

        dbHelper.updateMainListFav(ListCode);

        displayListViewFav();
        displayListView();
    }

    //Buttons in ListView --> ClickThrough to List Details
    public void clickHandlerFavListAllButton(View v) {
        ListView lv = (ListView) findViewById(R.id.listViewFav);
        int position = lv.getPositionForView(v);
        //String info = String.valueOf(position);
        //Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
        //toast.show();

        Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        String ListCode = cursor.getString(cursor.getColumnIndexOrThrow("_id")); //listcode

        //Intent intent = new Intent(getBaseContext(), PackListViewCursorAdaptorActivity.class);

        Intent intent = new Intent(getApplicationContext(), ItemListViewCursorAdaptorActivity.class);
        intent.putExtra("LIST_CODE", ListCode);
        startActivity(intent);

    }

    public void clickHandlerMainListAllButton(View v) {
        ListView lv = (ListView) findViewById(R.id.listViewMain);
        int position = lv.getPositionForView(v);

        String info = String.valueOf(position);
        Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
        toast.show();
    }

    //String info = (String) theButton.getText();

        //Toast toast = Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT);
        //toast.show();
    //}

    // void handleBtnlistViewFav(){
   //     String info = "Testing";
   //     Toast toast = Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT);
   //     toast.show();
   //}


}