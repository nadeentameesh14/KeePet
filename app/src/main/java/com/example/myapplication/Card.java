package com.example.myapplication;

import android.graphics.Bitmap;

public class Card {

    public int postID, age;
    public String breed, gender, username;
    public Bitmap petImage, userImage;


    public Card(int postID, String breed, int age, String gender, String username, Bitmap petImage, Bitmap userImage) {

        this.postID = postID;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.petImage = petImage;
        this.userImage = userImage;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getPetImage() {
        return petImage;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

}
