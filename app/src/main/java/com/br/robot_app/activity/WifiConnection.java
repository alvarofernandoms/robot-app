package com.br.robot_app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.br.robot_app.R;
import com.br.robot_app.connect.Connector;

/**
 * TODO: this class maybe tmp because the status of the connection must be visible thought the app
 */
public class WifiConnection extends AppCompatActivity {

    private final int STATUS_IMG = R.id.status_img;

    private final int ON_IMG = R.drawable.conn_status_on;
    private final int OFF_IMG = R.drawable.conn_status_off;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
    }

    public void showStatus(View view) {
        Connector conn = Connector.getConnector();
        boolean status = conn.getConnectionStatus();

        ImageView statusImage = (ImageView) findViewById(STATUS_IMG);
        if(status){statusImage.setImageResource(ON_IMG);}
        else{statusImage.setImageResource(OFF_IMG);}
    }

}