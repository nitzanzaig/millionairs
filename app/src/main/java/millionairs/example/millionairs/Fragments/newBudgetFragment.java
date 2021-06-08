package millionairs.example.millionairs.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nitzan.millionairs.R;

import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class newBudgetFragment extends Fragment {

    ProgressBar progressBarHome, progressBarGroceries, progressBarHealth, progressBarEducation, progressBarLeisure,
            progressBarTransportation, progressBarLoans, progressBarSaving, progressBarShopping, progressBarOther;

    FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    private final CollectionReference budget = db.collection("Budget");
    private final CollectionReference expenses = db.collection("Expenses");
    private final CollectionReference income = db.collection("Income");
    TextView budgetTextView, remainingTextView, homeTextView, groceriesTextView, healthTextView, educationTextView, leisureTextView,
            transportationTextView, loansTextView, savingsTextView, shoppingTextView, otherTextView, zero;
    String currentEmail;
    Button addBudgetBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_budget, container, false);

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
        //zero = view.findViewById(R.id.zero);
        double[] totalBudget = {0};
        double[] currentBudget = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        assert currentUser != null;
        currentEmail = currentUser.getEmail();
        DocumentReference budgetRef = budget.document(Objects.requireNonNull(currentEmail));

        budgetRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentBudget[0] = Double.parseDouble(String.valueOf(documentSnapshot.get("Home")));
                currentBudget[1] = Double.parseDouble(String.valueOf(documentSnapshot.get("Groceries")));
                currentBudget[2] = Double.parseDouble(String.valueOf(documentSnapshot.get("Health")));
                currentBudget[3] = Double.parseDouble(String.valueOf(documentSnapshot.get("Education")));
                currentBudget[4] = Double.parseDouble(String.valueOf(documentSnapshot.get("Leisure")));
                currentBudget[5] = Double.parseDouble(String.valueOf(documentSnapshot.get("Transportation")));
                currentBudget[6] = Double.parseDouble(String.valueOf(documentSnapshot.get("Savings")));
                currentBudget[7] = Double.parseDouble(String.valueOf(documentSnapshot.get("Loans")));
                currentBudget[8] = Double.parseDouble(String.valueOf(documentSnapshot.get("Shopping")));
                currentBudget[9] = Double.parseDouble(String.valueOf(documentSnapshot.get("Other")));
                progressBarHome.setMax((int) currentBudget[0]);
                progressBarGroceries.setMax((int) currentBudget[1]);
                progressBarHealth.setMax((int) currentBudget[2]);
                progressBarEducation.setMax((int) currentBudget[3]);
                progressBarLeisure.setMax((int) currentBudget[4]);
                progressBarTransportation.setMax((int) currentBudget[5]);
                progressBarSaving.setMax((int) currentBudget[6]);
                progressBarLoans.setMax((int) currentBudget[7]);
                progressBarShopping.setMax((int) currentBudget[8]);
                progressBarOther.setMax((int) currentBudget[9]);
                totalBudget[0] = Double.parseDouble(String.valueOf(documentSnapshot.get("Home"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Groceries"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Health"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Education"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Leisure"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Transportation"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Savings"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Loans"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Shopping"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Other")));
                budgetTextView.setText("Current Budget is " + String.format("%.2f", totalBudget[0]) + "₪");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireContext(), "You don't have a budget yet! Please create one.", Toast.LENGTH_SHORT).show();
            }
        });

        double[] amounts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        expenses.whereEqualTo("email", currentEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        switch (String.valueOf(documentSnapshot.get("type"))){
                            case "Home":
                                amounts[0] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Groceries":
                                amounts[1] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Health":
                                amounts[2] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Education":
                                amounts[3] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Leisure":
                                amounts[4] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Transportation":
                                amounts[5] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Savings":
                                amounts[6] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Loans":
                                amounts[7] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Shopping":
                                amounts[8] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                            case "Other":
                                amounts[9] += Double.parseDouble(String.valueOf(documentSnapshot.get("amount")));
                                break;
                        }
                    }
                    int total = 0;
                    for(int i = 0;i < 10;i++){
                        total += amounts[i];
                    }
                    remainingTextView.setText("Remaining Amount is " + String.format("%.2f", totalBudget[0] - total) +"₪");
                    progressBarHome.setProgress((int) amounts[0]);
                    homeTextView.setText(String.format("%.2f", amounts[0]) + "₪ /" + String.format("%.2f", currentBudget[0]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[0]));
                    progressBarGroceries.setProgress((int) amounts[1]);
                    groceriesTextView.setText(String.format("%.2f", amounts[1]) + "₪ /" + String.format("%.2f", currentBudget[1]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[1]));
                    progressBarHealth.setProgress((int) amounts[2]);
                    healthTextView.setText(String.format("%.2f", amounts[2]) + "₪ /" + String.format("%.2f", currentBudget[2]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[2]));
                    progressBarEducation.setProgress((int) amounts[3]);
                    educationTextView.setText(String.format("%.2f", amounts[3]) + "₪ /" + String.format("%.2f", currentBudget[3]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[3]));
                    progressBarLeisure.setProgress((int) amounts[4]);
                    leisureTextView.setText(String.format("%.2f", amounts[4]) + "₪ /" + String.format("%.2f", currentBudget[4]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[4]));
                    progressBarTransportation.setProgress((int) amounts[5]);
                    transportationTextView.setText(String.format("%.2f", amounts[5]) + "₪ /" + String.format("%.2f", currentBudget[5]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[5]));
                    progressBarSaving.setProgress((int) amounts[6]);
                    savingsTextView.setText(String.format("%.2f", amounts[6]) + "₪ /" + String.format("%.2f", currentBudget[6]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[6]));
                    progressBarLoans.setProgress((int) amounts[7]);
                    loansTextView.setText(String.format("%.2f", amounts[7]) + "₪ /" + String.format("%.2f", currentBudget[7]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[7]));
                    progressBarShopping.setProgress((int) amounts[8]);
                    shoppingTextView.setText(String.format("%.2f", amounts[8]) + "₪ /" + String.format("%.2f", currentBudget[8]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[8]));
                    progressBarOther.setProgress((int) amounts[9]);
                    otherTextView.setText(String.format("%.2f", amounts[9]) + "₪ /" + String.format("%.2f", currentBudget[9]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[9]));

                }
            }
        });

        addBudgetBtn = view.findViewById(R.id.Add_BudgetButton);



        // PyObject obj;


        addBudgetBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view1){
                if (! Python.isStarted()) {
                    Python.start(new AndroidPlatform(requireContext()));
                }

                DocumentReference user = db.collection("users").document(Objects.requireNonNull(currentEmail));


                int[] age = {0};
                int[] livingArea = {0};
                int[] houseOldSize = {0};

                user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        age[0] = Integer.parseInt(String.valueOf(documentSnapshot.get("age")));
                        String area = String.valueOf(documentSnapshot.get("living_area"));
                        if (area.equals("South Isael")){
                            livingArea[0] = 0;
                        }
                        else if(area.equals("Central Israel")){
                            livingArea[0] = 1;
                        }
                        else if (area.equals("North Israel")){
                            livingArea[0] = 2;
                        }
                        houseOldSize[0] = Integer.parseInt(String.valueOf(documentSnapshot.get("household_size")));
                    }
                });

                int[] totalIncome = {0};

                income.whereEqualTo("email", currentEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                totalIncome[0] += Integer.parseInt(String.valueOf(documentSnapshot.get("amount")));
                            }
                        }
                    }
                });
                //age,Living area,Income,Home,Education,Leisure,Health,Groceries,Shopping,Transportation,Savings & investments,Loans & cc fees,additional expenses,Num of People

                Python py = Python.getInstance();
                final PyObject pyObj = py.getModule("knn");
                PyObject obj = pyObj.callAttr("main", age[0], livingArea[0], totalIncome[0], amounts[0], amounts[3], amounts[4], amounts[2], amounts[1], amounts[8], amounts[5], amounts[6], amounts[7], amounts[9], houseOldSize[0]);
                // TODO: insert the new budget to DB

                double perc = 0;
                switch (Integer.parseInt(obj.toString())){
                    case 0:
                        perc = 0.89;
                        break;
                    case 1:
                        perc = 0.91;
                        break;
                    case 2:
                        perc = 0.93;
                        break;
                    case 3:
                        perc = 0.95;
                        break;
                }
                Map<String, Object> newBudget = new HashMap<>();
                newBudget.put("Education", progressBarEducation.getMax() * perc);
                newBudget.put("Groceries", progressBarGroceries.getMax() * perc);
                newBudget.put("Health", progressBarHealth.getMax() * perc);
                newBudget.put("Home", progressBarHome.getMax() * perc);
                newBudget.put("Leisure", progressBarLeisure.getMax() * perc);
                newBudget.put("Loans", progressBarLoans.getMax() * perc);
                newBudget.put("Other", progressBarOther.getMax() * perc);
                newBudget.put("Savings", progressBarSaving.getMax() * perc);
                newBudget.put("Shopping", progressBarShopping.getMax() * perc);
                newBudget.put("Transportation", progressBarTransportation.getMax() * perc);

                budgetRef.update(newBudget).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        createNewBudget();
                        Toast.makeText(requireContext(), "Budget was updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void createNewBudget(){
        DocumentReference budgetRef = budget.document(Objects.requireNonNull(currentEmail));
        final double[] totalBudget = {0};
        double[] currentBudget = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        budgetRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentBudget[0] = Double.parseDouble(String.valueOf(documentSnapshot.get("Home")));
                currentBudget[1] = Double.parseDouble(String.valueOf(documentSnapshot.get("Groceries")));
                currentBudget[2] = Double.parseDouble(String.valueOf(documentSnapshot.get("Health")));
                currentBudget[3] = Double.parseDouble(String.valueOf(documentSnapshot.get("Education")));
                currentBudget[4] = Double.parseDouble(String.valueOf(documentSnapshot.get("Leisure")));
                currentBudget[5] = Double.parseDouble(String.valueOf(documentSnapshot.get("Transportation")));
                currentBudget[6] = Double.parseDouble(String.valueOf(documentSnapshot.get("Savings")));
                currentBudget[7] = Double.parseDouble(String.valueOf(documentSnapshot.get("Loans")));
                currentBudget[8] = Double.parseDouble(String.valueOf(documentSnapshot.get("Shopping")));
                currentBudget[9] = Double.parseDouble(String.valueOf(documentSnapshot.get("Other")));
                progressBarHome.setMax((int) currentBudget[0]);
                progressBarGroceries.setMax((int) currentBudget[1]);
                progressBarHealth.setMax((int) currentBudget[2]);
                progressBarEducation.setMax((int) currentBudget[3]);
                progressBarLeisure.setMax((int) currentBudget[4]);
                progressBarTransportation.setMax((int) currentBudget[5]);
                progressBarSaving.setMax((int) currentBudget[6]);
                progressBarLoans.setMax((int) currentBudget[7]);
                progressBarShopping.setMax((int) currentBudget[8]);
                progressBarOther.setMax((int) currentBudget[9]);
                totalBudget[0] = Double.parseDouble(String.valueOf(documentSnapshot.get("Home"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Groceries"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Health"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Education"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Leisure"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Transportation"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Savings"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Loans"))) +
                        Double.parseDouble(String.valueOf(documentSnapshot.get("Shopping"))) + Double.parseDouble(String.valueOf(documentSnapshot.get("Other")));
                budgetTextView.setText("Current Budget is " + String.format("%.2f", totalBudget[0]) + "₪");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(requireContext(), "You don't have a budget yet! Please create one.", Toast.LENGTH_SHORT).show();
            }
        });

        double[] amounts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        Map<String, Object> expsObj = new HashMap<>();
        expsObj.put("amount", 0);

        ArrayList<String> ids = new ArrayList<>();

        expenses.whereEqualTo("email", currentEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                        ids.add(snapshot.getId());
                    }
                    for (int i = 0;i < ids.size();i++){
                        expenses.document(ids.get(i)).update(expsObj);
                    }
                    double total = 0;
                    for(int i = 0;i < 10;i++){
                        total += amounts[i];
                    }
                    remainingTextView.setText("Remaining Amount is " + String.format("%.2f", totalBudget[0] - total) +"₪");
                    progressBarHome.setProgress((int) amounts[0]);
                    homeTextView.setText(String.format("%.2f", amounts[0]) + "₪ /" + String.format("%.2f", currentBudget[0]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[0]));
                    progressBarGroceries.setProgress((int) amounts[1]);
                    groceriesTextView.setText(String.format("%.2f", amounts[1]) + "₪ /" + String.format("%.2f", currentBudget[1]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[1]));
                    progressBarHealth.setProgress((int) amounts[2]);
                    healthTextView.setText(String.format("%.2f", amounts[2]) + "₪ /" + String.format("%.2f", currentBudget[2]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[2]));
                    progressBarEducation.setProgress((int) amounts[3]);
                    educationTextView.setText(String.format("%.2f", amounts[3]) + "₪ /" + String.format("%.2f", currentBudget[3]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[3]));
                    progressBarLeisure.setProgress((int) amounts[4]);
                    leisureTextView.setText(String.format("%.2f", amounts[4]) + "₪ /" + String.format("%.2f", currentBudget[4]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[4]));
                    progressBarTransportation.setProgress((int) amounts[5]);
                    transportationTextView.setText(String.format("%.2f", amounts[5]) + "₪ /" + String.format("%.2f", currentBudget[5]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[5]));
                    progressBarSaving.setProgress((int) amounts[6]);
                    savingsTextView.setText(String.format("%.2f", amounts[6]) + "₪ /" + String.format("%.2f", currentBudget[6]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[6]));
                    progressBarLoans.setProgress((int) amounts[7]);
                    loansTextView.setText(String.format("%.2f", amounts[7]) + "₪ /" + String.format("%.2f", currentBudget[7]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[7]));
                    progressBarShopping.setProgress((int) amounts[8]);
                    shoppingTextView.setText(String.format("%.2f", amounts[8]) + "₪ /" + String.format("%.2f", currentBudget[8]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[8]));
                    progressBarOther.setProgress((int) amounts[9]);
                    otherTextView.setText(String.format("%.2f", amounts[9]) + "₪ /" + String.format("%.2f", currentBudget[9]) +"₪");
                    Log.d("Amounts", String.valueOf(amounts[9]));
                }
            }
        });
    }
}