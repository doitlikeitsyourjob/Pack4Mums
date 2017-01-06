package com.doitlikeitsyourjob.pack4mums;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuCreateNewList extends Activity {

    private MainMenuDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_create_newlist);

        //Bundle extras = getIntent().getExtras(); //get Bundle of extras
        String s = getIntent().getStringExtra("LIST_NAME");

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


        setupUIEvents();

    }

    void setupUIEvents() {

        Button btnCreateNewList = (Button) findViewById(R.id.btnCreateNewList_SAVE);
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCreateSaveListClick();
            }
        });

    }

    private void handleBtnCreateSaveListClick() {


        EditText et = (EditText) findViewById(R.id.etxtCreateNameList);

        if (et.getText().length()!=0) {
            //Editable inputtext = et.getText();

            Toast toast = Toast.makeText(getApplicationContext(),et.getText(), Toast.LENGTH_SHORT);
            toast.show();

            //INSERT NEW LIST INTO DB
            dbHelper = new MainMenuDbAdapter(this);
            dbHelper.open();
            dbHelper.insertMainList(String.valueOf(et.getText()));
            dbHelper.close();

            Intent intent = new Intent(this, MainMenuListViewCursorAdaptorActivity.class);
            startActivity(intent);
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


}

