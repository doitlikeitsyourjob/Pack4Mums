package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddItemListViewCursorAdaptorActivity extends Activity {

    private MainMenuDbAdapter dbHelper;
    private AddItemView_CustomAdapter dataAdapter;
    //private ItemListView_CustomAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlist);
        Integer listid = getIntent().getIntExtra("LIST_ID",0);

        if (listid != 0) {
            //int i = Integer.parseInt(s);
            dbHelper = new MainMenuDbAdapter(this);

            //Generate ListView from SQLite Database
            displayListView(listid);
        }

    }
    
    private void displayListView(final Integer listid) {

        dbHelper.close();
        dbHelper.open();
        Cursor cursor = dbHelper.fetchALLItemsForList(listid);

        // The desired columns to be bound
        String[] columns = new String[] {
                MainMenuDbAdapter.KEY_ITEMID,
                MainMenuDbAdapter.KEY_ITEMNAME,
                MainMenuDbAdapter.KEY_ITEMBUYLINK
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.txtitemaddname,
                R.id.txtitemaddbuylink,
                R.id.btnAddItem
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new AddItemView_CustomAdapter(
                this, R.layout.list_additem,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.lvAddItemList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //Filter Items
        EditText myFilter = (EditText) findViewById(R.id.myItemFilter);
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
                return dbHelper.fetchItemListsByName(constraint.toString(),listid);
            }
        });

    }

    public void clickHandlerItemAddButton(View v) {
        ListView lv = (ListView) findViewById(R.id.lvAddItemList);
        int position = lv.getPositionForView(v);

        //itemid
        Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        Integer itemid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        //listid
        Integer listid = getIntent().getIntExtra("LIST_ID",0);

        //button value
        Integer itemAddRemove = cursor.getInt(cursor.getColumnIndexOrThrow("itembuylink"));

        //Test
        Toast toast = Toast.makeText(getApplicationContext(), "itemid: " + itemid.toString() + "; listid: " + listid.toString() + "; add:" + itemAddRemove.toString(), Toast.LENGTH_SHORT);
        toast.show();

        //db insert
       if (itemAddRemove.equals(0))
        {
            //ADD ITEM TO LIST
            dbHelper.close();
            dbHelper.open();
            dbHelper.ItemList_AddItem(listid,itemid);
            dbHelper.close();
        }
        else
        {
            //REMOVE FROM LIST
            dbHelper.close();
            dbHelper.open();
            dbHelper.ItemList_RemoveItem(listid,itemid);
            dbHelper.close();
        }

        displayListView(listid);

    }

    public void clickHandlerItemAdd(View v) {

        //Save a new Item
        //Toast toast = Toast.makeText(getApplicationContext(), "New Item", Toast.LENGTH_SHORT);
        //toast.show();
        Integer listid = getIntent().getIntExtra("LIST_ID",0);

        EditText et = (EditText) findViewById(R.id.myItemFilter);
        if (et.getText().length() != 0) {

            final String itemname = String.valueOf(et.getText());
            //INSERT NEW LIST INTO DB
            dbHelper = new MainMenuDbAdapter(getApplicationContext());
            dbHelper.open();
            //ADD ITEM
            dbHelper.insertNewItemList(itemname);

            Cursor cursor = dbHelper.fetchItemId(itemname);
            Integer itemid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

            // ADD ITEM TO LIST
            dbHelper.ItemList_AddItem(listid, itemid);

            Cursor cursor2= dbHelper.fetchListName(listid);
            String listname = cursor2.getString(cursor2.getColumnIndexOrThrow("listname"));
            dbHelper.close();
            //SHOW LIST
            //Refresh
            Intent intent = new Intent(this, ItemListViewCursorAdaptorActivity.class);
            intent.putExtra("LIST_ID", listid);
            intent.putExtra("LIST_NAME", listname);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        Integer i = getIntent().getIntExtra("LIST_ID",0);

        Intent intent = new Intent(getApplicationContext(), ItemListViewCursorAdaptorActivity.class);
        intent.putExtra("LIST_ID", i);
        startActivity(intent);
        finish();
    }

}