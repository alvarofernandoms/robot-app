package com.br.robot_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.br.robot_app.R;

/**
 * Simple splash screen for start the application
 */
public class SplashActivity extends AppCompatActivity{

    private int SLEEP_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        openSplashThread();
    }

    /**
     * Open a thread to create a trasition from a splash screen to the main screen
     */
    private void openSplashThread(){
        Thread splashThread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(SLEEP_TIME);
                    Intent startAlfaActivity = new Intent(getApplicationContext(), AlfaActivity.class);
                    startActivity(startAlfaActivity);
                    finish();
                } catch (InterruptedException interrExcep){
                    interrExcep.printStackTrace();
                }
            }
        };
        splashThread.start();
    }

}