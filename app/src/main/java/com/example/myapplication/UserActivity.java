package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    GridView gridView;
    ImageButton editProfile;
    ImageView imageView;
    int [] images = {R.drawable.pupper,R.drawable.kitty,R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_side);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }


        gridView = findViewById(R.id.postgridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PetViewActivity.class);

                intent.putExtra("Image", images[position]);
                intent.putExtra("ID", 2);
                startActivity(intent);

            }
        });


        editProfile = (ImageButton)findViewById(R.id.editButton);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),EditProfileActivity.class);
                startActivity(intent);

            }
        });
        bottomNav();
    }

    public void bottomNav() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_add: {
                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    case R.id.nav_settings: {
                        return true;
                    }
                    case R.id.nav_home: {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                }
                return false;
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.nav_likes: {
                Toast.makeText(UserActivity.this, "Likes", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),LikesActivity.class);
                startActivity(intent);
                }
                break;
            case R.id.nav_messages: {
                Toast.makeText(UserActivity.this, "Messages", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MessagingActivity.class);
                startActivity(intent);
                }
                break;
            case R.id.nav_logout: {
                Toast.makeText(UserActivity.this, "Log Out", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return false;
    }

    private class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = getLayoutInflater().inflate(R.layout.post_item,null);

            imageView = v.findViewById(R.id.post_thumbnail);

            imageView.setImageResource(images[position]);

            return v;

        }
    }
}
