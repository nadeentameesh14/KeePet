package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PetViewActivity extends AppCompatActivity {

    private TextView petInfo;
    private String Type;
    private String Breed;
    private String Gender;
    private String Name;
    private String Age;
    private String Vac;
    private String Desc;
    private Integer ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_view);

        petInfo=(TextView)findViewById(R.id.petInfo) ;

        Intent intent = getIntent();

        ID = intent.getIntExtra("ID",0);

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

        String URL_BASE = "http://61bd8f5f.ngrok.io";
        String URL = URL_BASE + "/pet/get?id=" + ID;

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

                                if(item.getInt("vaccination") == 0) Vac = "No";
                                else Vac = "Yes";

                                Desc = item.getString("description");

                                String pet_i= "Name: " + Name + "\nBirthdate: " + Age + "\nType: " + Type + "\nBreed" + Breed + "\nGender: " + Gender
                                        + "\nVaccinated: " + Vac + "\nDescription: " + Desc;

                                petInfo.setText(pet_i);

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
