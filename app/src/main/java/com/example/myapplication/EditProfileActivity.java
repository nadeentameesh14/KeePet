package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText NameEdit;
    private EditText BioEdit;
    private EditText UsernameEdit;
    private CircleImageView userProfile;
    private ImageButton uploadButton;
    private ImageButton resetButton;
    private ImageButton saveButton;
    private ImageButton discardButton;
    private SharedPreferences prefs;
    private final int IMG_REQUEST= 2;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String token = prefs.getString("token","error");

        Log.i("Key",token);

        initialize();

        handleUpload();

        getUserRequest();

        bottomNav();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePostRequest();
            }
        });


    }

    private void initialize() {

        NameEdit = (EditText)findViewById(R.id.user_nameEdit);
        BioEdit = (EditText)findViewById(R.id.user_bioEdit);
        UsernameEdit = (EditText)findViewById(R.id.user_usernameEdit);
        userProfile = (CircleImageView) findViewById(R.id.profile_image);
        uploadButton = (ImageButton)findViewById(R.id.uploadButton2);
        resetButton = (ImageButton)findViewById(R.id.resetButton2);
        saveButton = (ImageButton)findViewById(R.id.saveButton);


    }

    public void handleUpload() {

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.uploadButton2: {
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
                    case R.id.resetButton2: {
                        Bitmap mIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.user_profile);
                        userProfile.setImageBitmap(mIcon);
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
                userProfile.setImageBitmap(bitmap);
                userProfile.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
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
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        overridePendingTransition(0,0);
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

    public void getUserRequest() {

        String URL_BASE = "http://d3bc1802.ngrok.io";
        String URL = URL_BASE + "/getuser";

        final RequestQueue requestQueue = Volley.newRequestQueue(EditProfileActivity.this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {

                            Log.i("Response", response.toString());

                            String name = "";
                            String username = "";
                            String bio = "";

                            if(response.getString("name") != null) {
                                name = response.getString("name");
                            }
                            if(response.getString("email") != null) {
                                username = response.getString("email");
                            }
                            if(response.getString("bio") != null) {
                                bio = response.getString("bio");
                            }


                            Log.i("Name", name);
                            Log.i("Username", username);
                            Log.i("bio", bio);

                            UsernameEdit.setHint(username);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();

                SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                String token = prefs.getString("token","error");
                headers.put("Authorization", "bearer " +token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        // Add the request to the RequestQueue.
        requestQueue.add(jsObjRequest);

    }

    public void updatePostRequest() {

        String URL_BASE = "http://d3bc1802.ngrok.io";
        String URL= URL_BASE + "/user/update";

        final RequestQueue requestQueue = Volley.newRequestQueue(EditProfileActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditProfileActivity.this,"Updated Successfully! ",Toast.LENGTH_LONG).show();
                        Log.d("Response", response);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfileActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("Error:",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //name,age,breed,type,gender,vaccination,description
                params.put("name",NameEdit.getText().toString());
                params.put("email",UsernameEdit.getText().toString());
                params.put("bio",BioEdit.getText().toString());


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                String token = prefs.getString("token","error");
                headers.put("Authorization", "bearer " +token);

                return headers;
            }

        };

        requestQueue.add(stringRequest);


    }
}
