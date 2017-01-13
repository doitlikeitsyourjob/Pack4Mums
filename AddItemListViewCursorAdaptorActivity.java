package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddItemListViewCursorAdaptorActivity extends Activity {

    private MainMenuDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    //private ItemListView_CustomAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlist);
        String s = getIntent().getStringExtra("LIST_CODE");

        if (s !=null) {
            int i = Integer.parseInt(s);
            dbHelper = new MainMenuDbAdapter(this);
            dbHelper.open();

            //Generate ListView from SQLite Database
            displayListView(i);
        }

    }

     /*void setupUIEvents() {

          Button btnAddItems = (Button) findViewById(R.id.btnAddItems);
         btnAddItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleBtnAddItemsClick();
                }
            });

         Button btnCreateItems = (Button) findViewById(R.id.btnCreateItems);
         btnCreateItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleBtnCreateItemsClick();
                }
            });

        }

    void handleBtnAddItemsClick() {
         Intent intent = new Intent(this, MenuCreateNewList.class);
         startActivity(intent);
    }

    void handleBtnCreateItemsClick() {
        Intent intent = new Intent(this, MenuCreateNewList.class);
        startActivity(intent);
    }*/
    
    private void displayListView(Integer i) {

        Cursor cursor = dbHelper.fetchALLItemsForList(i);

        // The desired columns to be bound
        String[] columns = new String[] {
                MainMenuDbAdapter.KEY_ITEMNAME,
                MainMenuDbAdapter.KEY_ITEMBUYLINK,
                MainMenuDbAdapter.KEY_ITEMID
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.txtitemaddname,
                R.id.txtitemaddbuylink,
                R.id.btnAddItem,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list_additem,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.lvItemList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


    }



}