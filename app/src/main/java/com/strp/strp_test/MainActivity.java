package com.strp.strp_test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.strp.strp_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST_CODE = 2;

    private ActivityMainBinding binding;
    private CardView lastSelectedCard;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Continue with your existing code
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check and request storage permission if needed
        checkStoragePermission();

        // Initialize CardViews
        MaterialCardView cardStockMode = findViewById(R.id.cardStockMode);
        MaterialCardView cardUltraBatteryMode = findViewById(R.id.cardUltraBatteryMode);
        MaterialCardView cardBalancedMode = findViewById(R.id.cardBalancedMode);
        MaterialCardView cardGamingMode = findViewById(R.id.cardGamingMode);
        MaterialCardView cardAutonomousMode = findViewById(R.id.cardAutonomousMode);

        // Set click listeners for each card
        cardStockMode.setOnClickListener(view -> {
            onClickStockMode(view);
            activateProfile(cardStockMode);
        });

        cardUltraBatteryMode.setOnClickListener(view -> {
            onClickUltraBatteryMode(view);
            activateProfile(cardUltraBatteryMode);
        });

        cardBalancedMode.setOnClickListener(view -> {
            onClickBalancedMode(view);
            activateProfile(cardBalancedMode);
        });

        cardGamingMode.setOnClickListener(view -> {
            onClickGamingMode(view);
            activateProfile(cardGamingMode);
        });

        cardAutonomousMode.setOnClickListener(view -> {
            onClickAutonomousMode(view);
            activateProfile(cardAutonomousMode);
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
                // Log error message if NavController is null
                Log.e("MainActivity", "NavController is null");
            }
        } else {
            // Log error message if NavHostFragment is null
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Handle the Change In-App Background menu item click
            openImagePicker();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri selectedImageUri = data.getData();

            // Implement the logic to set the selected image as the background
            // For simplicity, let's show a Toast message with the selected image URI
            Toast.makeText(this, "Selected Image: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }
}
