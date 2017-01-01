package com.doitlikeitsyourjob.pack4mums;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIEvents();
    }

    void setupUIEvents() {

        Button thebutton = (Button) findViewById(R.id.btnPrep);
        thebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick();
            }
        });

        Button thebuttonCook = (Button) findViewById(R.id.btnHosp);
        thebuttonCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick();
            }
        });

        Button thebutton3 = (Button) findViewById(R.id.btnBabyDay);
        thebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnMenuClick();
            }
        });

    }

    void handleButtonClick() {
        Intent intent = new Intent(this, PackListViewCursorAdaptorActivity.class);
        startActivity(intent);

        //interstitial.show();
    }

    void handleBtnMenuClick() {
        Intent intent = new Intent(this, MainMenuListViewCursorAdaptorActivity.class);
        startActivity(intent);

        //interstitial.show();
    }

}
