package millionairs.example.millionairs.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nitzan.millionairs.R;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressBar progressBarHome, progressBarGroceries, progressBarHealth, progressBarEducation, progressBarLeisure,
            progressBarTransportation, progressBarLoans, progressBarSaving, progressBarShopping, progressBarOther;

    FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private final CollectionReference budget = db.collection("Budget");
    private final CollectionReference expenses = db.collection("Expenses");
    TextView budgetTextView, remainingTextView, homeTextView, groceriesTextView, healthTextView, educationTextView, leisureTextView,
            transportationTextView, loansTextView, savingsTextView, shoppingTextView, otherTextView, zero;

    Button addBudgetBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        progressBarHome = view.findViewById(R.id.progressBarHome);
        progressBarGroceries = view.findViewById(R.id.progressBarGroceries);
        progressBarHealth = view.findViewById(R.id.progressBarHealth);
        progressBarEducation = view.findViewById(R.id.progressBarEducation);
        progressBarLeisure = view.findViewById(R.id.progressBarLeisure);
        progressBarTransportation = view.findViewById(R.id.progressBarTransportation);
        progressBarSaving = view.findViewById(R.id.progressBarSaving);
        progressBarLoans = view.findViewById(R.id.progressBarLoans);
        progressBarShopping = view.findViewById(R.id.progressBarShopping);
        progressBarOther = view.findViewById(R.id.progressBarOther);
        budgetTextView = view.findViewById(R.id.budgetTextView);
        remainingTextView = view.findViewById(R.id.remainingTextView);
        homeTextView = view.findViewById(R.id.textViewHome1);
        groceriesTextView = view.findViewById(R.id.textViewGroceries1);
        healthTextView = view.findViewById(R.id.textViewHealth1);
        educationTextView = view.findViewById(R.id.textViewEducation1);
        leisureTextView = view.findViewById(R.id.textViewLeisure1);
        transportationTextView = view.findViewById(R.id.textViewTransportation1);
        loansTextView = view.findViewById(R.id.textViewLoans1);
        savingsTextView = view.findViewById(R.id.textViewSaving1);
        shoppingTextView = view.findViewById(R.id.textViewShopping1);
        otherTextView = view.findViewById(R.id.textViewOther1);
        final int[] totalBudget = {0};

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        assert currentUser != null;
        String currentEmail = currentUser.getEmail();
        DocumentReference budgetRef = budget.document(Objects.requireNonNull(currentEmail));
        // TODO: add what to do in case there is no expenses in the DB
        budgetRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                progressBarHome.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Home"))));
                progressBarGroceries.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Groceries"))));
                progressBarHealth.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Health"))));
                progressBarEducation.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Education"))));
                progressBarLeisure.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Leisure"))));
                progressBarTransportation.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Transportation"))));
                progressBarSaving.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Savings"))));
                progressBarLoans.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Loans"))));
                progressBarShopping.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Shopping"))));
                progressBarOther.setMax(Integer.parseInt(String.valueOf(documentSnapshot.get("Other"))));
                totalBudget[0] = Integer.parseInt(String.valueOf(documentSnapshot.get("Home"))) + Integer.parseInt(String.valueOf(documentSnapshot.get("Groceries"))) +
                        Integer.parseInt(String.valueOf(documentSnapshot.get("Health"))) + Integer.parseInt(String.valueOf(documentSnapshot.get("Education"))) +
                        Integer.parseInt(String.valueOf(documentSnapshot.get("Leisure"))) + Integer.parseInt(String.valueOf(documentSnapshot.get("Transportation"))) +
                        Integer.parseInt(String.valueOf(documentSnapshot.get("Savings"))) + Integer.parseInt(String.valueOf(documentSnapshot.get("Loans"))) +
                        Integer.parseInt(String.valueOf(documentSnapshot.get("Shopping"))) + Integer.parseInt(String.valueOf(documentSnapshot.get("Other")));
                budgetTextView.setText("Current Budget is " + totalBudget[0]);
            }
        });

        expenses.whereEqualTo("email", currentEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int[] amounts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        switch (String.valueOf(documentSnapshot.get("type"))){
                            case "Home":
                                amounts[0] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Groceries":
                                amounts[1] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Health":
                                amounts[2] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Education":
                                amounts[3] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Leisure":
                                amounts[4] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Transportation":
                                amounts[5] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Savings":
                                amounts[6] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Loans":
                                amounts[7] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Shopping":
                                amounts[8] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Other":
                                amounts[9] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                                break;
                        }
                    }
                    int total = 0;
                    for(int i = 0;i < 10;i++){
                        total += amounts[i];
                    }
                    remainingTextView.setText("Remaining Amount is " + (totalBudget[0] - total));
                    progressBarHome.setProgress(amounts[0]);
                    homeTextView.setText(amounts[0] + "/" + progressBarHome.getMax());
                    progressBarGroceries.setProgress(amounts[1]);
                    groceriesTextView.setText(amounts[1] + "/" + progressBarGroceries.getMax());
                    progressBarHealth.setProgress(amounts[2]);
                    healthTextView.setText(amounts[2] + "/" + progressBarHealth.getMax());
                    progressBarEducation.setProgress(amounts[3]);
                    educationTextView.setText(amounts[3] + "/" + progressBarEducation.getMax());
                    progressBarLeisure.setProgress(amounts[4]);
                    leisureTextView.setText(amounts[4] + "/" + progressBarLeisure.getMax());
                    progressBarTransportation.setProgress(amounts[5]);
                    transportationTextView.setText(amounts[5] + "/" + progressBarTransportation.getMax());
                    progressBarSaving.setProgress(amounts[6]);
                    savingsTextView.setText(amounts[6] + "/" + progressBarSaving.getMax());
                    progressBarLoans.setProgress(amounts[7]);
                    loansTextView.setText(amounts[7] + "/" + progressBarLoans.getMax());
                    progressBarShopping.setProgress(amounts[8]);
                    shoppingTextView.setText(amounts[8] + "/" + progressBarShopping.getMax());
                    progressBarOther.setProgress(amounts[9]);
                    otherTextView.setText(amounts[9] + "/" + progressBarOther.getMax());

                }
            }
        });

        addBudgetBtn = view.findViewById(R.id.Add_BudgetButton);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this.getContext()));
        }

        Python py = Python.getInstance();
        // TODO: change the name to knn
        final PyObject pyObj = py.getModule("test");

        // PyObject obj;

        addBudgetBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view1){
                // TODO: change the args to expenses
                PyObject obj = pyObj.callAttr("main", 1, 2);
                // TODO: insert the new budget to DB
                zero.setText(obj.toString());
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

}