package com.example.alex.studybuddies;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.lang.reflect.Field;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.widget.Toast;
import java.util.ArrayList;
import android.widget.TextView;


public class ClassSelectionActivity extends AppCompatActivity {

    AppInfo appInfo;

    TextView title;

    Spinner coursesDropdown;
    Spinner colorDropdown;
    String[] subjectItems;
    String selectedCourseNumber;
    String selectedClass;
    String selectedColor;

    String rgb;
    String rValue;
    String bValue;
    String gValue;

    CourseList myCourseList;

    ArrayList<String> list;

    private static final String TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);
        myCourseList = new CourseList(this);
        appInfo = AppInfo.getInstance(this);

        title = (TextView) findViewById(R.id.title);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"ChalkDust.ttf");
        title.setTypeface(myCustomFont);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        disableShiftMode(navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_classes:
                        break;
                    case R.id.nav_map:
                        Intent intent1 = new Intent(ClassSelectionActivity.this, MapsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_study_mode:
                        Intent intent2 = new Intent(ClassSelectionActivity.this, JoinCreate.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_settings:
                        Intent intent3 = new Intent(ClassSelectionActivity.this, SettingsActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });


        coursesDropdown = (Spinner) findViewById(R.id.coursesSpinner);
        colorDropdown = (Spinner) findViewById(R.id.colorSpinner);

        /*Bundle extras = getIntent().getExtras();
        if (extras.getString("course") != null) {
            String classString = extras.getString("course");
        }
        if (extras.getString("color") != null) {
            String colorString = extras.getString("color");
        }*/

        String[] list = new String[myCourseList.getSize()+1];
        int totalSize = myCourseList.getSize();
        //Log.d(TAG, "list: " + totalSize);
        list[0] = "[Select a course]";

        for (int i = 0; i < myCourseList.getSize(); i++) {
            list[i+1] = myCourseList.courses.get(i);
        }

        int arraySize = list.length;
        //Log.d(TAG, "array: " + arraySize);

        for(int j = 0; j < list.length; j++) {
            //Log.d(TAG, "index: " + j + " subject: " + list[j]);
        }

        coursesDropdown = (Spinner) findViewById(R.id.coursesSpinner);
        subjectItems = list;
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, subjectItems);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coursesDropdown.setAdapter(adapter1);
        coursesDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedClass = adapterView.getItemAtPosition(i).toString();
                selectedClass = adapterView.getItemAtPosition(i).toString();
                if (selectedClass.equals("[Select a course]")) {
                }
                else{
                    Toast.makeText(getApplicationContext(), "Selected Course: " + selectedClass, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        colorDropdown = (Spinner) findViewById(R.id.colorSpinner);
        String[] colors = new String[]{"[Select a color]", "Red","Orange","Yellow","Green","Blue","Purple"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, colors);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorDropdown.setAdapter(adapter2);
        colorDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedColor = adapterView.getItemAtPosition(i).toString();
                if(selectedColor.equals("Red")) {
                    rgb = "234,51,12";
                    rValue = "234";
                    gValue = "51";
                    bValue = "12";
                }
                else if(selectedColor.equals("Orange")) {
                    rgb = "242,146,44";
                    rValue = "242";
                    gValue = "146";
                    bValue = "44";
                }
                else if(selectedColor.equals("Yellow")) {
                    rgb = "245,241,27";
                    rValue = "245";
                    gValue = "241";
                    bValue = "27";
                }
                else if(selectedColor.equals("Green")) {
                    rgb = "99,240,13";
                    rValue = "99";
                    gValue = "240";
                    bValue = "13";
                }
                else if(selectedColor.equals("Blue")) {
                    rgb = "13,85,240";
                    rValue = "13";
                    gValue = "85";
                    bValue = "240";
                }
                else if(selectedColor.equals("Purple")) {
                    rgb = "175,13,240";
                    rValue = "175";
                    gValue = "13";
                    bValue = "240";
                }
                if (selectedColor.equals("[Select a color]")) {

                }
                else{
                    Toast.makeText(getApplicationContext(), "Selected Color: " + selectedColor, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            //Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public void onClickAddClass(View view) {
        if (selectedClass.equals("[Select a course]")) {
            Toast.makeText(ClassSelectionActivity.this, "Please choose a course",
                    Toast.LENGTH_LONG).show();
        } else if (selectedColor.equals("[Select a color]")) {
            Toast.makeText(ClassSelectionActivity.this, "Please choose a color",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(ClassSelectionActivity.this, ClassListActivity.class);
            intent.putExtra("course", selectedClass);
            intent.putExtra("color",selectedColor);
            startActivity(intent);
        }
        appInfo.addClass(selectedClass,rValue,gValue,bValue);
    }
}
