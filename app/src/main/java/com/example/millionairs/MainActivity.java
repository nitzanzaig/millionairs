package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void move(View view){
        Intent intent = new Intent(getApplicationContext(), personaldetails.class);
        startActivity(intent);
    }

    public void income(View view){
        Intent intent = new Intent(getApplicationContext(), income.class);
        startActivity(intent);
    }
    public void budget(View view){
        Intent intent = new Intent(getApplicationContext(), budget.class);
        startActivity(intent);
    }
    public void bot(View view){
        Intent intent = new Intent(getApplicationContext(), bot.class);
        startActivity(intent);
    }

}