package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ItemListViewCursorAdaptorActivity extends Activity {

    private MainMenuDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packlist);
        String s = getIntent().getStringExtra("LIST_CODE");
        int i = Integer.parseInt(s);

        dbHelper = new MainMenuDbAdapter(this);
        dbHelper.open();

        //Generate ListView from SQLite Database
        displayListView(i);

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
                R.id.checkBox,
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
}