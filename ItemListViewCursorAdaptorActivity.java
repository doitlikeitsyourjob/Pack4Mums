package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ItemListViewCursorAdaptorActivity extends Activity {

    private MainMenuDbAdapter dbHelper;
    //private SimpleCursorAdapter dataAdapter;
    private ItemListView_CustomAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlist);
        Integer i = getIntent().getIntExtra("LIST_ID",0);
        //String listname = getIntent().getStringExtra("LIST_NAME");

        if (i != 0) {
            //int i = Integer.parseInt(s);
            dbHelper = new MainMenuDbAdapter(this);
            dbHelper.close();
            dbHelper.open();

            //TITLE
            Cursor cursor = dbHelper.fetchListName(i);
            String listname = cursor.getString(cursor.getColumnIndexOrThrow("listname"));
            TextView title = (TextView) findViewById(R.id.lblTitle_List);
            title.setText(listname);

            //LISTVIREW
            displayListView(i);
            setupUIEvents(i);
        }
    }

    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        Integer i = getIntent().getIntExtra("LIST_ID",0);
        if (i != 0) {
            //int i = Integer.parseInt(s);
            dbHelper = new MainMenuDbAdapter(this);
            dbHelper.close();
            dbHelper.open();
            displayListView(i);
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
                    handleBtnCreateItemsClick(i);
                }
            });

        }

    void handleBtnAddItemsClick(Integer i) {
         Intent intent = new Intent(this, AddItemListViewCursorAdaptorActivity.class);
         intent.putExtra("LIST_ID", i);
         startActivity(intent);
    }

    void handleBtnCreateItemsClick(Integer i) {
        Intent intent = new Intent(this, AddItem_NewItem.class);
        intent.putExtra("LIST_ID", i);
        startActivity(intent);
    }

    private void displayListView(Integer i) {

        dbHelper.close();
        dbHelper.open();
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
        final Integer ItemId = cursor.getInt(cursor.getColumnIndexOrThrow("itemid")); //itemid

        Integer cb_value = cursor.getInt(cursor.getColumnIndexOrThrow("checktick")); //checktick

        final Integer ListId = getIntent().getIntExtra("LIST_ID",0); //listid

        //DEBUG
        //String text = "ListId:" + ListId + " , Item_id:" + ItemId.toString();

        if (cb_value.equals(1)) {

            dbHelper.close();
            dbHelper.open();
            dbHelper.updateItemCheckBox_Uncheck(ListId, ItemId);
            dbHelper.close();

            //TextView tv = (TextView) findViewById(R.id.txtitemname);
            //tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else
        {
            dbHelper.close();
            dbHelper.open();
            dbHelper.updateItemCheckBox_Check(ListId, ItemId);
            dbHelper.close();

            //TextView tv = (TextView) findViewById(R.id.txtitemname);
            //tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        //Intent intent = new Intent(getApplicationContext(), ItemListViewCursorAdaptorActivity.class);
        //intent.putExtra("LIST_ID", ListId);
        //startActivity(intent);

        //Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        //toast.show();

    }


    public void clickHandlerItemOptions(View v) {
        //Toast toast = Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT);
        //toast.show();
        showPopupMenu(v);

    }

    private void showPopupMenu(final View view) {

        final PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.popup_itemmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.menuItemRemove) {
                    MenuItemRemove(view);
                    return true;
                //}
                //else if (i == R.id.menuEdit) {
                //    //UnFavorite
                //    MenuEdit(view);
                //    return true;
                //} else if (i == R.id.menuDelete) {
                //    MenuDelete(view);
                //    return true;
                } else {
                    return onMenuItemClick(item);
                }
            }
        });

        popup.show();
    }

    private void MenuItemRemove(View view) {
        ListView lv = (ListView) findViewById(R.id.lvItemList);
        int position = lv.getPositionForView(view);

        //itemid
        Cursor cursor = (Cursor) lv.getItemAtPosition(position);
        final Integer itemid = cursor.getInt(cursor.getColumnIndexOrThrow("itemid"));

        //listid
        final Integer listid = getIntent().getIntExtra("LIST_ID",0);
        //final int listid = Integer.parseInt(s);

        AsyncTask<Long, Object, Object> Task =  new AsyncTask<Long, Object, Object>()
        {
            @Override
            protected Object doInBackground(Long... params)
            {
                dbHelper.open();
                dbHelper.ItemList_RemoveItem(listid, itemid);
                dbHelper.close();
                return null;
            } //end method doInBackgroud

            @Override
            protected void onPostExecute (Object result)
            {
                finish(); //return to Activity

                Intent intent = new Intent(getApplicationContext(), ItemListViewCursorAdaptorActivity.class);
                intent.putExtra("LIST_ID", listid);
                startActivity(intent);
            } //end method on PostExecute
        }; //end new Async Task
        Task.execute();

        //displayListView(listid);
    }

    @Override
    public void onBackPressed(){
        Integer listid = getIntent().getIntExtra("LIST_ID",0);

        Intent intent = new Intent(getApplicationContext(), MainMenuListViewCursorAdaptorActivity.class);
        intent.putExtra("LIST_ID", listid);
        startActivity(intent);
        finish();
    }


}