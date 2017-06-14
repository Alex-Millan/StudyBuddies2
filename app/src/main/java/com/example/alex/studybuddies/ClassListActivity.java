package com.example.alex.studybuddies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
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
    ImageView imageView;
    AppInfo appInfo;
    TextView title;

    Typeface myCustomFont;

    private class ListElement {
        ListElement() {};

        ListElement(String tl, String colorID, String dbl) {
            textLabel = tl;
            colorLabel = colorID;
            deleteButtonLabel = dbl;
        }

        public String textLabel;
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
            imageView = (ImageView) newView.findViewById(R.id.icon);

            drawCircle(position);

            tv.setText(w.textLabel);
            Button deleteButton = (Button) newView.findViewById(R.id.deleteButton);
            deleteButton.setText(w.deleteButtonLabel);

            tv.setTypeface(myCustomFont);
            tv.setTextColor(Color.rgb(250,250,250));

            // Sets a listener for the button, and a tag for the button as well.

            deleteButton.setTag(new Integer(position));
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reacts to a button press.
                    // Gets the integer tag of the button.
                    String s = v.getTag().toString();
                    //int duration = Toast.LENGTH_SHORT;
                    //Toast toast = Toast.makeText(context, s, duration);
                    //toast.show();
                    // Let's remove the list item.
                    int i = Integer.parseInt(s);
                    aList.remove(i);
                    appInfo.delete(i);
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
        appInfo = AppInfo.getInstance(this);


        myCustomFont = Typeface.createFromAsset(getAssets(),"ChalkDust.ttf");
        title = (TextView) findViewById(R.id.title);
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
                        Intent intent1 = new Intent(ClassListActivity.this, ClassSelectionActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_map:
                        Intent intent2 = new Intent(ClassListActivity.this, MapsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_study_mode:
                        Intent intent3 = new Intent(ClassListActivity.this, JoinCreate.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_settings:
                        Intent intent4 = new Intent(ClassListActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });

        aList = new ArrayList<>();

        for(int i = 0; i < appInfo.getSize(); i++) {
            if(appInfo.courses.get(i).get("course") != "null") {
                aList.add(new ListElement(
                        appInfo.courses.get(i).get("course"), appInfo.courses.get(i).get("red") + appInfo.courses.get(i).get("blue") +
                        appInfo.courses.get(i).get("green"), "Delete"
                ));
            }
        }


        adapter = new MyAdapter(this, R.layout.class_list_layout, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void drawCircle(int i) {
        int red;
        int green;
        int blue;
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        String redS = appInfo.courses.get(i).get("red");
        String greenS = appInfo.courses.get(i).get("green");
        String blueS = appInfo.courses.get(i).get("blue");
        Log.d(TAG,"size: "+appInfo.getSize());
        Log.d(TAG, "index: "+i);
        Log.d(TAG,"R: "+ redS);
        Log.d(TAG,"G: "+ greenS);
        Log.d(TAG,"B: "+ blueS);
        red = Integer.parseInt(appInfo.courses.get(i).get("red"));
        green = Integer.parseInt(appInfo.courses.get(i).get("green"));
        blue = Integer.parseInt(appInfo.courses.get(i).get("blue"));
        paint.setColor(Color.rgb(red, green, blue));
        canvas.drawCircle(50, 50, 30, paint);
        imageView.setImageBitmap(bitmap);
    }

    public void onClickAddAnotherClass(View view) {
        Intent intent = new Intent(ClassListActivity.this, ClassSelectionActivity.class);
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
