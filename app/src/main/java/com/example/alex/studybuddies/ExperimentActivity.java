package com.example.alex.studybuddies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


//Test code here
public class ExperimentActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);


    }
    public void getFile(View v) {
        CourseList course = new CourseList(this);
        TextView tv = (TextView) findViewById(R.id.tv1);
        ClassInfo myclass = new ClassInfo();



    }

}
