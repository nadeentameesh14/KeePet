package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        al = new ArrayList<>();

        al.add("post");
        al.add("post");
        al.add("post");
        al.add("post");
        al.add("post");
        al.add("post");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );

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

                Intent intent = new Intent();

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
