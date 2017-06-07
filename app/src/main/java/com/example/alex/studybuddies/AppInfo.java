package com.example.alex.studybuddies;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca on 18/1/2016.
 */
public class AppInfo {

    private static AppInfo instance = null;

    protected AppInfo() {
        // Exists only to defeat instantiation.
    }

    // Here are some values we want to keep global.
    public ArrayList<String> courses = new ArrayList<String>();
    public ArrayList<String> coursesJoined = new ArrayList<String>();
    private static final String TEXT_ONE = "one";
    private static final String TEXT_TWO = "two";
    private static final String TEXT_THREE = "three";

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            instance.courses.add(settings.getString(TEXT_ONE, "null"));
            //instance.coursesJoined.add(null);
        }
        return instance;
    }

    public void addClass(String c) {
        instance.courses.add(c);
    }

    public void addStudyGroup(String c) {
        instance.coursesJoined.add(c);
    }

}

