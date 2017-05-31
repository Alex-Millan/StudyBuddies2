package com.example.alex.studybuddies;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//Test code here
public class ExperimentActivity extends AppCompatActivity {


    private StorageReference mStorageRef;
    StorageReference riversRef ;
    final String TAG = "Experiment: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }
    public void getFile(View v) {
        CourseList course = new CourseList(this);
        TextView tv = (TextView) findViewById(R.id.tv1);
        ClassInfo myclass = new ClassInfo();
        tv.setText(course.courses.get(10));
    }

}
