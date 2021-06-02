package millionairs.example.millionairs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nitzan.millionairs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SignupMainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private EditText householdEditText;
    String gender, livingArea;

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
                gender = parent.getItemAtPosition(0).toString();
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
                livingArea = parent.getItemAtPosition(0).toString();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        Button submitButton = findViewById(R.id.submitButtonExpenses);
        emailEditText = findViewById(R.id.emailEditTextSignUp);
        passwordEditText = findViewById(R.id.passwordEditTextSignup);
        ageEditText = findViewById(R.id.ageEditText);
        householdEditText = findViewById(R.id.householdEditText);
        authStateListener = firebaseAuth -> currentUser = firebaseAuth.getCurrentUser();
        submitButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(emailEditText.getText().toString()) && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String age = ageEditText.getText().toString();
                String household = householdEditText.getText().toString();
                createUserEmailAccount(email, password, gender, age, household, livingArea);
            }else {
                Toast.makeText(SignupMainActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    Log.d("Sign Up", "User logged in before signup");
                }else {
                    //no user yet...
                    Log.d("Sign Up", "No logged in user before sign up");
                }

            }
        };
    }

    private void createUserEmailAccount(String email, String password, String gender, String age, String household, String livingArea){
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(gender)
                && !TextUtils.isEmpty(String.valueOf(age)) && !TextUtils.isEmpty(String.valueOf(household))
                && !TextUtils.isEmpty(livingArea)){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                //Create a user Map so we can create a user in the User collection
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("email", email);
                                userObj.put("password", password);
                                userObj.put("gender", gender);
                                userObj.put("age", age);
                                userObj.put("household_size", household);
                                userObj.put("living_area", livingArea);

                                collectionReference.document(email).set(userObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Intent intent = new Intent(SignupMainActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(SignupMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //save to our firestore database
                                /*collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    Intent intent = new Intent(SignupMainActivity.this,
                                                                            MainActivity.class);
                                                                    startActivity(intent);
                                                                }else {
                                                                    Toast.makeText(SignupMainActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignupMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });*/


                            }else {
                                //something went wrong
                                Toast.makeText(SignupMainActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }else {
            Toast.makeText(SignupMainActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}