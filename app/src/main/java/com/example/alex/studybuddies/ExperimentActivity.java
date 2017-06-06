package com.example.alex.studybuddies;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;


//Test code here
public class ExperimentActivity extends AppCompatActivity {


    DataBase grabber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        grabber = new DataBase();

    }

    public void getFile(View v) {
        CourseList course;
        ClassInfo myClass;
        course = new CourseList(this);
        TextView tv = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.taskcomplete);
        tv.setText("Loading . . . . ");
//        new DownloadFile().execute();
        //tv.setText(grabber.fileContext.toString());
        myClass = new ClassInfo();
        String garbage = "lol";
        try {
            myClass.update("AMS 10", "123", " 321", " 123"," 123","123 ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //startTimer();
        tv.setText("update done!" + course.getSize());


    }


    private class DownloadFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView tv = (TextView) findViewById(R.id.taskcomplete);
            tv.setText(grabber.fileContext.toString());
            String result = grabber.fileContext.toString();
            Toast.makeText(ExperimentActivity.this, "The file is " + result, Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                grabber.grabFile();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while(!grabber.isFileRead()) {}
            return null;
        }
    }
}


