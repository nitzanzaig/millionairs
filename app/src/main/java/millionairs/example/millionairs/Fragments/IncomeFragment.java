package millionairs.example.millionairs.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nitzan.millionairs.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class IncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private final CollectionReference collectionReference = db.collection("Income");
    Button buttonImageView;
    EditText incomeEditText;
    TextView incomeTextView;
    String currentEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        buttonImageView = view.findViewById(R.id.buttonImageView);
        incomeTextView = view.findViewById(R.id.incomeTextView);
        incomeEditText = view.findViewById(R.id.incomeEditText);

        printIncome();
        /*incomeRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 totalIncome[0] = Double.parseDouble(String.valueOf(documentSnapshot.get("IncomeType")));
                 incomeTextView.setText("Current Income is " + String.format("%.0f", totalIncome[0]) + "₪");

             }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireContext(), "You Have no Income yet! Please enter your Income", Toast.LENGTH_SHORT).show();
            }
        });*/
        buttonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> incObj = new HashMap<>();
                incObj.put("email", currentUser.getEmail());
                if (!incomeEditText.getText().toString().isEmpty()) {
                    incObj.put("type", "IncomeType");
                    incObj.put("amount", Integer.parseInt(incomeEditText.getText().toString()));
                    collectionReference.document().set(incObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            incomeEditText.setText("");
                            printIncome();
                            Toast.makeText(view.getContext(), "Income added successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void printIncome(){
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        double[] totalIncome = {0};

        assert currentUser != null;
        currentEmail = currentUser.getEmail();
        collectionReference.whereEqualTo("email", currentEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int totalIncome = 0;
                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                        totalIncome += Integer.parseInt(String.valueOf(snapshot.get("amount")));
                    }
                    incomeTextView.setText("Current Income is " + totalIncome + "₪");
                }
            }
        });
    }
}