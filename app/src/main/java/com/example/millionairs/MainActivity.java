package com.example.millionairs;

import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.millionairs.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Fragment selectedFragment = null;
    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = (BottomNavigationView.OnNavigationItemSelectedListener)(new BottomNavigationView.OnNavigationItemSelectedListener() {
        public final boolean onNavigationItemSelected(@NotNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_personal_details:
                    moveToFragment(new PersonalDetailsFragment());
                    break;
                case R.id.nav_bot:
                    moveToFragment(new BotFragment());
                    break;
                case R.id.nav_expenses:
                    moveToFragment(new ExpensesFragment());
                    break;
                case R.id.nav_income:
                    moveToFragment(new IncomeFragment());
                    break;
                case R.id.nav_budget:
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

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        moveToFragment(new BudgetFragment());


    }

    public void moveToFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


    }


}