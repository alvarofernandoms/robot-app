package com.br.robot_app.model;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.br.robot_app.activity.AlfaActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for create a sequence of instruction adding by the blocks
 *
 */
public class Sequence {

    public List<Block> blocks;
    private File sequenceFile;

    public String fileName;
    public boolean isSaved;

    public Sequence(Context context){
        blocks = new ArrayList<Block>();
        isSaved = false;

        fileName = "instruction" + AlfaActivity.numberOfSequence + ".json";
        sequenceFile = new File(context.getFilesDir(), fileName);
    }

    /**
     * Create a file with a interator from AlfaActivity static var
     * @param context to define the dir path
     */
    public void saveFile(Context context, String progName){
        progName = progName + ".json";
        sequenceFile = new File(context.getFilesDir(), progName);
        try {
            sequenceFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Path:", String.valueOf(context.getFilesDir()));
        Log.d("File:", fileName);
    }

    // TODO: this is just to debug. Remove this later
    public void printFiles(Context context){
        File folder = new File(String.valueOf(context.getFilesDir()));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    /**
     * Build the file that is going to be send
     * @param context app context
     */
    public void buildJSON(Context context){
        FileOutputStream out;
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
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
     * TODO: this is just to debug. Remove this later
     */
    public void showJSONfile(Context context){
        buildJSON(context);
        try(BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir() + "/" + fileName))){
            String line = null;
            while((line = br.readLine()) != null){
                Log.d("Lines: ", line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a new instruction block into the sequence
     *
     * @param newBlock new instruction block
     */
    public void insertBlock(Block newBlock){
        blocks.add(newBlock);
    }

    /**
     * Remove a especific block from the list of blocks
     * @param rmId Id of the block to be removed
     */
    public void removeBlock(int rmId){
        ArrayList<Block> toRemove = new ArrayList<Block>();
        for(Block block : blocks){
            if(block.blockId == rmId) toRemove.add(block);
        }
        blocks.removeAll(toRemove);
    }
}
