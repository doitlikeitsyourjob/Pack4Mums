package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ItemListViewCursorAdaptorActivity extends Activity {

    private MainMenuDbAdapter dbHelper;
    //private SimpleCursorAdapter dataAdapter;
    private ItemListView_CustomAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlist);
        String s = getIntent().getStringExtra("LIST_CODE");

        if (s !=null) {
            int i = Integer.parseInt(s);
            dbHelper = new MainMenuDbAdapter(this);
            dbHelper.open();

            //Generate ListView from SQLite Database
            displayListView(i);
            setupUIEvents(i);
        }

    }

     void setupUIEvents(final Integer i) {
            Button btnAddItems = (Button) findViewById(R.id.btnAddItems);
         btnAddItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleBtnAddItemsClick(i);
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

    void handleBtnAddItemsClick(Integer i) {
         Intent intent = new Intent(this, AddItemListViewCursorAdaptorActivity.class);
         intent.putExtra("LIST_CODE", i);
         startActivity(intent);
    }

    void handleBtnCreateItemsClick() {
        Intent intent = new Intent(this, MenuCreateNewList.class);
        startActivity(intent);
    }

    private void displayListView(Integer i) {

        Cursor cursor = dbHelper.fetchItemsForList(i);

        // The desired columns to be bound
        String[] columns = new String[] {
                MainMenuDbAdapter.KEY_ITEMID,
                MainMenuDbAdapter.KEY_ITEMNAME,
                MainMenuDbAdapter.KEY_ITEMBUYLINK,
                MainMenuDbAdapter.KEY_CHECK
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.code,
                R.id.txtitemname,
                R.id.txtitembuylink,
                R.id.item_chkbx,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        //dataAdapter = new SimpleCursorAdapter(
        dataAdapter = new ItemListView_CustomAdapter(
                this, R.layout.list_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.lvItemList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //TextView tv = (TextView) findViewById(R.id.txtitembuylink);
        //String html = (String) tv.getText();
        //tv.setText(Html.fromHtml(html));


        //listView.setOnItemClickListener(new OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> listView, View view,
        //                            int position, long id) {
        //        // Get the cursor, positioned to the corresponding row in the result set
        //        Cursor cursor = (Cursor) listView.getItemAtPosition(position);
        //
        //        // Get the state's capital from this row in the database.
        //        String Item =
        //                cursor.getString(cursor.getColumnIndexOrThrow("name"));
        //        Toast.makeText(getApplicationContext(),
        //                Item, Toast.LENGTH_SHORT).show();
        //
        //    }
        //}
        //);

    }

    public void clickHandlerItemCheckBox(View v) {
        ListView lv = (ListView) findViewById(R.id.lvItemList);
        int position = lv.getPositionForView(v);

        Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        final Integer ItemId = cursor.getInt(cursor.getColumnIndexOrThrow("itemid")); //listcode

        Integer cb_value = cursor.getInt(cursor.getColumnIndexOrThrow("checktick")); //listcode

        final String ListId = getIntent().getStringExtra("LIST_CODE");

        //DEBUG
        //String text = "ListId:" + ListId + " , Item_id:" + ItemId.toString();

        if (cb_value.equals(1)) {

            dbHelper.open();
            dbHelper.updateItemCheckBox_Uncheck(Integer.valueOf(ListId), ItemId);
            dbHelper.close();

            //TextView tv = (TextView) findViewById(R.id.txtitemname);
            //tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else
        {
            dbHelper.open();
            dbHelper.updateItemCheckBox_Check(Integer.valueOf(ListId), ItemId);
            dbHelper.close();

            //TextView tv = (TextView) findViewById(R.id.txtitemname);
            //tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        //Intent intent = new Intent(getApplicationContext(), ItemListViewCursorAdaptorActivity.class);
        //intent.putExtra("LIST_CODE", ListId);
        //startActivity(intent);

        //Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        //toast.show();

    }


    public void clickHandlerItemOptions(View v) {
        //ListView lv = (ListView) findViewById(R.id.listViewMain);
        //int position = lv.getPositionForView(v);
        //String info = String.valueOf(position);
        //Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        //String ListCode = cursor.getString(cursor.getColumnIndexOrThrow("listcode"));
        //Integer ListCode = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); //listcode

        Toast toast = Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT);
        toast.show();

        //dbHelper.open();
        //dbHelper.updateMainListFav(ListCode);
        //dbHelper.close();

        //Intent intent = new Intent(getApplicationContext(), MainMenuListViewCursorAdaptorActivity.class);
        //startActivity(intent);
        //displayListViewFav();
        //displayListView();
    }

}