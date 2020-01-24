package com.example.myapplication;

import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Post {

    public ImageView postImage;
    public CircleImageView userImage;
    public TextView breed, age, gender, username;
    public int ID;

    public Post(ImageView postImage,  CircleImageView userImage, TextView breed, TextView age, TextView gender, TextView username, int ID) {
        this.postImage = postImage;
        this.userImage = userImage;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.ID = ID;


    }


}
