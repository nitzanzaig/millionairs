package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.millionairs.Fragments.ExpensesFragment;

public class SignupMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);
    }

    public void go_expenses(View view){
        //Intent intent = new Intent(getApplicationContext(), income.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}