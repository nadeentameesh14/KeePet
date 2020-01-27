package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditPassword extends AppCompatActivity {

    EditText oldpass, newpass, confirmnewpass;

    Button save, discard;

    String pass1, pass2, pass3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        oldpass = (EditText) findViewById(R.id.oldPass);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmnewpass = (EditText) findViewById(R.id.confirmnewpass);

        save = (Button) findViewById(R.id.saveButton);
        discard = (Button) findViewById(R.id.discardButton);

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPassword.this, UserActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pass1 = oldpass.getText().toString();
                pass2 = newpass.getText().toString();
                pass3 = confirmnewpass.getText().toString();

                if(pass1.equals("")){
                    oldpass.setError("can't be blank");
                }
                else if(pass2.equals("")){
                    newpass.setError("can't be blank");
                }
                else if(pass3.equals("")){
                    confirmnewpass.setError("can't be blank");
                }
                else if(!pass2.matches("[A-Za-z0-9]+")){
                    newpass.setError("only alphabet or number allowed");
                }
                else if(pass2.length()<5){
                    newpass.setError("at least 5 characters long");
                }
                else if(!pass1.equals(oldpass)){       //check old pass with database
                    oldpass.setError("Incorrect Password");
                }
                else if(!pass2.equals(pass3)) {
                    confirmnewpass.setError("passwords don't match");
                }
                else{       //accept and update password

                }
            }
        });
    }
}
