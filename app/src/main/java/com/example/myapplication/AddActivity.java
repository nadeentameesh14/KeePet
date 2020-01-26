package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private Spinner typeSpin;
    private Spinner breedSpin;
    private Spinner genderSpin;
    private Spinner citySpin;
    private Spinner areaSpin;
    private Switch vacSwitch;
    private EditText name;
    private EditText age;

    private EditText description;
    private ImageButton postButton;

    private ImageView uploadImage;
    private ImageButton uploadButton;
    private ImageButton resetButton;
    private final int IMG_REQUEST= 1;
    private Bitmap bitmap;



    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayAdapter<CharSequence> adapter3;
    private ArrayAdapter<CharSequence> adapter4;
    private ArrayAdapter<CharSequence> adapter5;

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


        handleUpload();

        bottomNav();


    }

    public void initialize() {

        typeSpin = (Spinner)findViewById(R.id.type_spinner);
        breedSpin =(Spinner)findViewById(R.id.breed_spinner);
        genderSpin =(Spinner)findViewById(R.id.gender_spinner);
        citySpin =(Spinner)findViewById(R.id.city_spinner);
        areaSpin =(Spinner)findViewById(R.id.area_spinner);
        vacSwitch = (Switch)findViewById(R.id.vaccinated);
        description = (EditText)findViewById(R.id.description_edit);
        postButton = (ImageButton)findViewById(R.id.postButton);
        uploadButton =(ImageButton)findViewById(R.id.uploadButton);
        resetButton =(ImageButton)findViewById(R.id.resetButton);
        uploadImage = (ImageView)findViewById(R.id.imageUpload);
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

        adapter4 = ArrayAdapter.createFromResource(this,R.array.cities,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
        citySpin.setAdapter(adapter4);



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

        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();

                switch (text) {
                    case "Cairo": {
                        adapter5 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.cairo_areas,android.R.layout.simple_spinner_item);
                        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        areaSpin.setAdapter(adapter5);
                        areaSpin.setEnabled(true);
                    } break;
                    default: {
                        adapter5 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.other_areas,android.R.layout.simple_spinner_item);
                        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        areaSpin.setAdapter(adapter5);
                        areaSpin.setEnabled(false);
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

        String URL_BASE = "http://513a90f3.ngrok.io";
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
                params.put("city",citySpin.getSelectedItem().toString());
                params.put("area",areaSpin.getSelectedItem().toString());
                params.put("image",imageToString(bitmap));

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

    public void handleUpload() {

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.uploadButton: {
                        selectImage();
                    }
                    break;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.resetButton: {
                        Bitmap mIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.user_profile);
                        uploadImage.setImageBitmap(mIcon);
                    }
                    break;
                }
            }
        });
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                uploadImage.setImageBitmap(bitmap);
                uploadImage.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
