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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;

public class JoinCreate extends AppCompatActivity {

//    Button joinButt, createButt;
//    Typeface tfChalk, tfWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_create);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);

//        joinButt = (Button) findViewById(R.id.joinButton);
//        createButt = (Button) findViewById(R.id.createButton);
//
//        tfChalk = Typeface.createFromAsset(getAssets(), "fonts/ChalkDust.tff");
//        joinButt.setTypeface(tfChalk);
//
//        tfWhite = Typeface.createFromAsset(getAssets(), "fonts/ChalkWhite.tff");
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent in;
            switch (item.getItemId()) {
                case R.id.nav_classes:
                    in=new Intent(getBaseContext(),ClassSelectionActivity.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    //return true;
                    break;
                case R.id.nav_map:
                    in=new Intent(getBaseContext(), MapsActivity.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    break;
                //return true;
                case R.id.nav_study_mode:
                    in=new Intent(getBaseContext(), JoinCreate.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.nav_settings:
                    in=new Intent(getBaseContext(), SettingsActivity.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    break;
            }
            return false;
        }

    };

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
