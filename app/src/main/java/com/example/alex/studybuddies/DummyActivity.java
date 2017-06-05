package com.example.alex.studybuddies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DummyActivity extends AppCompatActivity {
    ClassInfo myClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        myClass = new ClassInfo();


    }

    public void getString(View v) {
        TextView tv = (TextView) findViewById(R.id.dummyText);
                tv.setText(myClass.getTemp());
    }
}
