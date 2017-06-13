package com.example.alex.studybuddies;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

public class JoinCreate extends AppCompatActivity {

//    Button joinButt, createButt;
//    Typeface tfChalk, tfWhite;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_create);

        final Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"ChalkDust.ttf");
        title = (TextView) findViewById(R.id.title);
        title.setTypeface(myCustomFont);



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
                        Intent intent1 = new Intent(JoinCreate.this, ClassSelectionActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_map:
                        Intent intent2 = new Intent(JoinCreate.this, MapsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_study_mode:

                        break;
                    case R.id.nav_settings:
                        Intent intent3 = new Intent(JoinCreate.this, SettingsActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });


//        joinButt = (Button) findViewById(R.id.joinButton);
//        createButt = (Button) findViewById(R.id.createButton);
//
//        tfChalk = Typeface.createFromAsset(getAssets(), "fonts/ChalkDust");
//        joinButt.setTypeface(tfChalk);
//
//        tfWhite = Typeface.createFromAsset(getAssets(), "fonts/ChalkWhite");
//        createButt.setTypeface(tfWhite);
    }

    public void goToCreate(View V) {

        Intent intent = new Intent(this, CreateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToJoin(View V) {

        // Go to Create activity, dont forget Join Activity
        Intent intent = new Intent(this, Join.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
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


}
