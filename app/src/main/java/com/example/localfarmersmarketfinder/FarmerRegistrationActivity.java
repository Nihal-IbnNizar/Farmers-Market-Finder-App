package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FarmerRegistrationActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etLocation, etContactNumber;
    private Button btnRegister;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_registration);

        db = new DatabaseHandler(this);

        // Initialize UI elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etLocation = findViewById(R.id.etLocation);
        etContactNumber = findViewById(R.id.etContactNumber);
        btnRegister = findViewById(R.id.btnRegister);

        // Set click listener for register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String location = etLocation.getText().toString().trim();
                String contactNumber = etContactNumber.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty() || contactNumber.isEmpty()) {
                    Toast.makeText(FarmerRegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    registerFarmer(name, email, password, location, contactNumber);
                }
            }
        });
    }

    private void registerFarmer(String name, String email, String password, String location, String contactNumber) {
        // Check if the email is already registered
        if (db.getFarmerByEmail(email) != null) {
            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Farmer object
        Farmer newFarmer = new Farmer(name, email, password, location, contactNumber);

        // Add the farmer to the database
        db.addFarmer(newFarmer);

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

        // Close the activity and return to the login screen
        finish();
    }
}