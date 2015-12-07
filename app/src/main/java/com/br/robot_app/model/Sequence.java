package com.br.robot_app.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for create a sequence of instruction adding by the blocks
 *
 */
public class Sequence {

    private List<Block> blocks; // The sequence_screen of instructions
    private File sequenceFile;

    private final String FILENAME = "instruction.json";

    public Sequence(Context context){
        blocks = new ArrayList<Block>();
        sequenceFile = new File(context.getFilesDir(), FILENAME);
    }

    /**
     * Build the file that is going to be send
     * @param context app context
     */
    public void buildJSON(Context context){
        FileOutputStream out;
        try {
            out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            // TODO: the var with flag fixJSONBuild should be removed and add a improvement way to fix JSONbuild
            out.write("{".getBytes()); // fixJSONBuild
            int count = 0;
            for(Block block : blocks) {
                String instruction = block.getInstructions().toString();
                instruction = instruction.substring(1); // fixJSONBuild
                instruction = instruction.substring(0,instruction.length()-1); // fixJSONBuild
                count++;
                if(blocks.size() != count){
                    instruction = instruction + ",";
                }
                out.write(instruction.getBytes());
            }
            out.write("}".getBytes()); // fixJSONBuild
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get method thats returns a sequence file
     * The sequence returned depends on buildJSON method
     *
     * @return sequenceFile builded by the class
     */
    public File getSequence(){
        return this.sequenceFile;
    }

    /**
     * Insert a new instruction block into the sequence
     *
     * @param newBlock new instruction block
     */
    public void insertBlock(Block newBlock){
        blocks.add(newBlock);
    }

}
