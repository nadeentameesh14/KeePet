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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

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

    private final int IMG_REQUEST= 2;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initialize();

        handleUpload();

        bottomNav();


    }

    private void initialize() {

        NameEdit = (EditText)findViewById(R.id.user_nameEdit);
        BioEdit = (EditText)findViewById(R.id.user_bioEdit);
        UsernameEdit = (EditText)findViewById(R.id.user_usernameEdit);
        userProfile = (CircleImageView) findViewById(R.id.profile_image);
        uploadButton = (ImageButton)findViewById(R.id.uploadButton2);
        resetButton = (ImageButton)findViewById(R.id.resetButton2);


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
}
