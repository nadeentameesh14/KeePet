package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Card cards[];
    private CustomAdapter arrayAdapter;
    private SwipeFlingAdapterView flingContainer;
    private ImageButton filterButton;

    private ListView listView;
    private List<Card> list;

    private TextView noPosts;

    int [] images = {R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws,R.drawable.pupper,R.drawable.kitty,R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws,R.drawable.pupper,R.drawable.kitty,R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws,R.drawable.pupper,R.drawable.kitty,R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws,R.drawable.pupper,R.drawable.kitty,R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws,R.drawable.pupper,R.drawable.kitty};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getAllNonAdoptedPetsRequest();

        list = new ArrayList<Card>();

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        filterButton = (ImageButton)findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FilterActivity.class);
                startActivity(i);
            }
        });
        //dummyData();

        getAllNonAdoptedPetsRequest();


        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override

            public void removeFirstObjectInAdapter() {

                Log.d("LIST", "removed object!");

                list.remove(0);

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

                intent.putExtra("ID", list.get(itemPosition).getID());

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

    public void getAllNonAdoptedPetsRequest() {

        String URL_BASE = "http://d3bc1802.ngrok.io";
        String URL = URL_BASE + "/pet/get/nonadopted";



        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);


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

                                int id = item.getInt("id");
                                String item_breed = item.getString("breed");

                                Log.i( "ID: " ,  String.valueOf(id));


                                String item_gender;

                                if(item.getString("gender").equals("m")) item_gender = "Male";
                                else item_gender = "Female";

                                int item_age = getAge(item.getString("age"));

                                String item_username = item.getString("seller");

                                String img_string = item.getString("image");

                                Bitmap img = stomap(img_string);
                                Log.i("Image:",img_string);

                                Card card = new Card(id,item_breed,item_age,item_gender,item_username,img,img);

                                list.add(card);
                                //arrayAdapter.notifyDataSetChanged();


                            } catch (JSONException e) {
                                Log.e("Exception", "unexpected JSON exception in request 1", e);
                            }
                        }

                        arrayAdapter = new CustomAdapter( getApplicationContext(), R.layout.swipecard, list );
                        flingContainer.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                        Log.i( " THE SIZE OF THE LIST IS FINALYYYYYY: " ,  String.valueOf(list.size()));
                        if(list.size() < 1) {
                            noPosts = (TextView)findViewById(R.id.noposts);
                            noPosts.setVisibility(View.VISIBLE);

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

//        Log.i("List",list.toString());

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

    }

    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return age;
    }

    public Bitmap stomap(String encodedString){

        byte[] imageAsBytes = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }





}