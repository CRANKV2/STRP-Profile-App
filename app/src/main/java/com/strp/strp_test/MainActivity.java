package com.strp.strp_test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.strp.strp_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST_CODE = 2;

    private ActivityMainBinding binding;
    private SwitchMaterial lastSelectedSwitch;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Continue with your existing code
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check and request storage permission if needed
        checkStoragePermission();

        // Initialize Switch buttons
        SwitchMaterial switchStockMode = findViewById(R.id.switchStockMode);
        SwitchMaterial switchUltraBatteryMode = findViewById(R.id.switchUltraBatteryMode);
        SwitchMaterial switchBalancedMode = findViewById(R.id.switchBalancedMode);
        SwitchMaterial switchGamingMode = findViewById(R.id.switchGamingMode);
        SwitchMaterial switchAutonomousMode = findViewById(R.id.switchAutonomousMode);

        // Set OnCheckedChangeListener for each switch
        switchStockMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onClickStockMode(buttonView);
                activateProfile(switchStockMode);
            }
        });

        switchUltraBatteryMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onClickUltraBatteryMode(buttonView);
                activateProfile(switchUltraBatteryMode);
            }
        });

        switchBalancedMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onClickBalancedMode(buttonView);
                activateProfile(switchBalancedMode);
            }
        });

        switchGamingMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onClickGamingMode(buttonView);
                activateProfile(switchGamingMode);
            }
        });

        switchAutonomousMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onClickAutonomousMode(buttonView);
                activateProfile(switchAutonomousMode);
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

    private void activateProfile(SwitchMaterial selectedSwitch) {
        if (lastSelectedSwitch != null && lastSelectedSwitch != selectedSwitch) {
            // Disable the last selected switch
            lastSelectedSwitch.setChecked(false);
        }

        // Enable the newly selected switch
        selectedSwitch.setChecked(true);

        lastSelectedSwitch = selectedSwitch;
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
