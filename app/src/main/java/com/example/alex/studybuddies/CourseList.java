package com.example.alex.studybuddies;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
*   Call this Class to get the full list of classes being offered
*   CourseList myList = new CourseList(this);
*   myList.courses.get(0); //grabs the first course
*   myList.getSize(); //returns the course sizes
*   List is alphabetical order
 */
public class CourseList  {
    public ArrayList<String> courses = new ArrayList<String>();
    private Context my_context;

    public CourseList(Context context) {
        //TODO print out courseList in text
        AssetManager am = context.getAssets();
        InputStream is;
        try {
            is = am.open("Update.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = r.readLine()) != null) {
                courses.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return courses.size();
    }

    class Time {

    }

}
