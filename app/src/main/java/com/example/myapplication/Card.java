package com.example.myapplication;

public class Card {

    public int postID, age, petImage, userImage;
    public String breed, gender, username;


    public Card(int postID, String breed, int age, String gender, String username, int petImage, int userImage) {

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

    public int getPetImage() {
        return petImage;
    }

    public int getUserImage() {
        return userImage;
    }

}
