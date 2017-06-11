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
    public static ArrayList<String> courses = new ArrayList<String>();
    public ArrayList<String> coursesJoined = new ArrayList<String>();
    //private static final String TEXT_ONE = "one";


    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            int size = settings.getInt("SavedSize", 0);
            courses.add("null");
            for (int i = 0; i < size ; i++) {
                instance.courses.add(settings.getString(courses.get(i), "null"));
            }
            //instance.coursesJoined.add(null);
        }
        return instance;
    }

    public void addClass(String c) {
        int i = instance.courses.size()-1;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        if(i < 0){
            i = 0;
        }
        editor.putInt("SavedSize", courses.size());
        editor.putString(courses.get(i), c);
        editor.commit();
        instance.courses.add(c);
    }

    public void addStudyGroup(String c) {
        instance.coursesJoined.add(c);
    }

    public int getSize(){
        return courses.size();
    }

    public void delete(int i){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(courses.get(i));
        instance.courses.remove(i);
        editor.putInt("SavedSize", instance.getSize());
        editor.commit();

    }

}