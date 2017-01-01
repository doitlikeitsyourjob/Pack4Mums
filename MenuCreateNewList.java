package com.doitlikeitsyourjob.pack4mums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuCreateNewList extends AppCompatActivity {

    private MainMenuDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_create_newlist);

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
        Editable inputtext = et.getText();

        Toast toast = Toast.makeText(getApplicationContext(),inputtext, Toast.LENGTH_SHORT);
        toast.show();

        //INSERT NEW LIST INTO DB
        dbHelper = new MainMenuDbAdapter(this);
        dbHelper.open();
        dbHelper.insertMainList(String.valueOf(inputtext));
        dbHelper.close();

        Intent intent = new Intent(this, MainMenuListViewCursorAdaptorActivity.class);
        startActivity(intent);

    }


}

