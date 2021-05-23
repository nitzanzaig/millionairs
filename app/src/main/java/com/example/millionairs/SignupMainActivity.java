package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupMainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText householdEditText;
    private String gender = "";
    private String livingArea = "";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);
        Spinner dropdown = findViewById(R.id.spinnerGender);
        String[] items = new String[]{"Female", "Male","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter(SignupMainActivity.this,
                R.layout.spinner_layout,items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                 gender = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner dropdown1 = findViewById(R.id.spinnerLivingArea);
        String[] items1 = new String[]{"North Israel", "Center Israel","South Israel"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter(SignupMainActivity.this,
                R.layout.spinner_layout,items1);
        dropdown1.setAdapter(adapter1);

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                livingArea = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        Button submitButton = findViewById(R.id.submitButton);
        emailEditText = findViewById(R.id.emailEditTextSignUp);
        passwordEditText = findViewById(R.id.passwordEditTextSignup);
        ageEditText = findViewById(R.id.ageEditText);
        householdEditText = findViewById(R.id.householdEditText);
        authStateListener = firebaseAuth -> currentUser = firebaseAuth.getCurrentUser();
        submitButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(emailEditText.getText().toString()) && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                int age = Integer.parseInt(ageEditText.getText().toString());
                int household = Integer.parseInt(householdEditText.getText().toString());
                createUserEmailAccount(email, password, gender, age, household, livingArea);
            }else {
                Toast.makeText(SignupMainActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserEmailAccount(String email, String password, String gender, int age, int household, String livingArea){
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(gender)
                && !TextUtils.isEmpty(String.valueOf(age)) && !TextUtils.isEmpty(String.valueOf(household)) && !TextUtils.isEmpty(livingArea)){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    currentUser = firebaseAuth.getCurrentUser();
                    assert currentUser != null;
                    String currentUserId = currentUser.getUid();

                    Map<String, Object> userObj = new HashMap<>();
                    userObj.put("userId", currentUserId);
                    userObj.put("email", email);
                    userObj.put("password", password);
                    userObj.put("age", age);
                    userObj.put("house_hold_size", household);
                    userObj.put("gender", gender);
                    userObj.put("living_area", livingArea);
                    collectionReference.add(userObj).addOnSuccessListener(documentReference -> documentReference.get().addOnCompleteListener(task1 -> {
                        if (Objects.requireNonNull(task1.getResult()).exists()){
                            Intent intent = new Intent(SignupMainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })).addOnFailureListener(e -> {
                        Toast.makeText(SignupMainActivity.this, e.toString().trim(), Toast.LENGTH_SHORT).show();
                    });
                }else {
                    Toast.makeText(SignupMainActivity.this, "Sign up failed, please try again", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(SignupMainActivity.this, e.toString().trim(), Toast.LENGTH_SHORT).show());
        }else {
            Toast.makeText(SignupMainActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}