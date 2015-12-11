package com.br.robot_app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.br.robot_app.R;

/**
 * Created by paulo on 11/12/15.
 */

public class PopupFormActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_prog_form);
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = (int) (display.widthPixels * 0.8);
        int heigh = (int) (display.heightPixels * 0.5);

        getWindow().setLayout(width,heigh);
    }
}