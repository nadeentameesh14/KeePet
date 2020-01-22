package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private Spinner typeSpin;
    private Spinner breedSpin;
    private Spinner genderSpin;
    private Switch vacSwitch;
    private EditText name;
    private EditText age;

    private EditText description;
    private ImageButton postButton;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayAdapter<CharSequence> adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initialize();

        spinners();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendPostRequest();
            }
        });


        bottomNav();


    }

    public void initialize() {

        typeSpin = (Spinner)findViewById(R.id.type_spinner);
        breedSpin =(Spinner)findViewById(R.id.breed_spinner);
        genderSpin =(Spinner)findViewById(R.id.gender_spinner);
        vacSwitch = (Switch)findViewById(R.id.vaccinated);
        description = (EditText)findViewById(R.id.description_edit);
        postButton = (ImageButton)findViewById(R.id.postButton);
        name = (EditText)findViewById(R.id.nameEdit);
        age = (EditText)findViewById(R.id.dateEdit);

    }

    public void spinners() {



        adapter = ArrayAdapter.createFromResource(this,R.array.pet_types,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpin.setAdapter(adapter);

        adapter3 = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genderSpin.setAdapter(adapter3);

        typeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();

                switch (text) {
                    case "Cat": {
                        adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.cat_breeds,android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        breedSpin.setAdapter(adapter2);
                    } break;
                    case "Dog": {
                        adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.dog_breeds,android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        breedSpin.setAdapter(adapter2);
                    } break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        breedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        genderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void bottomNav() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_add);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_add: {
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

    public void sendPostRequest() {

        String URL_BASE = "http://localhost:3000";
        String URL= URL_BASE + "/pet/create";

        final RequestQueue requestQueue = Volley.newRequestQueue(AddActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Toast.makeText(AddActivity.this,"Posted Successfully! ",Toast.LENGTH_LONG).show();
                            Log.d("Response", response);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            overridePendingTransition(0,0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("Error:",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //name,age,breed,type,gender,vaccination,description
                params.put("name",name.getText().toString());
                params.put("age",age.getText().toString());
                params.put("type",typeSpin.getSelectedItem().toString());
                params.put("breed",breedSpin.getSelectedItem().toString());

                if(genderSpin.getSelectedItem().toString().equals("Male")) params.put("gender",String.valueOf('m'));
                else params.put("gender",String.valueOf('f'));

                if(vacSwitch.isChecked()) params.put("vaccination",String.valueOf(1));
                else params.put("vaccination",String.valueOf(0));

                params.put("description",description.getText().toString());

                return params;
            }

        };

        requestQueue.add(stringRequest);


    }


}
