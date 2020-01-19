package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

import android.os.Bundle;
import android.app.Activity;

import android.content.Context;

import android.os.Bundle;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   // ListView feed;

   // JSONArray pets;

   // int mImages[];

   // String mBreeds[], mGenders[], mAges[];

    private ArrayList<String> al;

    private ArrayAdapter<String> arrayAdapter;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //feed = (ListView) findViewById(R.id.listView);

        al = new ArrayList<>();

        al.add("php");

        al.add("c");

        al.add("python");

        al.add("java");

        al.add("html");

        al.add("c++");

        al.add("css");

        al.add("javascript");

        ImageView img = (ImageView) findViewById(R.id.imageView3);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override

            public void removeFirstObjectInAdapter() {

                // this is the simplest way to delete an object from the Adapter (/AdapterView)

                Log.d("LIST", "removed object!");

                al.remove(0);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override

            public void onLeftCardExit(Object dataObject) {

                //Do something on the left!

                //You also have access to the original object.

                //If you want to use it just cast it (String) dataObject

                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();

            }

            @Override

            public void onRightCardExit(Object dataObject) {

                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onAdapterAboutToEmpty(int itemsInAdapter) {

                // Ask for more data here

                al.add("XML ".concat(String.valueOf(i)));

                arrayAdapter.notifyDataSetChanged();

                Log.d("LIST", "notified");

                i++;

            }
            @Override

            public void onScroll(float scrollProgressPercent) {


            }

        });

        // Optionally add an OnItemClickListener

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {

            @Override

            public void onItemClicked(int itemPosition, Object dataObject) {

                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }

        });





        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_add: {
                        startActivity(new Intent(getApplicationContext(),AddActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.nav_settings: {
                        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.nav_home: {
                        return true;
                    }

                }
                return false;
            }
        });


    }

  /*  class myAdapter extends ArrayAdapter<String> {

        Context context;
        int rImg[];
        String rBreeds[], rGenders[], rAges[];


        myAdapter( Context c, int imgs[], String breeds[], String genders[], String ages[]) {
            super(c, R.layout.post, R.id.imageView);

        }
    }*/


}
