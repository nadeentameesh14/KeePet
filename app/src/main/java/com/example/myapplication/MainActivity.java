package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Post> al;

    private ArrayAdapter<Post> arrayAdapter;

   /*


    ImageView img;
    CircleImageView circ;
    TextView a, b, c, d;
    int id;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView img = (ImageView) findViewById(R.id.displayPet);

        //TextView a = (TextView) findViewById(R.id.viewBreed);
        TextView b = (TextView) findViewById(R.id.viewGender), c = (TextView) findViewById(R.id.viewAge), d = (TextView) findViewById(R.id.username);

        CircleImageView circ = (CircleImageView)  findViewById(R.id.displayPic);

       // LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //View vi = inflater.inflate(R.layout.swipecard, null);


        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.parentlayout);// in main activity
        View child = inflater.inflate(R.layout.swipecard, null);
        TextView a = (TextView) child.findViewById(R.id.viewBreed);
        a.setText("ABOOS EDAK ESHT3'L YA IBN L KALB YA");
        parent.addView(child);



        int id = 0;

        al = new ArrayList<Post>();

        Post p1 = new Post(img, circ, a, b, c, d, id);

        al.add(p1);

        setContentView(R.layout.activity_main);

        arrayAdapter = new ArrayAdapter<Post>(this, R.layout.swipecard, R.id.helloText, al );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override

            public void removeFirstObjectInAdapter() {

                Log.d("LIST", "removed object!");

                al.remove(0);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override

            public void onLeftCardExit(Object dataObject) { //left swipe with pet post

                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();

            }

            @Override

            public void onRightCardExit(Object dataObject) {       //right swipe with pet post

                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onAdapterAboutToEmpty(int itemsInAdapter) {



            }
            @Override

            public void onScroll(float scrollProgressPercent) {


            }

        });

        // Optionally add an OnItemClickListener

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {

            @Override

            public void onItemClicked(int itemPosition, Object dataObject) {    //click on pet post

                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, PetViewActivity.class);

                intent.putExtra("ID", 2);

                startActivity(intent);




            }

        });

        bottomNav();

    }

    public void bottomNav() {

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
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
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

}