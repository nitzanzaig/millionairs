package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class logActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

    }
    public void move_to(View view){
        Intent intent = new Intent(logActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void go_signup(View view){
        Intent intent = new Intent(logActivity.this, SignupMainActivity.class);
        startActivity(intent);
    }
}