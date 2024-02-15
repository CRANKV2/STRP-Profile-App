package com.strp.strp_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.strp.strp_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CardView lastSelectedCard;
    private NavController navController;

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

        // Set up ActionBar
        setSupportActionBar(binding.toolbar);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mobile_navigation);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            if (navController != null) {
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(navView, navController);
            } else {
                Log.e("MainActivity", "NavController is null");
            }
        } else {
            Log.e("MainActivity", "NavHostFragment is null");
        }
    }

    private void activateProfile(CardView selectedCard) {
        if (lastSelectedCard != null) {
            int lastCheckmarkOverlayId = getCheckmarkOverlayId(lastSelectedCard);
            hideCheckmarkOverlay(lastCheckmarkOverlayId, lastSelectedCard);
        }

        int checkmarkOverlayId = getCheckmarkOverlayId(selectedCard);
        showCheckmarkOverlay(checkmarkOverlayId, selectedCard);
        ((MaterialCardView) selectedCard).setCardBackgroundColor(ContextCompat.getColor(this, R.color.activatedColor));

        lastSelectedCard = selectedCard;
    }

    private int getCheckmarkOverlayId(CardView card) {
        int cardId = card.getId();

        if (cardId == R.id.cardStockMode) {
            return R.id.checkmarkOverlayStockMode;
        } else if (cardId == R.id.cardUltraBatteryMode) {
            return R.id.checkmarkOverlayUltraBatteryMode;
        } else if (cardId == R.id.cardBalancedMode) {
            return R.id.checkmarkOverlayBalancedMode;
        } else if (cardId == R.id.cardGamingMode) {
            return R.id.checkmarkOverlayGamingMode;
        } else if (cardId == R.id.cardAutonomousMode) {
            return R.id.checkmarkOverlayAutoMode;
        } else {
            return 0;
        }
    }

    private void hideCheckmarkOverlay(int checkmarkOverlayId, View card) {
        if (checkmarkOverlayId != 0) {
            ImageView checkmarkOverlay = card.findViewById(checkmarkOverlayId);
            checkmarkOverlay.setVisibility(View.GONE);

            // Adjusted to use MaterialCardView
            ((MaterialCardView) card).setCardBackgroundColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    private void showCheckmarkOverlay(int checkmarkOverlayId, View card) {
        if (checkmarkOverlayId != 0) {
            ImageView checkmarkOverlay = card.findViewById(checkmarkOverlayId);
            checkmarkOverlay.setVisibility(View.VISIBLE);
        }
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