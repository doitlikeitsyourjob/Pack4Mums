package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import android.support.v7.app.AppCompatActivity;

public class AddItem_NewItem extends Activity {

    private MainMenuDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_create_newitem);

        //Bundle extras = getIntent().getExtras(); //get Bundle of extras
        String s = getIntent().getStringExtra("LIST_NAME");
        Integer listid = getIntent().getIntExtra("LIST_ID",0);

        if (s != null)
        {
            EditText et = (EditText) findViewById(R.id.etxtCreateNameItem);
            et.setText("");
            et.append(s);

            //Populate Highlight Correct Colour
        }
        else
        {
            TextView tv = (TextView) findViewById(R.id.lblTitle_NewItem);
            tv.setText("New Item");
        }

        setupUIEvents(listid);
    }

    void setupUIEvents(final Integer itemid) {

        Button btnCreateNewList = (Button) findViewById(R.id.btnCreateNewItem_SAVE);
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCreateSaveItemClick(itemid);
            }
        });

    }

    private void handleBtnCreateSaveItemClick(final Integer listid) {

        //Save a new Item
        //Toast toast = Toast.makeText(getApplicationContext(), "New Item", Toast.LENGTH_SHORT);
        //toast.show();

        EditText et = (EditText) findViewById(R.id.etxtCreateNameItem);
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

