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
    public static ArrayList<HashMap<String, String>> courses = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> coursesJoined = new ArrayList<>();


    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            int size = settings.getInt("SavedSize", 0);
            for (int i = 0; i < size ; i++) {
                String myCourse = settings.getString("course"+i, "null");
                String pos = settings.getString("position"+i, "null");
                String time = settings.getString("time"+i, "null");
                HashMap<String, String> tempClass = new HashMap<>();
                tempClass.put("class", myCourse);
                tempClass.put("position", pos);
                tempClass.put("time", time);
                instance.coursesJoined.add(tempClass);
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
        editor.putString(courses.get(i), c);
        instance.courses.add(c);
        editor.putInt("SavedSize", instance.getSize());
        editor.commit();
    }

    public void addStudyGroup(String c, String p, String t) {
        int i = instance.coursesJoined.size() - 1;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("course"+i, c);
        editor.putString("position" + i, p);
        editor.putString("time" + i, t);
        HashMap<String, String> tempClass = new HashMap<>();
        tempClass.put("class", c);
        tempClass.put("position", p);
        tempClass.put("time", t);
        instance.coursesJoined.add(tempClass);
        editor.putInt("HashSize", instance.getHashSize());
        editor.commit();
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
        i++;
        editor.remove("course"+i);
        editor.remove("position" + i);
        editor.remove("time" + i);
        instance.coursesJoined.remove(i);
        editor.putInt("HashSize", instance.getHashSize());
        editor.commit();

    }

}