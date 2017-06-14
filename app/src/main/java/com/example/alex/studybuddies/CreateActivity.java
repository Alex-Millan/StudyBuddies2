package com.example.alex.studybuddies;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TimePicker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;
import java.lang.String;

// DropDown
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.TextView;

// TimePicker
import android.app.AlertDialog;

import java.io.FileNotFoundException;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CreateActivity extends AppCompatActivity {

    TextView title;
    TextView classText;
    TextView startText;
    TextView endText;
    TextView locationText;

    Typeface myCustomFont;

    // DropDown
    private Spinner spinner1;
    private Button btnSubmit;
    String course;

    // TimePicker Stuff
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    Button button_stpd1;
    Button button_stpd2;
    static final int DIALOG_ID1 = 0;
    int hour_1;
    int minute_1;
    static final int DIALOG_ID2 = 1;
    int hour_2;
    int minute_2;
    String displayTime1;
    String displayTime2;
    String latty;
    String longy;

    // EditText
    EditText edit;
    String name;

    // ClassInfo
    ClassInfo classStuff;

    //AppInfo
    AppInfo appInfo;
    int numClassesJoined;
//    int numberOfClasses = appInfo.getSize();
    //String i = appInfo.get(i).get("course");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"ChalkDust.ttf");
        title = (TextView) findViewById(R.id.title);
        classText = (TextView) findViewById(R.id.classTextView);
        startText = (TextView) findViewById(R.id.startTimeTextView);
        endText = (TextView) findViewById(R.id.endTimeTextView);
        locationText = (TextView) findViewById(R.id.locationTextView);

        title.setTypeface(myCustomFont);
        classText.setTypeface(myCustomFont);
        startText.setTypeface(myCustomFont);
        endText.setTypeface(myCustomFont);
        locationText.setTypeface(myCustomFont);

        latty = "null";
        longy = "null";
        Bundle change = getIntent().getExtras();
        if (change == null) {
        } else {
            latty = change.getString("latitude");
            longy = change.getString("longitude");
        }

        // Drop-Down
//        addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        // TimePicker
        showTimePickerDialog();

        // ClassInfo
        classStuff = new ClassInfo();

        myCustomFont = Typeface.createFromAsset(getAssets(),"ChalkDust.ttf");

        // AppInfo
        appInfo = AppInfo.getInstance(this);

        // Navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        disableShiftMode(navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_classes:
                        Intent intent1 = new Intent(CreateActivity.this, ClassSelectionActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_map:
                        Intent intent2 = new Intent(CreateActivity.this, MapsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_study_mode:

                        break;
                    case R.id.nav_settings:
                        Intent intent3 = new Intent(CreateActivity.this, SettingsActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

    }

    // DropDown Stuff --------------------------------------------------------------------

    // add items into spinner dynamically
//    public void addItemsOnSpinner2() {
//
//        spinner2 = (Spinner) findViewById(R.id.spinner2);
//        List<String> list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(dataAdapter);
//    }

    public void addListenerOnSpinnerItemSelection() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        int i;
        int numClassesJoined = appInfo.courses.size();
        String[] classArray = new String[numClassesJoined];


        if(numClassesJoined > 0) {

            for (i = 0; i < numClassesJoined; i++) {
                classArray[i] = appInfo.courses.get(i).get("course");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, classArray);
        spinner1.setAdapter(adapter);
    }


    // TimePicker Stuff --------------------------------------------------------------------

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID1){
            return new TimePickerDialog(CreateActivity.this, AlertDialog.THEME_HOLO_DARK,
                    kTimePickerListenter1, hour_1, minute_1, false);
        }
        else if(id == DIALOG_ID2){
            return new TimePickerDialog(CreateActivity.this, AlertDialog.THEME_HOLO_DARK,
                    kTimePickerListenter2, hour_2, minute_2, false);
        }
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListenter1 =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute){

                    hour_1 = hourOfDay;
                    minute_1 = minute;
                    displayTime1 = formatTime(hourOfDay, minute_1);

                    Button btnStartTime = (Button) findViewById(R.id.startTimeButton);
                    btnStartTime.setText(displayTime1);
                    Toast.makeText(CreateActivity.this, displayTime1, Toast.LENGTH_SHORT).show();
                }
            };

    protected TimePickerDialog.OnTimeSetListener kTimePickerListenter2 =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute){

                    hour_2 = hourOfDay;
                    minute_2 = minute;
                    displayTime2 = formatTime(hour_2, minute_2);

                    Button btnEndTime = (Button) findViewById(R.id.endTimeButton);
                    btnEndTime.setText(displayTime2);
                    Toast.makeText(CreateActivity.this, displayTime2, Toast.LENGTH_SHORT).show();
                }
            };

    public void showTimePickerDialog(){
        button_stpd1 = (Button) findViewById(R.id.startTimeButton);
        button_stpd1.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View V){
                        showDialog(DIALOG_ID1);
                    }
                }
        );
        button_stpd2 = (Button) findViewById(R.id.endTimeButton);
        button_stpd2.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View V){
                        showDialog(DIALOG_ID2);
                    }
                }
        );
    }



    // Custom Stuff --------------------------------------------------------------------

    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //DropDown
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        // TimePickers
        displayTime1 = formatTime(hour_1, minute_1);
        displayTime2 = formatTime(hour_2, minute_2);

        // EditText
        edit = (EditText) findViewById(R.id.editText);
//        text = (TextView) findViewById(R.id.textView);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // getting text from edittext
                name = edit.getText().toString();

                if(name.equals("")){
                    name = "No Description";
                }

//                text.setText(name);
                course = String.valueOf(spinner1.getSelectedItem());


                // ClassInfo
                String tempCourse = "AMS 10";
                // Temp long and lat values
                String lat = latty;
                String longi = longy;

                try {
                    classStuff.update(tempCourse, lat, longi, displayTime1, displayTime2, name);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                (String course, String lat, String longi, String startTime, String endTime, String desc)


                Toast.makeText(CreateActivity.this,
                        "DropDown : " + course +
                                "\nStart: " + displayTime1 +
                                "\nEnd: " + displayTime2 +
                                "\nLat: " + lat +
                                "\nLong: " + longi +
                                "\nDescription: " + name,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private static String formatTime(int hour, int minute){

        String meridiem, displayTime;

        if(hour < 12) {
            meridiem = "AM";
        } else {
            meridiem = "PM";
        }

        displayTime = "";
        if(hour%12 == 0){
            displayTime = displayTime.concat("12")
                    .concat(":").concat(pad(minute)).concat(" ").concat(meridiem);
        }
        else{
            displayTime = displayTime.concat(Integer.toString(hour%12))
                    .concat(":").concat(pad(minute)).concat(" ").concat(meridiem);
        }
        return displayTime;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, JoinCreate.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToMap(View V) {

        // Go to Map activity
        Intent intent2 = new Intent(this, MapsActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.putExtra("draggable",true);
        startActivity(intent2);
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


}
