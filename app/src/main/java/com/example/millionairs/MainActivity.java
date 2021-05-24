package com.example.millionairs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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

    private ActivityMainBinding binding;
    Fragment selectedFragment = null;
    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = (BottomNavigationView.OnNavigationItemSelectedListener)(new BottomNavigationView.OnNavigationItemSelectedListener() {
        public final boolean onNavigationItemSelected(@NotNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_personal_details:
                    setTitle("Personal details");
                    moveToFragment(new PersonalDetailsFragment());
                    break;
                case R.id.nav_bot:
                    setTitle("Bot");
                    moveToFragment(new BotFragment());
                    break;
                case R.id.nav_expenses:
                    setTitle("Expenses");
                    moveToFragment(new ExpensesFragment());
                    break;
                case R.id.nav_income:
                    setTitle("Income");
                    moveToFragment(new IncomeFragment());
                    break;
                case R.id.nav_budget:
                    setTitle("Budget");
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

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        setTitle("Budget");
        moveToFragment(new BudgetFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            if (currentUser != null && firebaseAuth != null) {
                firebaseAuth.signOut();

                startActivity(new Intent(MainActivity.this,
                        logActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void moveToFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


    }
}