package com.br.robot_app.activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

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

    List<String> listOfBlocks = new ArrayList<String>();
    List<Integer> listOfSequence = new ArrayList<>();
    Integer interator = 0;

    // TODO: check if this is going to be id of the blocks
    private final int MOVE_BLOCK = 0;
    private final int TURN_BLOCK = 1;
    private final int LOOP_BLOCK = 2;

    private final int BLOCK_TO_IMG = 0;
    private final int IMG_TO_BLOCK = 1;

    // Main images of the screen
    private final int MOVE_FORWARD = R.id.move_forward;
    private final int MOVE_BACKWARD = R.id.move_backward;
    private final int MOVE_RIGHT = R.id.move_right;
    private final int MOVE_LEFT = R.id.move_left;
    private final int MOVE_STOP = R.id.move_stop;
    private final int MOVE_SPIN = R.id.move_spin;
    private final int CONDITION = R.id.condition;
    private final int MOVE_IMG = R.id.moveButton;
    private final int TURN_IMG = R.id.turnButton;
    private final int LOOP_IMG = R.id.loopButton;
    private final int LOOP_IMG2 = R.id.loop;
    private final int PLAY_IMG = R.id.playButton;
    private final int SAVE_IMG = R.id.saveButton;


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
        findViewById(MOVE_FORWARD).setOnTouchListener(new BlockListener(R.mipmap.ic_move_forward));
        findViewById(MOVE_BACKWARD).setOnTouchListener(new BlockListener(R.mipmap.ic_move_backward));
        findViewById(MOVE_RIGHT).setOnTouchListener(new BlockListener(R.mipmap.ic_move_right));
        findViewById(MOVE_LEFT).setOnTouchListener(new BlockListener(R.mipmap.ic_move_left));
        findViewById(MOVE_STOP).setOnTouchListener(new BlockListener(R.mipmap.ic_move_stop));
        findViewById(MOVE_SPIN).setOnTouchListener(new BlockListener(R.mipmap.ic_move_spin));
        findViewById(CONDITION).setOnTouchListener(new BlockListener(R.mipmap.ic_condition));
        findViewById(LOOP_IMG2).setOnTouchListener(new BlockListener(R.mipmap.ic_loop));
        findViewById(PLAY_IMG).setOnTouchListener(new PlayListener());
        findViewById(SAVE_IMG).setOnTouchListener(new SaveListener());
    }

    /**
     * Player button. Execute the file tranfer from the app to the robot
     *
     * @param context
     */
    private void sendSequenceFile(Context context) {
        Connector conn = Connector.getConnector();
        newSequence.buildJSON(context);

        // TODO: for debug! exclude this
        File file = newSequence.getSequence();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Log.d("JSON", reader.readLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.sender(newSequence.getSequence());
    }

    /**
     * @param blockId represent the id of the block on view
     * @return the block that is on blockTypes
     */
    public Block getBlockById(int blockId) {
        Block result = blockTypes.get(blockId);
        return result;
    }

    /**
     * TODO: transform the hard code to get from a api
     */
    private void defaultBlocks() {
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

        try {
            moveBlock.addingInstruction("move", moveparams, movevalues);
            turnBlock.addingInstruction("turn", turnparams, turnvalues);
            loopBlock.addingInstruction("loop", loopparams, loopvalues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        blockTypes.add(moveBlock);
        blockTypes.add(turnBlock);
        blockTypes.add(loopBlock);
    }

    private int convertId(View v, int type){
        // Define which bock to set
        int currentId = v.getId();
        int id = 0;
        if(type == IMG_TO_BLOCK){
            switch (currentId) {
                case MOVE_IMG:
                    id = MOVE_BLOCK;
                    break;
                case TURN_IMG:
                    id = TURN_BLOCK;
                    break;
                case LOOP_IMG:
                    id = LOOP_BLOCK;
                    break;
            }
        }else if(type == BLOCK_TO_IMG) {
            switch (currentId) {
                case MOVE_BLOCK:
                    id = MOVE_IMG;
                    break;
                case TURN_BLOCK:
                    id = TURN_IMG;
                    break;
                case LOOP_BLOCK:
                    id = LOOP_IMG;
                    break;
            }
        }
        return id;
    }

    /**
     * Listener inerclass for blocks
     */
    private class BlockListener implements OnTouchListener {

        private int viewResource;

        public BlockListener(int viewResource) {
            this.viewResource = viewResource;
        }

        // TODO: modularize!
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            boolean actionResult = false;
            LinearLayout dragArea = (LinearLayout) findViewById(R.id.dragArea);

            // Set screen to the bottom of the scroll
            ScrollView sv = (ScrollView) findViewById(R.id.scrollview);
            sv.scrollTo(0, sv.getBottom());

            // Setting the new linear layout
            LinearLayout new_line = new LinearLayout(v.getContext());
            new_line.setGravity(Gravity.CENTER_HORIZONTAL);
            dragArea.addView(new_line);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                int blockId = convertId(v,IMG_TO_BLOCK);

                ImageView viewBlock = new ImageView(getApplication());
                viewBlock.setId(interator++);
                Block blockResource = getBlockById(blockId);

                viewBlock.setImageResource(viewResource);
                new_line.addView(viewBlock);
                viewBlock.setOnTouchListener(new RemoveBlockListener(viewResource));

                listOfBlocks.add(String.valueOf(viewBlock.getId()));
                // findViewById(viewBlock.getId()).setOnTouchListener(new RemoveBlockListener());

                newSequence.insertBlock(blockResource);
                actionResult = true;

                for (int i = 0; i < listOfBlocks.size(); i++) {
                    System.out.println(viewResource);
                }
                System.out.println("----------------------");
            }
            return actionResult;
        }
    }

    /* Romove block  */

    private class RemoveBlockListener implements OnTouchListener {

        private int viewResource;

        public RemoveBlockListener(int viewResource) {
            this.viewResource = viewResource;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // TODO: another ACTION to consider the scroll moviment
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                View destroy_line = findViewById(v.getId());
                ((ViewGroup) destroy_line.getParent()).removeView(destroy_line);

                Log.d("Parent's view >>>>>>>>>>>>>>", String.valueOf(v.getId()));
                Log.d("Debug>>>>>>>>>>>>>>>>>>>", "bloco na lista!");

                listOfBlocks.remove(String.valueOf(v.getId()));

                for (int i = 0; i < listOfBlocks.size(); i++) {
                    System.out.println(listOfBlocks.get(i));
                }
                System.out.println("----------na remoçao------------");

            }
            return false;

        }
    }

    /**
     * Listener inerclass for Play button
     */
    private class PlayListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean actionResult = false;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                sendSequenceFile(v.getContext());
                Log.d("Play Listener", String.valueOf(newSequence.getSequence()));
                actionResult = true;
            }
            return actionResult;
        }
    }

    /**
     * Listener inerclass for Save button
     */
    private class SaveListener implements OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean actionResult = false;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                FragmentManager manager = getFragmentManager();
                ProgSaveDialog popup = new ProgSaveDialog();
                popup.show(manager,"Dialog");
                Log.d("SAVE","...");
            }
            return false;
        }
    }

    private class ProgSaveDialog extends DialogFragment implements View.OnClickListener {

        private Button save;
        private Button cancel;
        private EditText progName;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View dialog = inflater.inflate(R.layout.save_prog,null);

            progName = (EditText) findViewById(R.id.prog_text);

            save = (Button) dialog.findViewById(R.id.save_button_dialog);
            cancel = (Button) dialog.findViewById(R.id.cancel_button_dialog);

            save.setOnClickListener(this);
            cancel.setOnClickListener(this);

            setCancelable(false);
            return dialog;
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.saveButton) {
                newSequence.saveFile(getContext(), progName.getText().toString());
                Log.d("Saving...", progName.getText().toString());
                Toast.makeText(getActivity(), "Programa Salvo!", Toast.LENGTH_LONG).show();
                dismiss();
            } else {
                Log.d("Cancel...","");
                dismiss();
            }
        }
    }
}
