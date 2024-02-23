package com.strp.strp_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.strp.strp_test.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final String PREF_IMAGE_URI = "backgroundImageUri";
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST_CODE = 2;

    private ActivityMainBinding binding;
    private SwitchMaterial lastSelectedSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkStoragePermission();

        SwitchMaterial switchStockMode = findViewById(R.id.switchStockMode);
        SwitchMaterial switchUltraBatteryMode = findViewById(R.id.switchUltraBatteryMode);
        SwitchMaterial switchBalancedMode = findViewById(R.id.switchBalancedMode);
        SwitchMaterial switchGamingMode = findViewById(R.id.switchGamingMode);
        SwitchMaterial switchAutonomousMode = findViewById(R.id.switchAutonomousMode);

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

        setSupportActionBar(binding.toolbar);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mobile_navigation);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        } else {
            Log.e("MainActivity", "NavHostFragment is null");
        }

        Uri savedImageUri = getSavedImageUri();
        if (savedImageUri != null) {
            setActivityBackground(savedImageUri);
        }
    }

    private void activateProfile(SwitchMaterial selectedSwitch) {
        if (lastSelectedSwitch != null && lastSelectedSwitch != selectedSwitch) {
            lastSelectedSwitch.setChecked(false);
        }

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
            Uri selectedImageUri = data.getData();
            saveImageUri(selectedImageUri);
            setActivityBackground(selectedImageUri);
        }
    }

    private void setActivityBackground(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .apply(new RequestOptions().centerCrop())
                .into(binding.backgroundImage);
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    private void saveImageUri(Uri imageUri) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_IMAGE_URI, imageUri.toString());
        editor.apply();
    }

    private Uri getSavedImageUri() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String uriString = preferences.getString(PREF_IMAGE_URI, null);
        return uriString != null ? Uri.parse(uriString) : null;
    }
}