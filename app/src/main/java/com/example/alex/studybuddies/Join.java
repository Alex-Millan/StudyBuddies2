package com.example.alex.studybuddies;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;

public class Join extends ListActivity {


    /**
     * Items entered by the user is stored in this ArrayList variable
     */
    ArrayList list = new ArrayList();

    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    SimpleAdapter adapter;
    // Configure the first entry.
    HashMap<String, String> entry1;
    // Configure the second entry
    HashMap<String, String> entry2;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setting a custom layout for the list activity */
        setContentView(R.layout.content_join);

        /** Reference to the delete button of the layout main.xml */
        Button btnDel = (Button) findViewById(R.id.btnDel);

        // The soon to be list of entries.
        List<Map<String, String>> entries = new ArrayList<Map<String, String>>();

        entry1 = new HashMap<String, String>();
        entry1.put("title", "Class 1");
        entry1.put("content", "Class 1 Details");
        String[] keys = new String[]{
                "title", "content"
        };
        // Add the first entry to the list.
        entries.add(entry1);

        // Configure the second entry
        entry2 = new HashMap<String, String>();
        entry2.put("title", "Class 2");
        entry2.put("content", "Class 2 Details");

        // Add the second entry to the list.
        entries.add(entry2);
        int[] viewIDs = new int[]{
                android.R.id.text1, android.R.id.text2
        };

        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new SimpleAdapter(this, entries, android.R.layout.simple_list_item_activated_2, keys, viewIDs);
        //adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, keys, list);


        /** Defining a click event listener for the button "Delete" */
        OnClickListener listenerDel = new OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Getting the checked items from the listview */
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();
                entry1.clear();
                entry2.clear();
                for (int i = itemCount - 1; i >= 0; i--) {
                    if (checkedItemPositions.get(i)) {
                        //adapter.remove(entry.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        };


        /** Setting the event listener for the delete button */
        btnDel.setOnClickListener(listenerDel);

        /** Setting the adapter to the ListView */
        setListAdapter(adapter);
    }

}