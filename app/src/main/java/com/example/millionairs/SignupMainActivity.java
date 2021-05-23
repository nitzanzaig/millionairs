package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
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

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Button submitButton = findViewById(R.id.submitButton);
        emailEditText = findViewById(R.id.emailEditTextSignUp);
        passwordEditText = findViewById(R.id.passwordEditTextSignup);
        authStateListener = firebaseAuth -> currentUser = firebaseAuth.getCurrentUser();
        submitButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(emailEditText.getText().toString()) && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                createUserEmailAccount(email, password);
            }else {
                Toast.makeText(SignupMainActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserEmailAccount(String email, String password){
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    currentUser = firebaseAuth.getCurrentUser();
                    assert currentUser != null;
                    String currentUserId = currentUser.getUid();

                    Map<String, String> userObj = new HashMap<>();
                    userObj.put("userId", currentUserId);
                    userObj.put("email", email);
                    userObj.put("password", password);
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