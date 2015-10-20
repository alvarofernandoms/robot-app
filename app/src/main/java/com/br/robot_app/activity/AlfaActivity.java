package com.br.robot_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.br.robot_app.R;

/**
 * Responsible for the connection all apps functionalities
 */
public class AlfaActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alfa_main);
    }

    /**
     * TODO: create a unique method that recall the specific screen by param
     * */
    public void wifiConfigScreen(View view){
        Intent startActivity = new Intent(getApplicationContext(), WifiConnection.class);
        startActivity(startActivity);
    }

    public void sequenceScreen(View view){
        Intent startActivity = new Intent(getApplicationContext(), SequenceActivity.class);
        startActivity(startActivity);
    }
}