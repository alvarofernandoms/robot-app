package com.br.robot_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.br.robot_app.R;
import com.br.robot_app.connect.Connector;

/**
 * Responsible for the connection all apps functionalities
 */
public class AlfaActivity extends AppCompatActivity {

    public static int numberOfSequence; // Bad practice of static global

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alfa_main);

        // Initialize the connection with the server
        Connector conn = Connector.getConnector();
        conn.connectToAlfa();

        // Initialize the amount of sequence
        numberOfSequence = 0;
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
        numberOfSequence++;
    }


    public void programListScreen(View view){
        Intent startActivity = new Intent(getApplicationContext(), ProgramListActivity.class);
        startActivity(startActivity);
    }
}