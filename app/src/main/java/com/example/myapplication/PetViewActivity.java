package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PetViewActivity extends AppCompatActivity {

    private TextView petInfo;
    private TextView petInfo2;
    private TextView ownerInfo;
    private String Type;
    private String Breed;
    private String Gender;
    private String City;
    private String Area;
    private String Name;
    private String Age;
    private String Vac;
    private String Desc;
    private String Username;
    private Integer ID;

    private TextView Adoption;
    private ImageView AdoptionPic;

    private FloatingActionButton messageButton;
    private ImageView imageView;
    private int imageID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_view);

        petInfo=(TextView)findViewById(R.id.petInfo) ;
        petInfo2=(TextView)findViewById(R.id.petInfo2) ;
        ownerInfo=(TextView)findViewById(R.id.ownerInfo);

        Intent intent = getIntent();

        ID = intent.getIntExtra("ID",0);
        Log.i("ID HERE",String.valueOf(ID));

        imageView = (ImageView)findViewById(R.id.image);
        messageButton = (FloatingActionButton)findViewById(R.id.fab) ;
        Adoption = (TextView)findViewById(R.id.adoption_title);
        AdoptionPic = (ImageView)findViewById(R.id.adoption_image);


        imageID = intent.getIntExtra("Image",R.drawable.default_upload);

        imageView.setImageResource(imageID);


        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(PetViewActivity.this, Chat.class);

                startActivity(messageIntent);
            }
        });

        getPetRequest();

        bottomNav();


    }

    public void bottomNav() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_add);

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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }

                }
                return false;
            }
        });

    }

    public void getPetRequest() {

        String URL_BASE = "http://124ed2a8.ngrok.io";
        String URL = URL_BASE + "/pet/get/byId?id=" + ID;

        final RequestQueue requestQueue = Volley.newRequestQueue(PetViewActivity.this);

        JsonArrayRequest stringRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //parse
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject item = response.getJSONObject(i);
                                Type = item.getString("type");
                                Breed = item.getString("breed");

                                if(item.getString("gender").equals("m")) Gender = "Male";
                                else Gender = "Female";

                                Name = item.getString("name");
                                Age = item.getString("age");
                                City = item.getString("city");
                                Area = item.getString("area");
                                Username = item.getString("seller");

                                if(item.getInt("vaccination") == 0) Vac = "No";
                                else Vac = "Yes";


                                if(item.getInt("adopted") == 0) {
                                    Adoption.setText("Up for adoption!");
                                    AdoptionPic.setImageResource(R.drawable.foradoption);
                                }
                                else {
                                    Adoption.setText("Got Adopted!");
                                    AdoptionPic.setImageResource(R.drawable.adopted);
                                }

                                Desc = item.getString("description");

                                String pet_i= "Name: " + Name + "\nBirthdate: " + Age + "\nCity: " + City + "\nArea: " + Area + "\nVaccinated: " + Vac;

                                petInfo.setText(pet_i);

                                String pet_i2 = "Type: " + Type + "\nBreed: " + Breed + "\nGender: " + Gender
                                         + "\nDescription: " + Desc;

                                petInfo2.setText(pet_i2);

                                ownerInfo.setText("Owner's username: " + Username);



                            } catch (JSONException e) {
                                Log.e("Exception", "unexpected JSON exception in request 1", e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE IS :", error.toString());
                    }
                }
        );

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

    }


}
