package com.strp.strp_test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.strp.strp_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CardView lastSelectedCard;
    private androidx.navigation.NavController NavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize CardViews
        MaterialCardView cardStockMode = findViewById(R.id.cardStockMode);
        MaterialCardView cardUltraBatteryMode = findViewById(R.id.cardUltraBatteryMode);
        MaterialCardView cardBalancedMode = findViewById(R.id.cardBalancedMode);
        MaterialCardView cardGamingMode = findViewById(R.id.cardGamingMode);
        MaterialCardView cardAutonomousMode = findViewById(R.id.cardAutonomousMode);

        // Set click listeners for each card
        cardStockMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStockMode(view);
                activateProfile(cardStockMode);
            }
        });

        cardUltraBatteryMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUltraBatteryMode(view);
                activateProfile(cardUltraBatteryMode);
            }
        });

        cardBalancedMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBalancedMode(view);
                activateProfile(cardBalancedMode);
            }
        });

        cardGamingMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGamingMode(view);
                activateProfile(cardGamingMode);
            }
        });

        cardAutonomousMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAutonomousMode(view);
                activateProfile(cardAutonomousMode);
            }
        });

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.cardsLayout, NavController);
    }

    private void activateProfile(MaterialCardView selectedCard) {
        if (lastSelectedCard != null) {
            ImageView lastCheckmarkOverlay = lastSelectedCard.findViewById(R.id.checkmarkOverlay);
            lastCheckmarkOverlay.setVisibility(View.GONE);
            lastSelectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black));
        }

        ImageView checkmarkOverlay = selectedCard.findViewById(R.id.checkmarkOverlay);
        checkmarkOverlay.setVisibility(View.VISIBLE);
        selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.activatedColor));

        lastSelectedCard = selectedCard;
    }

    public void onClickStockMode(View view) {
        Toast.makeText(this, "Stock Mode Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onClickUltraBatteryMode(View view) {
        Toast.makeText(this, "Ultra Battery Mode Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onClickBalancedMode(View view) {
        Toast.makeText(this, "Balanced Mode Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onClickGamingMode(View view) {
        Toast.makeText(this, "Gaming Mode Clicked", Toast.LENGTH_SHORT).show();
    }

    public void onClickAutonomousMode(View view) {
        Toast.makeText(this, "Autonomous Mode Clicked", Toast.LENGTH_SHORT).show();
    }
}