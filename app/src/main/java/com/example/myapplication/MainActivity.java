package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Pair<String, String> user = new Pair<>("tomas","b956dd5e9b8e1914ca52cb9af527700905249437de8909549915467f159050e0a2936047d50ff3c325ac08f481fa812a3f42a2f09256254ea055d4cf84ac7b71");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            View root = findViewById(android.R.id.content);


            ImageView imageView = findViewById(R.id.imageView);
            EditText editText = findViewById(R.id.editTextText);
            EditText editTextPassword = findViewById(R.id.editTextTextPassword);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = String.valueOf(editText.getText());
                    String userpw = toSHA3_256(String.valueOf(editTextPassword.getText()));
                    Log.d("sha256",userpw);

                    if (username.equals(user.first) && userpw.equals(user.second)){
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                }
                    else {
                        Snackbar.make(root, "Wrong credentials", Snackbar.LENGTH_LONG).show();
                    }
                }
            });





            return insets;
        });


    }
    public static String toSHA3_256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA512");
            byte[] inputBytes = input.getBytes();
            byte[] hashBytes = digest.digest(inputBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}