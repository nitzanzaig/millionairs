package com.example.millionairs.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.millionairs.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class IncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private final CollectionReference collectionReference = db.collection("Income");
    ImageView buttonImageView;
    EditText incomeEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        buttonImageView = view.findViewById(R.id.buttonImageView);
        incomeEditText = view.findViewById(R.id.incomeEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        buttonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> incObj = new HashMap<>();
                incObj.put("email", currentUser.getEmail());
                incObj.put("amount", Integer.parseInt(incomeEditText.getText().toString()));
                collectionReference.document().set(incObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        incomeEditText.setText("");
                        Toast.makeText(view.getContext(), "Income added successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}