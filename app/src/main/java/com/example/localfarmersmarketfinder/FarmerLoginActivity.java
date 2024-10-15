package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FarmerLoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);

        db = new DatabaseHandler(this);

        // Initialize UI elements
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Set click listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(FarmerLoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    loginFarmer(email, password);
                }
            }
        });

        // Set click listener for register text
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerLoginActivity.this, FarmerRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginFarmer(String email, String password) {
        Farmer farmer = db.getFarmerByEmail(email);

        if (farmer != null) {
            if (farmer.getPassword().equals(password)) {
                // Login successful
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                // Start FarmerDashboardActivity
                Intent intent = new Intent(FarmerLoginActivity.this, FarmerDashboardActivity.class);
                intent.putExtra("FARMER_ID", farmer.getId());
                startActivity(intent);
                finish(); // Close the login activity
            } else {
                // Incorrect password
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Farmer not found
            Toast.makeText(this, "Farmer not found", Toast.LENGTH_SHORT).show();
        }
    }
}
