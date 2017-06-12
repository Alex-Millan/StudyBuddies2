package com.example.alex.studybuddies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

// DropDown
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.TextView;

// TimePicker
import android.app.AlertDialog;

import java.lang.String;

public class CreateTest extends AppCompatActivity {

    // DropDown
    private Spinner spinner1, spinner2;
    private Button btnSubmit;

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

    // EditText
    EditText edit;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Drop-Down
//        addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        // TimePicker
        showTimePickerDialog();

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
//        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


    // TimePicker Stuff --------------------------------------------------------------------

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID1){
            return new TimePickerDialog(CreateTest.this, AlertDialog.THEME_HOLO_DARK,
                    kTimePickerListenter1, hour_1, minute_1, false);
        }
        else if(id == DIALOG_ID2){
            return new TimePickerDialog(CreateTest.this, AlertDialog.THEME_HOLO_DARK,
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
                    Toast.makeText(CreateTest.this, displayTime1, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CreateTest.this, displayTime2, Toast.LENGTH_SHORT).show();
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
//                text.setText(name);

                Toast.makeText(CreateTest.this,
                        "DropDown : " + String.valueOf(spinner1.getSelectedItem()) +
                                "\nStart: " + displayTime1 +
                                "\nEnd: " + displayTime2 +
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToMap(View V) {

        // Go to Map activity
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
