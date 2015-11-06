package com.br.robot_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.br.robot_app.R;
import com.br.robot_app.connect.Connector;
import com.br.robot_app.model.Block;
import com.br.robot_app.model.Sequence;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for creating a sequence_screen of blocks and mount the
 * program for execution
 */
public class SequenceActivity extends AppCompatActivity {

    private Sequence newSequence;
    private List<Block> blockTypes;

    // TODO: check if this is going to be id of the blocks
    private final int MOVE_BLOCK = 0;
    private final int TURN_BLOCK = 1;
    private final int LOOP_BLOCK = 2;

    // Main images of the screen
    private final int MOVE_IMG = R.id.moveButton;
    private final int TURN_IMG = R.id.turnButton;
    private final int LOOP_IMG = R.id.loopButton;
    private final int PLAY_IMG = R.id.playButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the view and elements
        setContentView(R.layout.sequence_screen);
        blockTypes = new ArrayList<Block>();
        newSequence = new Sequence(getBaseContext());
        defaultBlocks();

        // Getting touch elements
        findViewById(MOVE_IMG).setOnTouchListener(new BlockListener(R.drawable.ic_launcher));
        findViewById(TURN_IMG).setOnTouchListener(new BlockListener(R.drawable.ic_launcher_red));
        findViewById(LOOP_IMG).setOnTouchListener(new BlockListener(R.drawable.ic_launcher_blue));
        findViewById(PLAY_IMG).setOnTouchListener(new PlayListener());
    }

    /**
     * Player button. Execute the file tranfer from the app to the robot
     *
     * @param context
     */
    private void sendSequenceFile(Context context){
        Connector conn = Connector.getConnector();
        newSequence.buildJSON(context);

        // TODO: for debug! exclude this
        File file = newSequence.getSequence();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Log.d("JSON",reader.readLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.sender(newSequence.getSequence());
    }

    /**
     *
     * @param blockId represent the id of the block on view
     * @return the block that is on blockTypes
     */
    public Block getBlockById(int blockId){
        Block result = blockTypes.get(blockId);
        return result;
    }

    /**
     * TODO: transform the hard code to get from a api
     */
    private void defaultBlocks(){
        Block moveBlock = new Block();
        Block turnBlock = new Block();
        Block loopBlock = new Block();

        // MOVE
        List<String> movevalues = new ArrayList<String>();
        List<String> moveparams = new ArrayList<String>();
        moveparams.add("duration");
        moveparams.add("power");
        moveparams.add("direction");
        movevalues.add("1");
        movevalues.add("100");
        movevalues.add("forward");

        // TURN
        List<String> turnvalues = new ArrayList<String>();
        List<String> turnparams = new ArrayList<String>();
        turnparams.add("degree");
        turnparams.add("direction");
        turnvalues.add("90");
        turnvalues.add("right");

        // LOOP
        List<String> loopvalues = new ArrayList<String>();
        List<String> loopparams = new ArrayList<String>();
        loopparams.add("qnt");
        loopvalues.add("3");

        try{
            moveBlock.addingInstruction("move",moveparams,movevalues);
            turnBlock.addingInstruction("turn",turnparams,turnvalues);
            loopBlock.addingInstruction("loop",loopparams,loopvalues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        blockTypes.add(moveBlock);
        blockTypes.add(turnBlock);
        blockTypes.add(loopBlock);
    }

    /**
     * Listener inerclass for blocks
     */
    private class BlockListener implements OnTouchListener {

        private int viewResource;

        public BlockListener(int viewResource){
            this.viewResource = viewResource;
        }

        // TODO: modularize!
        @Override
        public boolean onTouch(View v, MotionEvent event){

            boolean actionResult = false;
            LinearLayout dragArea = (LinearLayout) findViewById(R.id.dragArea);

            // Set screen to the bottom of the scroll
            ScrollView sv = (ScrollView)findViewById(R.id.scrollview);
            sv.scrollTo(0, sv.getBottom());

            // Setting the new linear layout
            LinearLayout new_line = new LinearLayout(v.getContext());
            new_line.setGravity(Gravity.CENTER_HORIZONTAL);
            dragArea.addView(new_line);

            if(event.getAction() == MotionEvent.ACTION_DOWN){

                // Define which bock to set
                int currentId = v.getId();
                int blockId = 0;
                switch (currentId){
                    case MOVE_IMG:
                        blockId = MOVE_BLOCK;
                        break;
                    case TURN_IMG:
                        blockId = TURN_BLOCK;
                        break;
                    case LOOP_IMG:
                        blockId = LOOP_BLOCK;
                        break;
                }

                ImageView viewBlock = new ImageView(getApplication());
                Block blockResource = getBlockById(blockId);

                viewBlock.setImageResource(viewResource);
                new_line.addView(viewBlock);

                newSequence.insertBlock(blockResource);
                actionResult = true;
            }
            return actionResult;
        }
    }

    /**
     * Listener inerclass for Play button
     */
    private class PlayListener implements OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean actionResult = false;
            sendSequenceFile(v.getContext());
            Log.d("Play Listener", String.valueOf(newSequence.getSequence()));


            return actionResult;
        }
    }
}
