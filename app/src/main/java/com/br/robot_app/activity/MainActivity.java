package com.br.robot_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.br.robot_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> arrayOfInstructions = new ArrayList<>();

    JSONObject myJSONobject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence_screen);

        findViewById(R.id.imageToDrop1).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher, 1));
        findViewById(R.id.imageToDrop2).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher_red, 2));
        findViewById(R.id.imageToDrop3).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher_blue, 3));
        findViewById(R.id.imageToDrop4).setOnTouchListener(new MyTouchListener(R.drawable.ic_launcher_purple, 4));
        findViewById(R.id.playButton).setOnTouchListener(new MyTouchListener(R.drawable.ic_play_arrow_black_24dp, 5));
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
        private int id;

        public MyTouchListener(int resource, int id) {
            this.resource = resource;
            this.id = id;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            LinearLayout dragArea = (LinearLayout) findViewById(R.id.dragArea);
            LinearLayout linha1 = (LinearLayout) dragArea.findViewById(R.id.linha1);
            int count1 = linha1.getChildCount();
            LinearLayout linha2 = (LinearLayout) dragArea.findViewById(R.id.linha2);
            int count2 = linha2.getChildCount();
            LinearLayout linha3 = (LinearLayout) dragArea.findViewById(R.id.linha3);
            int count3 = linha3.getChildCount();
            LinearLayout linha4 = (LinearLayout) dragArea.findViewById(R.id.linha4);
            int count4 = linha4.getChildCount();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (id == 5) {
                    for (int i = 0; i < arrayOfInstructions.size(); i++) {
                        try {
                            if (arrayOfInstructions.get(i) == 1) {
                                JSONObject tempJSON = new JSONObject();
                                tempJSON.put("position", (i + 1));
                                tempJSON.put("duration", 1);
                                tempJSON.put("power", 100);
                                myJSONobject.put("moveForward" + (i + 1), tempJSON);
                                System.out.println("DEBUG:  " + arrayOfInstructions.get(i));
                            } else if (arrayOfInstructions.get(i) == 2) {
                                JSONObject tempJSON = new JSONObject();
                                tempJSON.put("position", (i + 1));
                                tempJSON.put("degree", 180);
                                tempJSON.put("power", 100);
                                myJSONobject.put("turnLeft" + (i + 1), tempJSON);
                                System.out.println("DEBUG:  " + arrayOfInstructions.get(i));
                            } else if (arrayOfInstructions.get(i) == 3) {
                                JSONObject tempJSON = new JSONObject();
                                tempJSON.put("position", (i + 1));
                                tempJSON.put("degree", 0);
                                tempJSON.put("power", 100);
                                myJSONobject.put("turnRight" + (i + 1), tempJSON);
                                System.out.println("DEBUG:  " + arrayOfInstructions.get(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(arrayOfInstructions.get(i));
                    }
                    try {
                        Log.d("json :", myJSONobject.toString(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (count1 == 0 || count1 < 4) {
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    linha1.addView(v1);
                    arrayOfInstructions.add(id);
                    return true;
                } else if (count2 == 0 || count2 < 4) {
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    arrayOfInstructions.add(id);
                    linha2.addView(v1);
                    return true;
                } else if (count3 == 0 || count3 < 4) {
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    arrayOfInstructions.add(id);
                    linha3.addView(v1);
                    return true;
                } else if (count4 == 0 || count4 < 4) {
                    ImageView v1 = new ImageView(getApplication());
                    v1.setImageResource(resource);
                    arrayOfInstructions.add(id);
                    linha4.addView(v1);
                    return true;
                }
            } else return false;


            return false;
        }

    }

}
