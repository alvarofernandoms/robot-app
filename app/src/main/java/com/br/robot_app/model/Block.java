package com.br.robot_app.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Blocks hold all the instruction in JSON objects as strings
 */
public class Block{

    private int blockId;
    private JSONObject instructions;

    public Block(int id){
        this.blockId = id;
        this.instructions = new JSONObject();
    }

    /**
     * Access method to get the current instructions
     *
     * @return instructions of the block
     */
    public JSONObject getInstructions(){
        return this.instructions;
    }
    public int getBlockId(){ return this.blockId; }

    /**
     * TODO: change the 2 parameters to receve only 1
     * Adding a new instruction on the block
     *
     * @param function name of the function to be executed
     * @param params list of all parameters of the function
     * @param values list of all values releted to the params
     */
    public void addingInstruction(String function, List<String> params, List<String> values) throws JSONException {
        int numParam = params.size();
        LinkedHashMap hashParam = new LinkedHashMap();
        for (int i = 0; i < numParam; i++)
            hashParam.put(params.get(i), values.get(i));
        instructions.put(function, hashParam);
    }
}