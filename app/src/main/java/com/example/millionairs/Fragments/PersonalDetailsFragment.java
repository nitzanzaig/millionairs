package com.example.millionairs.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.millionairs.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
    String select = "";
    int value;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view=inflater.inflate(R.layout.fragment_personal_details, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        DocumentReference ref = collectionReference.document(currentUser.getUid());
        Log.d("Index", currentUser.getProviderId());

        Spinner dropdown = view.findViewById(R.id.genderSpinner);
        ArrayList<String> items = new ArrayList<>();
        items.add("Female");
        items.add("Male");
        items.add("Other");
        int location_gender = items.indexOf(getSelection(ref, "gender"));
        Log.d("Index", String.valueOf(location_gender));
        dropdown.setSelection(location_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);

        Spinner dropdown1 = view.findViewById(R.id.livingAreaSpinner);
        ArrayList<String> items1 = new ArrayList<>();
        items1.add("North Israel");
        items1.add("Center Israel");
        items1.add("South Israel");
        int location_area = items.indexOf(getSelection(ref, "living_area"));
        dropdown1.setSelection(location_area);
        ArrayAdapter<String> adapter1 = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,items1);
        dropdown1.setAdapter(adapter1);



        seek_bar = view.findViewById(R.id.SeekBarID);
        seek_bar1 = view.findViewById(R.id.SeekBarID1);
        seek_bar2 = view.findViewById(R.id.SeekBarID2);
        seek_bar3 = view.findViewById(R.id.SeekBarID3);
        seek_bar4 = view.findViewById(R.id.SeekBarID4);
        sb = view.findViewById(R.id.sb);
        sb1 = view.findViewById(R.id.sb1);
        sb2 = view.findViewById(R.id.sb2);
        sb3 = view.findViewById(R.id.sb3);
        sb4 = view.findViewById(R.id.sb4);
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                sb.setText("You Rated " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_bar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                sb1.setText("You Rated " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {

            }
        });
        seek_bar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                sb2.setText("You Rated " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {

            }
        });
        seek_bar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar3, int progress, boolean fromUser) {

                sb3.setText("You Rated " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar3) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar3) {

            }
        });
        seek_bar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar4, int progress, boolean fromUser) {

                sb4.setText("You Rated " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar4) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar4) {

            }
        });

        ageEditText = view.findViewById(R.id.ageEditTextPersonal);
        houseHoldEditText = view.findViewById(R.id.householdEditTextPersonal);

        ageEditText.setText(String.valueOf(getValue(ref, "age")));
        houseHoldEditText.setText(String.valueOf(getValue(ref, "household_size")));

        return view;



    }

    private String getSelection(DocumentReference ref, String field){
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    select = documentSnapshot.getString(field);
                }else {
                    select = "";
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("Get Info", e.getMessage());
            }
        });
        return select;
    }

    private int getValue(DocumentReference ref, String field){
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    value = (int) documentSnapshot.get(field);
                }else {
                    value = 0;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("Get Info", e.getMessage());
            }
        });
        return value;
    }

}