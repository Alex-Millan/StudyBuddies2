package com.example.alex.studybuddies;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private final static String KEY_COURSE= "course";
    private final static String KEY_RED = "red";
    private final static String KEY_BLUE = "blue";
    private final static String KEY_GREEN = "green";
    private final static String KEY_COURSE_SIZE = "SavedSize";

    private final static String KEY_JOINED= "course_joined";
    private final static String KEY_POSITION = "position";
    private final static String KEY_TIME = "time";
    private final static String KEY_JOINED_SIZE = "JoinedSize";


    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            int size = settings.getInt(KEY_COURSE_SIZE, 0);
            for (int i = 0; i < size ; i++) {
                String myCourse = settings.getString(KEY_COURSE + i, "null");
                String red = settings.getString(KEY_RED + i, "null");
                String blue = settings.getString(KEY_BLUE + i, "null");
                String green = settings.getString(KEY_GREEN + i, "null");

                HashMap<String, String> temp = new HashMap<>();
                temp.put(KEY_COURSE, myCourse);
                temp.put(KEY_RED, red);
                temp.put(KEY_BLUE, blue);
                temp.put(KEY_GREEN, green);
                instance.courses.add(temp);
            }

            int size2 = settings.getInt(KEY_JOINED_SIZE, 0);
            for (int i = 0; i < size2 ; i++) {
                String course = settings.getString(KEY_JOINED+i, "null");
                String pos = settings.getString(KEY_POSITION+i, "null");
                String time = settings.getString(KEY_TIME+i, "null");
                HashMap<String, String> tempClass = new HashMap<>();
                tempClass.put(KEY_JOINED, course);
                tempClass.put(KEY_POSITION, pos);
                tempClass.put(KEY_TIME, time);
                instance.coursesJoined.add(tempClass);
            }

        }
        return instance;
    }

    public void addClass(String myCourse, String red, String green, String blue) {
        //new entry added will go to the current size of the arraylist
        int i = instance.getSize();
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        //Saves the data into the phone even after we close the app
        editor.putString(KEY_COURSE + i, myCourse);
        editor.putString(KEY_RED + i, red);
        editor.putString(KEY_GREEN + i, green);
        editor.putString(KEY_BLUE + i, blue);

        //Stores the data into the current loaded app
        HashMap<String, String> temp = new HashMap<>();
        temp.put(KEY_COURSE, myCourse);
        temp.put(KEY_RED, red);
        temp.put(KEY_GREEN, green);
        temp.put(KEY_BLUE, blue);
        instance.courses.add(temp);
        editor.putInt(KEY_COURSE_SIZE, instance.getSize());
        editor.commit();
    }
    public void delete(int i){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.remove(KEY_COURSE + i);
        editor.remove(KEY_RED + i);
        editor.remove(KEY_GREEN + i);
        editor.remove(KEY_BLUE + i);

        instance.courses.remove(i);
        editor.putInt(KEY_COURSE_SIZE, instance.getSize());
        editor.commit();
        updateInfo();
    }

    public void updateInfo(){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        for(int i = 0; i < instance.getSize(); i++){
            //Saves the data into the phone even after we close the app
            editor.putString(KEY_COURSE + i, instance.courses.get(i).get(KEY_COURSE));
            editor.putString(KEY_RED + i,  instance.courses.get(i).get(KEY_RED));
            editor.putString(KEY_GREEN + i,  instance.courses.get(i).get(KEY_GREEN));
            editor.putString(KEY_BLUE + i,  instance.courses.get(i).get(KEY_BLUE));
        }
        editor.commit();
    }

    public int getSize(){
        return instance.courses.size();
    }

    public void addStudyGroup(String c, String p, String t) {

        int i = instance.coursesJoined.size();
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_JOINED + i, c);
        editor.putString(KEY_POSITION + i, p);
        editor.putString(KEY_TIME + i, t);
        HashMap<String, String> tempClass = new HashMap<>();
        tempClass.put(KEY_JOINED, c);
        tempClass.put(KEY_POSITION, p);
        tempClass.put(KEY_TIME, t);
        instance.coursesJoined.add(tempClass);
        editor.putInt(KEY_JOINED_SIZE, instance.getHashSize());
        editor.commit();
        Log.i("YOURMUM   ", "Added " + tempClass.toString() + " to app info \n position: " + i );
    }


    public int getHashSize(){
        return instance.coursesJoined.size();
    }



    public void deleteHash(int i){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.remove(KEY_JOINED + i);
        editor.remove(KEY_POSITION + i);
        editor.remove(KEY_TIME + i);
        instance.coursesJoined.remove(i);
        editor.putInt(KEY_JOINED_SIZE, instance.getHashSize());
        editor.commit();
        updateHashInfo();
    }

    public void updateHashInfo(){
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        for(int i = 0; i < instance.getHashSize(); i++){

            //Saves the data into the phone even after we close the app
            editor.putString(KEY_JOINED + i, instance.coursesJoined.get(i).get(KEY_JOINED));
            editor.putString(KEY_POSITION + i,  instance.coursesJoined.get(i).get(KEY_POSITION));
            editor.putString(KEY_TIME + i,  instance.coursesJoined.get(i).get(KEY_TIME));
        }
        editor.commit();
    }

}