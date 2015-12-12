package com.br.robot_app.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import com.br.robot_app.R;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProgramListActivity extends ListActivity {

    private ArrayAdapter<String> adapter;
    private List<String> programsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        programsName = getAllPrograms();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,programsName);
        setListAdapter(adapter);
    }

    private List<String> getAllPrograms(){
        List<String> programs = new ArrayList<String>();
        Context context = getBaseContext();
        File folder = new File(String.valueOf(context.getFilesDir()));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                programs.add(listOfFiles[i].getName());
                Log.d("File: ", listOfFiles[i].getName());
            }
        }
        Log.d("Size", String.valueOf(programs.size()));
        return programs;
    }
}
