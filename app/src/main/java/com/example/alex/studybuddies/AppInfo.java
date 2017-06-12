package com.example.alex.studybuddies;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static ArrayList<HashMap<String, String>> coursesJoined = new ArrayList<>();


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

            int size2 = settings.getInt("HashSize", 0);
            for (int i = 0; i < size2 ; i++) {
                String course = settings.getString("course"+i, "null");
                String pos = settings.getString("position"+i, "null");
                String time = settings.getString("time"+i, "null");
                HashMap<String, String> tempClass = new HashMap<>();
                tempClass.put("class", course);
                tempClass.put("position", pos);
                tempClass.put("time", time);
                instance.coursesJoined.add(tempClass);
            }

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

    public void addStudyGroup(String c, String p, String t) {
        int i = instance.coursesJoined.size()-1;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("course"+i, c);
        editor.putString("position" + i, p);
        editor.putString("time" + i, t);
        editor.putInt("HashSize", coursesJoined.size());
        editor.commit();
        HashMap<String, String> tempClass = new HashMap<>();
        tempClass.put("class", c);
        tempClass.put("position", p);
        tempClass.put("time", t);
        instance.coursesJoined.add(tempClass);
    }

    public int getSize(){
        return courses.size();
    }

    public int getHashSize(){
        return coursesJoined.size();
    }

    public void delete(int i){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(courses.get(i));
        instance.courses.remove(i);
        editor.putInt("SavedSize", instance.getSize());
        editor.commit();

    }

    public void deleteHash(int i){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("course"+i);
        editor.remove("position" + i);
        editor.remove("time" + i);
        instance.coursesJoined.remove(i);
        editor.putInt("HashSize", instance.getHashSize());
        editor.commit();

    }

}