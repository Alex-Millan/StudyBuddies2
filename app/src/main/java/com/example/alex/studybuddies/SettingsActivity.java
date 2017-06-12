package com.example.alex.studybuddies;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.lang.reflect.Field;


public class SettingsActivity extends AppCompatActivity {

    private SeekBar radiusBar;
    private TextView radiusNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        disableShiftMode(navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_classes:
                        Intent intent1 = new Intent(SettingsActivity.this, ClassSelectionActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_map:
                        Intent intent2 = new Intent(SettingsActivity.this, MapsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_study_mode:
                        Intent intent3 = new Intent(SettingsActivity.this, Join.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_settings:

                        break;
                }
                return false;
            }
        });

        radiusBar = (SeekBar) findViewById(R.id.radiusBar);
        radiusNum = (TextView) findViewById(R.id.radiusNumber);
        radiusNum.setText("Radius: " + radiusBar.getProgress());
        radiusBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                radiusNum.setText("Radius: " + radiusBar.getProgress());
            }
        });
    }

    public void GoToEditAccount(View V){
        Intent intent = new Intent(this, ClassSelectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent in;
            switch (item.getItemId()) {
                case R.id.nav_classes:
                    //item.setChecked(true);
                    in=new Intent(getBaseContext(),ClassSelectionActivity.class);
                    startActivity(in);
                    //overridePendingTransition(0, 0);
                    return true;
                    //break;
                case R.id.nav_map:
                    in=new Intent(getBaseContext(), MapsActivity.class);
                    startActivity(in);
                    //overridePendingTransition(0, 0);
                    return true;
                    //item.setChecked(true);
                    //break;
                case R.id.nav_study_mode:
                    in=new Intent(getBaseContext(), Join.class);
                    startActivity(in);
                    //overridePendingTransition(0, 0);
                    return true;
                    //item.setChecked(true);
                    //break;
                case R.id.nav_settings:
                    in=new Intent(getBaseContext(), SettingsActivity.class);
                    startActivity(in);
                    //overridePendingTransition(0, 0);
                    return true;
                    //item.setChecked(true);
                    //break;

            }
            return false;
            //startActivity(in);
        }

    };*/



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_classes:
                    Intent First= new Intent(SettingsActivity.this, ClassSelectionActivity.class);
                    startActivity(First);
                    break;
                case R.id.nav_settings:

                    break;
            }

            return false;
        }
    };

}
