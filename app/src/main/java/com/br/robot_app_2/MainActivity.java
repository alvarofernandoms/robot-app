package com.br.robot_app_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FrameLayout dragArea = (FrameLayout)findViewById(R.id.dragArea);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageToDrop1).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher));
        findViewById(R.id.imageToDrop2).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher_red));
        findViewById(R.id.imageToDrop3).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher_blue));
        findViewById(R.id.imageToDrop4).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher_purple));


        //findViewById(R.id.dragArea).setOnDragListener(new MyDragListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyTouchListener implements View.OnTouchListener {

        private int resource;

        //private int count = 0;

        public MyTouchListener(int resource) {
            this.resource = resource;
        }
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //count++;
            LinearLayout dragArea = (LinearLayout)findViewById(R.id.dragArea);
            LinearLayout linha1 = (LinearLayout) dragArea.findViewById(R.id.linha1);
            int count1 = linha1.getChildCount();
            LinearLayout linha2 = (LinearLayout) dragArea.findViewById(R.id.linha2);
            int count2 = linha2.getChildCount();
            LinearLayout linha3 = (LinearLayout) dragArea.findViewById(R.id.linha3);
            int count3 = linha3.getChildCount();
            LinearLayout linha4 = (LinearLayout) dragArea.findViewById(R.id.linha4);
            int count4 = linha4.getChildCount();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (count1 == 0 || count1 < 4) {
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    linha1.addView(v1);
                    //count++;
                    return true;
                }
                else if (count2 == 0 || count2 < 4){
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    linha2.addView(v1);
                    //count++;
                    return true;
                }
                else if (count3 == 0 || count3 < 4){
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    linha3.addView(v1);
                    //count++;
                    return true;
                }
                else if (count4 == 0 || count4 < 4){
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    linha4.addView(v1);
                    //count++;
                    return true;
                }
                //count++;
            } else return false;


            //count++;
            return false;
        }

    }
}
