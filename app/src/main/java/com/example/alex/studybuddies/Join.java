package com.example.alex.studybuddies;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Join extends AppCompatActivity{

    private static final String LOG_TAG = "lv-ex";
    AppInfo appInfo;


    private class ListElement {
        ListElement() {};

        ListElement(String tl, String t2, String bl) {
            textLabel = tl;
            textLabel2 = t2;
            buttonLabel = bl;
        }

        public String textLabel;
        public String textLabel2;
        public String buttonLabel;
    }

    private ArrayList<ListElement> aList;

    private class MyAdapter extends ArrayAdapter<ListElement> {

        int resource;
        Context context;

        public MyAdapter(Context _context, int _resource, List<ListElement> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout newView;

            ListElement w = getItem(position);

            // Inflate a new view if necessary.
            if (convertView == null) {
                newView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (LinearLayout) convertView;
            }

            // Fills in the view.
            TextView tv = (TextView) newView.findViewById(R.id.itemText);
            TextView tv2 = (TextView) newView.findViewById(R.id.itemText2);
            Button b = (Button) newView.findViewById(R.id.itemButton);
            tv.setText(w.textLabel);
            tv2.setText(w.textLabel2);
            b.setText(w.buttonLabel);

            // Sets a listener for the button, and a tag for the button as well.
            b.setTag(new Integer(position));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reacts to a button press.
                    // Gets the integer tag of the button.
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                    // Let's remove the list item.
                    int i = Integer.parseInt(s);
                    aList.remove(i);
                    appInfo.deleteHash(i);
                    aa.notifyDataSetChanged();

                }
            });

            // Set a listener for the whole list item.
            newView.setTag(w.textLabel);
            newView.setTag(w.textLabel2);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                    String i[] = s.split(" ");
                    int index = Integer.parseInt(i[1])-1;
                    String temp = appInfo.coursesJoined.get(index).get("position");
                    ChangetoMaps(temp);

                }
            });

            return newView;
        }
    }


    public void ChangetoMaps(String location){
        Intent intent2 = new Intent(this, MapsActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.putExtra("flag",true);
        intent2.putExtra("location",location);
        startActivity(intent2);
    }

    private MyAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        aList = new ArrayList<ListElement>();
        aa = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();
        appInfo = AppInfo.getInstance(this);
        for (int i = 1; i < appInfo.getHashSize(); i++) {
            if(appInfo.coursesJoined.get(i).get("course_joined") != "null") {
                aList.add(new ListElement(
                        appInfo.coursesJoined.get(i).get("course_joined"), appInfo.coursesJoined.get(i).get("time"), "Delete"
                ));
            }
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
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

/*
    public void clickRefresh (View v) {
        Log.i(LOG_TAG, "Requested a refresh of the list");
        Random rn = new Random();
        SecureRandomString srs = new SecureRandomString();
        // How long a list do we make?
        int n = 4 + rn.nextInt(10);
        // Let's fill the array with n random strings.
        // NOTE: aList is associated to the array adapter aa, so
        // we cannot do here aList = new ArrayList<ListElement>() ,
        // otherwise we create another ArrayList which would not be
        // associated with aa.
        // aList = new ArrayList<ListElement>(); --- NO
        aList.clear();
        for (int i = 0; i < n; i++) {
            aList.add(new ListElement(
                    srs.nextString(), "Delete"
            ));
        }
        // We notify the ArrayList adapter that the underlying list has changed,
        // triggering a re-rendering of the list.
        aa.notifyDataSetChanged();
    }*/

}