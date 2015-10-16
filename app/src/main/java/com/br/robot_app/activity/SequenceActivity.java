package com.br.robot_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        newSequence = new Sequence();
        defaultBlocks();
    }

    public void onClickBlock(View view){
        // create something like
        // int blockId = view.findViewById()
        Block newBlock = getBlockById(0);
        newSequence.insertBlock(newBlock);
    }

    public void onClickPLay(View view){
        sendSequenceFile(view.getContext());
    }

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
        Block newBlock = new Block();
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
    }
}
