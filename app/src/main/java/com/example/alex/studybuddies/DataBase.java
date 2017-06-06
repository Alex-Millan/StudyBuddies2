package com.example.alex.studybuddies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Alex on 6/4/2017.
 */

public class DataBase {
    private StorageReference mStorageRef;
    public ArrayList<String> fileContext = new ArrayList<String>();
    private boolean fileRead = false;
    private File localFile = null;
    DataBase() {

        mStorageRef = FirebaseStorage.getInstance().getReference();

        try {
            localFile = File.createTempFile("Empty", "File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void grabFile() throws FileNotFoundException {

        StorageReference riversRef = mStorageRef.child("StudyList.json");
        fileRead = false;
        String temp = "failed # 1";
        if (localFile != null) {
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            Log.d(TAG, "grabed new file in database");

                            readFile();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    failed();
                    Log.d(TAG, "failed to grab new file in database");

                    // Handle failed download
                    // ...
                }
            });
        }


    }
    public boolean isFileRead() {
        return fileRead;
    }
    public File getLocalFile(){
        return localFile;
    }
    private void failed() {
        fileContext.add("FAILED happen but still something");
    }
    private void readFile() {
        //fileContext.add("readFile() was called");
        FileReader readFile = null;
        try {
            readFile = new FileReader(localFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            BufferedReader r = new BufferedReader(readFile);

            String line;
            while ((line = r.readLine()) != null) {
                fileContext.add(line);
            }
        } catch (IOException e) {
            fileContext.add("Failed to read file");
            e.printStackTrace();
        }
        fileRead = true;

    }
}
