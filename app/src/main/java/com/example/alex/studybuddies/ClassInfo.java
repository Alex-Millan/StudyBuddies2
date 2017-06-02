package com.example.alex.studybuddies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will read and write data into the server
 *
 * Created by Alex on 5/19/2017.
 */

class ClassInfo {


    private ArrayList<HashMap<String, String>> studyList;
    private StorageReference mStorageRef;
    private StorageReference riversRef ;
    Location loc;
    Time startTime;
    Time endTime;

    private String TAG = "UPLOADING . . .";
    //Constructor retrieves data from server

    private String temp = "";  //Attempt to return a simple string

    ClassInfo() {
        //TODO intitalize all global Strings to zero
        mStorageRef = FirebaseStorage.getInstance().getReference();
        loc = new Location();
        startTime = new Time("start_time");
        endTime = new Time("end_time");
    }


    //Initialize the json list.
    public void getCourse(String courseName, Context context) {

        String jsonFile = this.getStudyTimes(context);
        JSONArray arr = null;
        JSONObject obj = null;

        try {
            arr = new JSONObject(jsonFile).getJSONArray(courseName);
            for(int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                String longitude = obj.getString("longitude");
                String lattiude = obj.getString("latitude");
                String start = obj.getString("start_time");
                String end = obj.getString("end_time");

                HashMap<String, String> temp = new HashMap<>();

                // adding each child node to HashMap key => value
                temp.put("longitude", longitude);
                temp.put("latitude", lattiude);
                temp.put("start_time", start);
                temp.put("end_time", end);

                studyList.add(temp);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int getCourseSize() {
        return studyList.size();
    }
    public String getStudyTimes(Context context){
        String json = null;
        try {

            InputStream is = context.getAssets().open("DummyText.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void grabFile() {
        File localFile = null;
        StorageReference riversRef = mStorageRef.child("Upload.txt");
        try {
            localFile = File.createTempFile("Upload", "txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (localFile != null) {
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        }

    }

    public void update() {

        String userName = "Username: ";
        String courseName = "Course: ";
        String location = "Location: ";
        String startTime = "Start time: ";
        String endTime = "End time: ";


        String studyPlan = userName + "\r\n\t" +
                            courseName + "\r\n\t" +
                            location + "\r\n\t" +
                            startTime+ "\r\n\t" +
                            endTime+ "\r\n" + "\r\n";
        System.out.println(studyPlan);
        byte[] bits = studyPlan.getBytes();
        riversRef = mStorageRef.child("Updated.txt");
        riversRef.putBytes(bits).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                Log.d(TAG, "EZ PZ LMN SQEZ");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.d(TAG, "Ahh yess A challenge worth figuring out");
                    }
                });
    }



    String getTemp() {
        return temp;
    }

    class Location {
        private double longitude;
        private double lattidue;
        Location() {
            longitude = 0;
            lattidue = 0;
        }

        public void setLongitude(double longi){
            longitude = longi;
        }
        public void setLattidue(double lati){
            lattidue = lati;
        }
        public double getLongitude(int index) {
            String convert = studyList.get(index).get("longitude");
            double longitude = Double.parseDouble(convert);
            return longitude;
        }
        public double getLattidue(int index) {
            String convert = studyList.get(index).get("lattidue");
            double lattidue = Double.parseDouble(convert);

            return lattidue;
        }
    }

    class Time {
        private int hour;
        private int minute;
        private String myTime;
        Time(String time) {
            myTime = time;
        }

        public void setTime(int time){
            hour = time/60;
            minute= time%60;
        }
        public int getHour(int index) {
            String convert = studyList.get(index).get(myTime);
            int hour = Integer.parseInt(convert);
            return hour;
        }
        public int getMinute(int index) {
            String convert = studyList.get(index).get(myTime);
            int minute = Integer.parseInt(convert);
            return minute;
        }
    }
}
