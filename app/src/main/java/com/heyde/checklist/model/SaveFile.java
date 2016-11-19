package com.heyde.checklist.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Daniel on 11/4/2016.
 */

public class SaveFile extends AsyncTask<TaskList, Void, Void> {

    private Context mContext;

    public SaveFile(Context context) {
        mContext = context;
    }

    @Override

    protected Void doInBackground(TaskList... tList) {
        TaskList list = tList[0];// varargs? research more into this
        FileWriter mFileWriter = null;
        if (!(!list.getNameChanged() && list.getTasks().size() == 0)) { // if name hasnt been changed and list is empty, don't save

            File directory = new File(mContext.getFilesDir() + File.separator + "lists"); // list files will be stored in data/data/com.heyde.checklist/files/lists
            File textFile = new File(directory + File.separator + list.getName() + ".txt");
            try {

                textFile.createNewFile();

                mFileWriter = new FileWriter(textFile);
                for (Task task : list.getTasks()) {
                    mFileWriter.write(task.getTaskText() + ":::" + task.isChecked() + "\n");
                    Log.i("WRITING", task.getTaskText() + ":::" + task.isChecked());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    mFileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

