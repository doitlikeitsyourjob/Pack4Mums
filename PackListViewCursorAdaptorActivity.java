package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PackListViewCursorAdaptorActivity extends Activity {

    private PackListDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlist);
        String s = getIntent().getStringExtra("CODE");

        //dbHelper = new MainMenuDbAdapter(this);
        //dbHelper.open();

        //dbHelper = new PackListDbAdapter(this);
        //dbHelper.open();
        //Clean all data
        //dbHelper.deleteAllListItems();
        //Add some data
        //dbHelper.insertSomeLists();

        //Generate ListView from SQLite Database
        displayListView();

    }

    private void displayListView() {

        Cursor cursor = dbHelper.fetchAllListItems();

        // The desired columns to be bound
        String[] columns = new String[] {
                PackListDbAdapter.KEY_CODE,
                PackListDbAdapter.KEY_NAME,
                PackListDbAdapter.KEY_DESCRIPTION,
                PackListDbAdapter.KEY_LINK
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.code,
                R.id.name,
                R.id.description,
                R.id.link,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list_info,
                cursor,
                columns,
                to,
                0);




        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

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
}