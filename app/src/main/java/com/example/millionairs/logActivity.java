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

public class logActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private FirebaseAuth firebaseAuth;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        firebaseAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.submitButtonLogin);
        emailEditText = findViewById(R.id.emailEditTextLogin);
        passwordEditText = findViewById(R.id.passwordEditTextLogin);
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(logActivity.this, SignupMainActivity.class);
            startActivity(intent);
        });
        loginButton.setOnClickListener(v -> loginEmailPasswordUser(emailEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim()));
    }

    private void loginEmailPasswordUser(String email, String password){
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                assert user != null;
                final String currentUserId = user.getUid();
                collectionReference.whereEqualTo("userId", currentUserId).addSnapshotListener((value, e) -> {
                    if (e != null){
                        Toast.makeText(logActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    assert value != null;
                    if (!value.isEmpty()){
                        startActivity(new Intent(logActivity.this, MainActivity.class));
                    }
                });
            }).addOnFailureListener(e -> {

            });
        }
        else{
            Toast.makeText(logActivity.this, "Please Enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

}