package com.br.robot_app.model;

import android.view.View;

import com.br.robot_app.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ApiBlock {

    public static final int MOVE_FORWARD = 0;
    public static final int MOVE_BACKWARD = 1;
    public static final int TURN_RIGHT = 3;
    public static final int TURN_LEFT = 4;

    public static final int STOP = 5;
    public static final int SPIN = 6;

    public static final int CONDITION = 7;
    public static final int LOOP = 8;

    public static final int BLOCK_TO_IMG = 0; // TODO: from json to sequence
    public static final int IMG_TO_BLOCK = 1;

    // TODO: this is not a good practice
    private static final int MOVE_FORWARD_IMG = R.id.move_forward;
    private static final int MOVE_BACKWARD_IMG = R.id.move_backward;
    private static final int TURN_RIGHT_IMG = R.id.move_right;
    private static final int TURN_LEFT_IMG = R.id.move_left;

    private static final int STOP_IMG = R.id.move_stop;
    private static final int SPIN_IMG = R.id.move_spin;

    private static final int CONDITION_IMG = R.id.condition;
    private static final int LOOP_IMG = R.id.loop;

    public List<Block> blockTypes;

    public ApiBlock(){
        this.blockTypes = new ArrayList<Block>();
        defaultBlocks();
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
        Block moveForwardBlock = new Block();
        Block moveBackwardBlock = new Block();
        Block moveRightBlock = new Block();
        Block moveLeftBlock = new Block();

        Block stopBlock = new Block();
        Block spinBlock = new Block();

        Block conditionBlock = new Block();
        Block loopBlock = new Block();

        // Move Forward
        List<String> mfValues = new ArrayList<String>();
        List<String> mfParams = new ArrayList<String>();

        mfParams.add("duration");
        mfParams.add("power");
        mfParams.add("orietation");

        mfValues.add("2");
        mfValues.add("100");
        mfValues.add("0");

        // Move Backward
        List<String> mbValues = new ArrayList<String>();
        List<String> mbParams = new ArrayList<String>();

        mbParams.add("duration");
        mbParams.add("power");
        mbParams.add("orietation");

        mbValues.add("2");
        mbValues.add("100");
        mbValues.add("1");

        // Turn Right
        List<String> trValues = new ArrayList<String>();
        List<String> trParams = new ArrayList<String>();

        trParams.add("degree");
        trParams.add("power");
        trParams.add("orietation");

        trValues.add("90");
        trValues.add("100");
        trValues.add("1");

        // Turn Left
        List<String> tlValues = new ArrayList<String>();
        List<String> tlParams = new ArrayList<String>();

        tlParams.add("degree");
        tlParams.add("power");
        tlParams.add("orietation");

        tlValues.add("90");
        tlValues.add("100");
        tlValues.add("0");

        // Stop
        List<String> stopValues = new ArrayList<String>();
        List<String> stopParams = new ArrayList<String>();

        stopParams.add("duration");

        stopValues.add("0");

        // Spin
        List<String> spinValues = new ArrayList<String>();
        List<String> spinParams = new ArrayList<String>();

        spinParams.add("degree");
        spinParams.add("power");

        spinValues.add("360");
        spinValues.add("100");

        // Condition
        List<String> conditionValues = new ArrayList<String>();
        List<String> conditionParams = new ArrayList<String>();

        conditionParams.add("minimumDistance");

        conditionValues.add("100");

        // Loop
        List<String> loopValues = new ArrayList<String>();
        List<String> loopParams = new ArrayList<String>();

        loopParams.add("instructions");
        loopParams.add("loops");

        loopValues.add("3");
        loopValues.add("3");

        // Setting instruction to the blocks
        moveForwardBlock.setInstruction("moveForward", mfParams, mfValues);
        moveBackwardBlock.setInstruction("moveBackward", mbParams, mbValues);
        moveRightBlock.setInstruction("turnRight", trParams, trValues);
        moveLeftBlock.setInstruction("turnLeft", tlParams, tlValues);

        stopBlock.setInstruction("stop", stopParams, stopValues);
        spinBlock.setInstruction("spin", spinParams, spinValues);

        conditionBlock.setInstruction("sensorDistance", spinParams, spinValues);
        loopBlock.setInstruction("loop", loopParams, loopValues);

        blockTypes.add(moveForwardBlock);
        blockTypes.add(moveBackwardBlock);
        blockTypes.add(moveRightBlock);
        blockTypes.add(moveLeftBlock);

        blockTypes.add(stopBlock);
        blockTypes.add(spinBlock);

        blockTypes.add(conditionBlock);
        blockTypes.add(loopBlock);
    }

    public int convertId(int imgId, int typeId){
        int id = 0;
        if(typeId == IMG_TO_BLOCK){
            switch (imgId) {
                case MOVE_FORWARD_IMG:
                    id = MOVE_FORWARD;
                    break;
                case MOVE_BACKWARD_IMG:
                    id = MOVE_BACKWARD;
                    break;
                case TURN_RIGHT_IMG:
                    id = TURN_RIGHT;
                    break;
                case TURN_LEFT_IMG:
                    id = TURN_LEFT;
                    break;
                case STOP_IMG:
                    id = STOP;
                    break;
                case SPIN_IMG:
                    id = SPIN;
                    break;
                case CONDITION_IMG:
                    id = CONDITION;
                    break;
                case LOOP_IMG:
                    id = LOOP;
                    break;
            }
        }
        return id;
    }
}
