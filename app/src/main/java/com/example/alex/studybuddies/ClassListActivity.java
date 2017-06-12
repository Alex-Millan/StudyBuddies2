package com.example.alex.studybuddies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Color;


public class ClassListActivity extends AppCompatActivity {

    private static final String TAG = "LOG_TAG";

    String classString;
    String colorString;

    private class ListElement {
        ListElement() {};

        ListElement(String tl, String colorID, String ebl, String dbl) {
            textLabel = tl;
            editButtonLabel = ebl;
            colorLabel = colorID;
            deleteButtonLabel = dbl;
        }

        public String textLabel;
        public String editButtonLabel;
        public String colorLabel;
        public String deleteButtonLabel;
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
            ImageView imageView = (ImageView) newView.findViewById(R.id.icon);

            Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            if(colorString.equals("Red")) {
                paint.setColor(Color.rgb(234,51,12));
            }
            else if(colorString.equals("Orange")) {
                paint.setColor(Color.rgb(242,146,44));
            }
            else if(colorString.equals("Yellow")) {
                paint.setColor(Color.rgb(245,241,27));
            }
            else if(colorString.equals("Green")) {
                paint.setColor(Color.rgb(99,240,13));
            }
            else if(colorString.equals("Blue")) {
                paint.setColor(Color.rgb(13,85,240));
            }
            else if(colorString.equals("Purple")) {
                paint.setColor(Color.rgb(175,13,240));
            }

            canvas.drawCircle(50, 50, 30, paint);
            imageView.setImageBitmap(bitmap);


            Button editButton = (Button) newView.findViewById(R.id.editButton);
            tv.setText(w.textLabel);
            editButton.setText(w.editButtonLabel);
            Button deleteButton = (Button) newView.findViewById(R.id.deleteButton);
            deleteButton.setText(w.deleteButtonLabel);

            // Sets a listener for the button, and a tag for the button as well.
            editButton.setTag(new Integer(position));
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reacts to a button press.
                    String s = v.getTag().toString();
                    Intent intent = new Intent(ClassListActivity.this, ClassSelectionActivity.class);
                    intent.putExtra("course", classString);
                    intent.putExtra("color",colorString);
                    startActivity(intent);
                    adapter.notifyDataSetChanged();
                }
            });

            deleteButton.setTag(new Integer(position));
            deleteButton.setOnClickListener(new View.OnClickListener() {
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
                    adapter.notifyDataSetChanged();
                }
            });

            // Set a listener for the whole list item.
            newView.setTag(w.textLabel);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                }
            });

            return newView;
        }
    }

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
        aList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras.getString("course") != null) {
            classString = extras.getString("course");
            //aList.add(classString);
        }
        if (extras.getString("color") != null) {
            colorString = extras.getString("color");
        }

        aList.add(new ListElement(classString, colorString, "Edit", "Delete"));


        adapter = new MyAdapter(this, R.layout.class_list_layout, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void onClickAddClass(View view) {
        Intent intent = new Intent(ClassListActivity.this, ClassSelectionActivity.class);
        intent.putExtra("course", classString);
        intent.putExtra("color",colorString);
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
                    in=new Intent(getBaseContext(), MapsActivity.class);
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
