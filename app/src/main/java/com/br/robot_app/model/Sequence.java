package com.br.robot_app.model;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Responsable for create a sequence of instruction adding by the blocks
 *
 */
public class Sequence {

    private List<Block> blocks; // The sequence_screen of instructions
    private File sequenceFile;

    private final String FILENAME = "instruction.json";

    public Sequence(){
        sequenceFile = new File(Environment.DIRECTORY_DOCUMENTS,FILENAME);
    }

    /**
     * Build the file that is going to be send
     * @param context app context
     */
    public void buildJSON(Context context){
        try {
            FileOutputStream out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for(Block block : blocks){
                String instruction = block.getInstructions().toString();
                out.write(instruction.getBytes());
                out.close();
            }
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
