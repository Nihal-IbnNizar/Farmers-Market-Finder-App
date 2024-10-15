package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnFarmerLogin;
    private Button btnBrowseMarkets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        btnFarmerLogin = findViewById(R.id.btnFarmerLogin);
        btnBrowseMarkets = findViewById(R.id.btnBrowseMarkets);

        // Set click listeners
        btnFarmerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  navigation to FarmerLoginActivity
                 Intent intent = new Intent(MainActivity.this, FarmerLoginActivity.class);
                 startActivity(intent);
            }
        });

        btnBrowseMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  navigation to MarketBrowseActivity
                 Intent intent = new Intent(MainActivity.this, MarketBrowseActivity.class);
                 startActivity(intent);
            }
        });
    }
}