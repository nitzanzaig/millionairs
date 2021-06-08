package millionairs.example.millionairs.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.nitzan.millionairs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PersonalDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SeekBar seek_bar;
    private TextView sb;
    private SeekBar seek_bar1;
    private TextView sb1;
    private SeekBar seek_bar2;
    private TextView sb2;
    private SeekBar seek_bar3;
    private TextView sb3;
    private SeekBar seek_bar4;
    private TextView sb4;

    private EditText ageEditText;
    private EditText houseHoldEditText;

    FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private final CollectionReference collectionReference = db.collection("users");
    private final CollectionReference preferences = db.collection("Preferences Questionnaire");
    private Button submitButton;
    String gender, livingArea;

    ArrayList<String> values = new ArrayList<String>(5){
        {
            add("1");
            add("1");
            add("1");
            add("1");
            add("1");
        }
    };;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view=inflater.inflate(R.layout.fragment_personal_details, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        DocumentReference ref = collectionReference.document(Objects.requireNonNull(currentUser.getEmail()));

        ageEditText = view.findViewById(R.id.ageEditTextPersonal);
        houseHoldEditText = view.findViewById(R.id.householdEditTextPersonal);

        Log.d("email", currentUser.getEmail());

        Spinner dropdown = view.findViewById(R.id.genderSpinner);
        ArrayList<String> items = new ArrayList<>();
        items.add("Female");
        items.add("Male");
        items.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gender = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                gender = (String) parent.getSelectedItem();
            }
        });

        Spinner dropdown1 = view.findViewById(R.id.livingAreaSpinner);
        ArrayList<String> items1 = new ArrayList<>();
        items1.add("North Israel");
        items1.add("Center Israel");
        items1.add("South Israel");
        ArrayAdapter<String> adapter1 = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,items1);
        dropdown1.setAdapter(adapter1);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    int location_gender = items.indexOf(documentSnapshot.getString("gender"));
                    dropdown.setSelection(location_gender);
                    int location_livingArea = items.indexOf(documentSnapshot.getString("living_area"));
                    dropdown1.setSelection(location_livingArea);
                    ageEditText.setText(documentSnapshot.getString("age"));
                    houseHoldEditText.setText(documentSnapshot.getString("household_size"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("Get Info", e.getMessage());
            }
        });

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                livingArea = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                livingArea = (String) parent.getSelectedItem();
            }
        });


        submitButton = view.findViewById(R.id.submitButtonExpenses);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> userObj = new HashMap<>();
                userObj.put("gender", gender);
                userObj.put("age", ageEditText.getText().toString());
                userObj.put("household_size", houseHoldEditText.getText().toString());
                userObj.put("living_area", livingArea);
                ref.update(userObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Details were updates successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                Map<String, Object> personalObj = new HashMap<>();
                personalObj.put("email", currentUser.getEmail());
                personalObj.put("home_bills", values.get(0));
                personalObj.put("leisure", values.get(1));
                personalObj.put("transportation", values.get(2));
                personalObj.put("housing", values.get(3));
                personalObj.put("loans_and_credit_cards", values.get(4));
            }
        });

        return view;
    }
}