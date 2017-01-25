package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuCreateNewList extends Activity {

    private MainMenuDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_create_newlist);

        //Bundle extras = getIntent().getExtras(); //get Bundle of extras
        String s = getIntent().getStringExtra("LIST_NAME");
        Integer listid = getIntent().getIntExtra("LIST_ID",0);

        if (s != null)
        {
            //Get Properties of List from _id
            //int i = Integer.parseInt(s);

            //dbHelper = new MainMenuDbAdapter(this);
            //dbHelper.open();

            //Generate ListView from SQLite Database
            //displayListView(i);
            //Populate AddEditList

            EditText et = (EditText) findViewById(R.id.etxtCreateNameList);
            et.setText("");
            et.append(s);

            //Populate Highlight Correct Colour
        }
        else
        {
            TextView tv = (TextView) findViewById(R.id.lblTitle_EditMenu);
            tv.setText("New List");
        }

        setupUIEvents(listid);
    }

    void setupUIEvents(final Integer listid) {

        Button btnCreateNewList = (Button) findViewById(R.id.btnCreateNewList_SAVE);
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCreateSaveListClick(listid);
            }
        });

    }

    private void handleBtnCreateSaveListClick(final Integer listid) {

        if (listid != 0)
        {
            //Save an Existing List
            TextView tv = (TextView) findViewById(R.id.etxtCreateNameList);
            final String listname = String.valueOf(tv.getText());

            AsyncTask<Long, Object, Object> Task =  new AsyncTask<Long, Object, Object>()
            {
                @Override
                protected Object doInBackground(Long... params)
                {
                    //Toast toast = Toast.makeText(getApplicationContext(),"Save Existing List", Toast.LENGTH_SHORT);
                    //toast.show();

                    //AsyncTask
                    dbHelper = new MainMenuDbAdapter(getApplicationContext());
                    dbHelper.open();
                    dbHelper.updateEditList(listid, listname);
                    dbHelper.close();
                    return null;
                } //end method doInBackgroud

                @Override
                protected void onPostExecute (Object result)
                {
                    finish(); //return to Activity
                } //end method on PostExecute
            }; //end new Async Task
            Task.execute();

        }
        else
        {
            //Save a new list
            //Toast toast = Toast.makeText(getApplicationContext(),"New List", Toast.LENGTH_SHORT);
            //toast.show();

            EditText et = (EditText) findViewById(R.id.etxtCreateNameList);
            if (et.getText().length()!=0) {

                final String listname = String.valueOf(et.getText());

                AsyncTask<Long, Object, Object> Task =  new AsyncTask<Long, Object, Object>()
                {
                    @Override
                    protected Object doInBackground(Long... params)
                    {
                        //AsyncTask
                        //INSERT NEW LIST INTO DB
                        dbHelper = new MainMenuDbAdapter(getApplicationContext());
                        dbHelper.open();
                        dbHelper.insertMainList(listname);
                        dbHelper.close();
                        return null;
                    } //end method doInBackgroud

                    @Override
                    protected void onPostExecute (Object result)
                    {
                        finish(); //return to Activity
                    } //end method on PostExecute
                }; //end new Async Task
                Task.execute();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error");
                    builder.setMessage("Enter List Name");
                    builder.setPositiveButton("Ok",null);
                    builder.show();
                }

        }

        //Refresh
        Intent intent = new Intent(this, MainMenuListViewCursorAdaptorActivity.class);
        startActivity(intent);

    }


}

