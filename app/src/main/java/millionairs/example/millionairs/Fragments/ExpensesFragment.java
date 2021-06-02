package millionairs.example.millionairs.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nitzan.millionairs.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class ExpensesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText transportationEditText, groceriesEditText, shoppingEditText, otherEditText,
            loansEditText, educationEditText, leisureEditText, homeEditText, healthEditText, SavingEditText;

    FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private final CollectionReference collectionReference = db.collection("Expenses");
    Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        transportationEditText = view.findViewById(R.id.transportationEditText);
        groceriesEditText = view.findViewById(R.id.groceriesEditText);
        shoppingEditText = view.findViewById(R.id.shoppingEditText);
        otherEditText = view.findViewById(R.id.otherEditText);
        loansEditText = view.findViewById(R.id.loansEditText);
        educationEditText = view.findViewById(R.id.educationEditText);
        leisureEditText = view.findViewById(R.id.leisureEditText);
        homeEditText = view.findViewById(R.id.homeEditText);
        healthEditText = view.findViewById(R.id.healthEditText);
        SavingEditText = view.findViewById(R.id.SavingEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        submitButton = view.findViewById(R.id.submitButtonExpenses);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> expsObj = new HashMap<>();
                expsObj.put("email", currentUser.getEmail());
                if (!transportationEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Transportation");
                    expsObj.put("amount", Integer.parseInt(transportationEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            transportationEditText.setText("");
                        }
                    });
                }
                if (!groceriesEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Groceries");
                    expsObj.put("amount", Integer.parseInt(groceriesEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            groceriesEditText.setText("");
                        }
                    });
                }
                if (!shoppingEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Shopping");
                    expsObj.put("amount", Integer.parseInt(shoppingEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            shoppingEditText.setText("");
                        }
                    });
                }
                if (!otherEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Other");
                    expsObj.put("amount", Integer.parseInt(otherEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            otherEditText.setText("");
                        }
                    });
                }
                if (!loansEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Loans");
                    expsObj.put("amount", Integer.parseInt(loansEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loansEditText.setText("");
                        }
                    });
                }
                if (!educationEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Education");
                    expsObj.put("amount", Integer.parseInt(educationEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            educationEditText.setText("");
                        }
                    });
                }
                if (!leisureEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Leisure");
                    expsObj.put("amount", Integer.parseInt(leisureEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            leisureEditText.setText("");
                        }
                    });
                }
                if (!homeEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Home");
                    expsObj.put("amount", Integer.parseInt(homeEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            homeEditText.setText("");
                        }
                    });
                }
                if (!healthEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Health");
                    expsObj.put("amount", Integer.parseInt(healthEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            healthEditText.setText("");
                        }
                    });
                }
                if (!SavingEditText.getText().toString().isEmpty()) {
                    expsObj.put("type", "Savings");
                    expsObj.put("amount", Integer.parseInt(SavingEditText.getText().toString()));
                    collectionReference.document().set(expsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            SavingEditText.setText("");
                        }
                    });
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}