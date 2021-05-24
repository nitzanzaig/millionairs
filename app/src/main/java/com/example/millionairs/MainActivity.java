package com.example.millionairs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.millionairs.Fragments.BotFragment;
import com.example.millionairs.Fragments.BudgetFragment;
import com.example.millionairs.Fragments.ExpensesFragment;
import com.example.millionairs.Fragments.IncomeFragment;
import com.example.millionairs.Fragments.PersonalDetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.millionairs.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private ImageView logoutImageView;

    private ActivityMainBinding binding;
    Fragment selectedFragment = null;
    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = (BottomNavigationView.OnNavigationItemSelectedListener)(new BottomNavigationView.OnNavigationItemSelectedListener() {
        public final boolean onNavigationItemSelected(@NotNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_personal_details:
                    toolbar_title.setText(R.string.personal_details);
                    moveToFragment(new PersonalDetailsFragment());
                    break;
                case R.id.nav_bot:
                    toolbar_title.setText(R.string.bot);
                    moveToFragment(new BotFragment());
                    break;
                case R.id.nav_expenses:
                    toolbar_title.setText(R.string.expenses);
                    moveToFragment(new ExpensesFragment());
                    break;
                case R.id.nav_income:
                    toolbar_title.setText(R.string.income);
                    moveToFragment(new IncomeFragment());
                    break;
                case R.id.nav_budget:
                    toolbar_title.setText(R.string.budget);
                    moveToFragment(new BudgetFragment());
                    break;
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.home_toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);

        logoutImageView = findViewById(R.id.logoutImageView);
        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null && firebaseAuth != null) {
                    firebaseAuth.signOut();

                    startActivity(new Intent(MainActivity.this,
                            logActivity.class));
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        toolbar_title.setText("Budget");
        moveToFragment(new BudgetFragment());
    }

    public void moveToFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}