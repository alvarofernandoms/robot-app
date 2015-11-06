package com.br.robot_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.br.robot_app.R;
import com.br.robot_app.connect.Connector;
import com.br.robot_app.model.Block;
import com.br.robot_app.model.Sequence;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for creating a sequence_screen of blocks and mount the
 * program for execution
 */
public class SequenceActivity extends AppCompatActivity {

    private Sequence newSequence;
    private List<Block> blockTypes;

    private final int MOVE_BLOCK = 0; // TODO: check if this is going to be a move forward block

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the view and elements
        setContentView(R.layout.sequence_screen);
        blockTypes = new ArrayList<Block>();
        newSequence = new Sequence(getBaseContext());
        defaultBlocks();

        // Getting touch elements
        findViewById(R.id.imageToDrop1).setOnTouchListener(new BlockListener(R.drawable.ic_launcher));
        findViewById(R.id.playButton).setOnTouchListener(new PlayListener());
    }

    /**
     * Player button. Execute the file tranfer from the app to the robot
     *
     * @param context
     */
    private void sendSequenceFile(Context context){
        Connector conn = Connector.getConnector();
        newSequence.buildJSON(context);
        conn.sender(newSequence.getSequence());
    }

    /**
     *
     * @param blockId represent the id of the block on view
     * @return the block that is on blockTypes
     */
    public Block getBlockById(int blockId){
        return blockTypes.get(blockId);
    }

    /**
     * TODO: transform the hard code to get from a api
     */
    private void defaultBlocks(){
        Block newBlock = new Block(MOVE_BLOCK);
        List<String> values = new ArrayList<String>();
        List<String> params = new ArrayList<String>();

        params.add("duration");
        params.add("power");

        values.add("1");
        values.add("100");

        try{
            newBlock.addingInstruction("moveForward",params,values);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        blockTypes.add(newBlock);
    }

    /**
     * Listener inerclass for blocks
     */
    private class BlockListener implements OnTouchListener {

        private int viewResource;

        public BlockListener(int viewResource){
            this.viewResource = viewResource;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event){

            boolean actionResult = false;
            LinearLayout dragArea = (LinearLayout) findViewById(R.id.dragArea);
//            LinearLayout line = (LinearLayout) dragArea.findViewById(R.id.linha1);

            // Setting the new linear layout
            LinearLayout new_line = new LinearLayout(v.getContext());
            dragArea.addView(new_line);

            if(event.getAction() == MotionEvent.ACTION_DOWN){
                ImageView viewBlock = new ImageView(getApplication());
                Block blockResource = getBlockById(MOVE_BLOCK);

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
