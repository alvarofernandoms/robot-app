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
import com.br.robot_app.model.ApiBlock;
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
    private ApiBlock api;

    private List<String> listOfBlocks = new ArrayList<String>();
    private Integer interator = 0;

    // Main images of the screen
    private final int MOVE_FORWARD = R.id.move_forward;
    private final int MOVE_BACKWARD = R.id.move_backward;
    private final int MOVE_RIGHT = R.id.move_right;
    private final int MOVE_LEFT = R.id.move_left;
    private final int MOVE_STOP = R.id.move_stop;
    private final int MOVE_SPIN = R.id.move_spin;
    private final int CONDITION = R.id.condition;
    private final int LOOP_IMG = R.id.loop_blk;
    private final int PLAY_IMG = R.id.playButton;
    private final int SAVE_IMG = R.id.saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the view and elements
        setContentView(R.layout.sequence_screen);

        newSequence = new Sequence(getBaseContext());
        api = new ApiBlock();
        setElements();
    }

    /**
     * To set all elements on view
     */
    private void setElements(){
        // Getting touch elements
        findViewById(MOVE_FORWARD).setOnTouchListener(new BlockListener(R.mipmap.ic_move_forward));
        findViewById(MOVE_BACKWARD).setOnTouchListener(new BlockListener(R.mipmap.ic_move_backward));
        findViewById(MOVE_RIGHT).setOnTouchListener(new BlockListener(R.mipmap.ic_move_right));
        findViewById(MOVE_LEFT).setOnTouchListener(new BlockListener(R.mipmap.ic_move_left));
        findViewById(MOVE_STOP).setOnTouchListener(new BlockListener(R.mipmap.ic_move_stop));
        findViewById(MOVE_SPIN).setOnTouchListener(new BlockListener(R.mipmap.ic_move_spin));
        findViewById(CONDITION).setOnTouchListener(new BlockListener(R.mipmap.ic_condition));
        findViewById(LOOP_IMG).setOnTouchListener(new BlockListener(R.mipmap.ic_loop));
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

    /** Start of inerclasses TODO: This should be a package of class contains Listeners **/

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

                int blockId = api.convertId(v.getId(), ApiBlock.IMG_TO_BLOCK);
                Block blockResource = api.getBlockById(blockId);

                ImageView viewBlock = new ImageView(getApplication());
                viewBlock.setId(interator);
                blockResource.blockId = interator;
                interator++;

                viewBlock.setImageResource(viewResource);
                new_line.addView(viewBlock);
                viewBlock.setOnTouchListener(new RemoveBlockListener(viewResource));

                listOfBlocks.add(String.valueOf(viewBlock.getId()));

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

    /**
     * Listener inerclass for Remove block
     */
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
                newSequence.removeBlock(v.getId());

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
                //sendSequenceFile(v.getContext());
                newSequence.showJSONfile(getBaseContext());
                Log.d("Size blocks: ", String.valueOf(newSequence.blocks.size()));
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
                popup.show(manager, "Dialog");
            }
            return false;
        }
    }

    /**
     * Dialog form to get the name of the program
     */
    private class ProgSaveDialog extends DialogFragment implements View.OnClickListener {

        private Button save;
        private Button cancel;
        private EditText progName;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View dialog = inflater.inflate(R.layout.save_prog,null);
            getDialog().setTitle("Nome do Programa:");

            progName = (EditText) dialog.findViewById(R.id.prog_text);

            save = (Button) dialog.findViewById(R.id.save_button_dialog);
            cancel = (Button) dialog.findViewById(R.id.cancel_button_dialog);

            save.setOnClickListener(this);
            cancel.setOnClickListener(this);

            setCancelable(false);
            return dialog;
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.save_button_dialog) {
                String name = progName.getText().toString();
                newSequence.saveFile(v.getContext(), name);
                newSequence.printFiles(v.getContext());
                Log.d("Saving...", name);
                Toast.makeText(getActivity(), "Programa Salvo!", Toast.LENGTH_LONG).show();
                dismiss();
            } else {
                Log.d("Cancel...","");
                dismiss();
            }
        }
    }

    /**
     * Dialog form to get the block params
     */

}
