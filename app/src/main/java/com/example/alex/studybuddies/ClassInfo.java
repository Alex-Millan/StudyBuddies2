package com.example.alex.studybuddies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * For now the only courses available are "Abigail" and "YourMom101"
 * Each course has a fix 4 items (which include longitude, lattitude, starttime, endtime).
 * here is a sample code of how to use this class
 * ClassInfo abigail = new ClassInfo();
 * abigail.getCourse("Abigail", this); //Initialize the course list to read from
 * abigail.getCourseSize();     //Returns the number of items in the list (should be 4 rn)
 * abigail.startTime.getHour(0); // Returns the hour of the first item in the list
 * abigail.loc.getLongitude(0); // returns longitude of the first item in the list as a double
 *
 *
 * To update Study time
    ClassInfo myStudyTime = new ClassInfo();
    myStudyTime.update()



 *TODO add color in Appinfo
 * Created by Alex on 5/19/2017.
 */

class ClassInfo {

    private Context context;
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
        studyList = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        loc = new Location();
        startTime = new Time("start_time");
        endTime = new Time("end_time");
        updateFile = new DataBase();
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
                String desc = obj.getString("description");
                HashMap<String, String> temp = new HashMap<>();

                // adding each child node to HashMap key => value
                temp.put("longitude", longitude);
                temp.put("latitude", lattiude);
                temp.put("start_time", start);
                temp.put("end_time", end);
                temp.put("description", desc);
                if(Double.parseDouble(longitude) != 0 && Double.parseDouble(longitude) != 0) {
                    studyList.add(temp);
                }

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


    String getTemp() {
        return temp;
    }

    class Location {
        private double longitude;
        private double latitude;
        private String description;
        Location() {
            longitude = 0;
            latitude = 0;
            description = "";
        }

        public double getLongitude(int index) {
            String convert = studyList.get(index).get("longitude");
            longitude = Double.parseDouble(convert);
            return longitude;
        }
        public double getLattidue(int index) {
            String convert = studyList.get(index).get("latitude");
            latitude = Double.parseDouble(convert);
            return latitude;
        }
        public String getDescription(int index) {
            return studyList.get(index).get("description");
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
            int hour = Integer.parseInt(convert)/60;
            return hour;
        }
        public int getMinute(int index) {
            String convert = studyList.get(index).get(myTime);
            int minute = Integer.parseInt(convert)%60;
            return minute;
        }
    }

    DataBase updateFile;
    private String courseName;
    private String newLat;
    private String newLong;
    private String newStart;
    private String newEnd;
    private String description;
    public void update(String course, String lat, String longi, String startTime, String endTime, String desc) throws FileNotFoundException {
        courseName = course ;
        newLat = lat;
        newLong = longi;
        newStart = startTime;
        newEnd = endTime;
        description = desc;
        new DownloadFile().execute();
    }


    private class DownloadFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            File update = updateFile.getLocalFile();
            InputStream stream = null;
            try {
                stream = new FileInputStream(update);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
                String json = null;

            JSONArray arr = null;
            JSONObject updateJson= null;
                try {
                    int size = stream.available();
                    byte[] buffer = new byte[size];
                    stream.read(buffer);
                    json = new String(buffer, "UTF-8");
                    try {
                        arr = new JSONObject(json).getJSONArray(courseName);

                        JSONObject temp = new JSONObject();
                        temp.put("latitude", newLat);
                        temp.put("longitude", newLong);
                        temp.put("start_time", newStart);
                        temp.put("end_time", newEnd);
                        temp.put("description", description);
                        arr.put(temp);
                        updateJson = new JSONObject(json);
                        updateJson.put(courseName, arr);
                    } catch (JSONException e) {


                        e.printStackTrace();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                   }
            if(arr != null){
                stream = new ByteArrayInputStream(updateJson.toString().getBytes());
            }else {
                JSONObject addCourse = null;
                try {
                    addCourse = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray temp = new JSONArray();
                JSONObject initialize = new JSONObject();
                try {
                    initialize.put("latitude", 0);
                    initialize.put("longitude", 0);
                    initialize.put("start_time", 0);
                    initialize.put("end_time", 0);
                    initialize.put("description", 0);
                    temp.put(initialize);
                    addCourse.put(courseName,temp);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                stream = new ByteArrayInputStream(addCourse.toString().getBytes());
            }
            riversRef = mStorageRef.child("StudyList.json");
            riversRef.putStream(stream).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
        @Override
        protected String doInBackground(String... params) {
            try {
                updateFile.grabFile();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while(!updateFile.isFileRead()){
                //Wait until file is read
            }
            return null;
        }
    }

}
