package com.strp.strp_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    private ActivityMainBinding binding;
    private CardView lastSelectedCard;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the app has permission to access external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            requestStoragePermission();
        } else {
            // Permission already granted, continue with your logic (e.g., open the app)
            openApp();
        }

        // Continue with your existing code
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

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showPermissionDeniedDialog();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    private void openApp() {
        // Add your logic to open the app after permission is granted
        // For example, you can start the main activity
        // Replace YourMainActivity with the actual name of your main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showPermissionDeniedDialog() {
        // Create a LayoutInflater object to inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_dialog_layout, null);

        // Find the TextView in the custom layout and set its text
        TextView messageTextView = customView.findViewById(R.id.dialog_message);
        messageTextView.setText("Without storage access, the app cannot function properly. Please grant the necessary permission. Don't worry, we won't bite :-)\n\nActually, the access is just needed to change the in-app background.");

        // Create the AlertDialog.Builder and set the custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customView);

        // Continue with the rest of your code
        builder.setPositiveButton("Grant Access", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Request storage permission again
                requestStoragePermission();
            }
        });

        builder.setNegativeButton("Skip for now..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setCancelable(false); // Prevent dismissing dialog on outside touch

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, add your logic here (e.g., open the app)
                openApp();
            } else {
                // Permission denied, show a message or take appropriate action
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showPermissionDeniedDialog();
                } else {
                    // User selected "Never Ask Again," take them to app settings
                    showSettingsDialog();
                }
            }
        }

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use storage. You can grant the permission in the app settings.");

        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openAppSettings();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
