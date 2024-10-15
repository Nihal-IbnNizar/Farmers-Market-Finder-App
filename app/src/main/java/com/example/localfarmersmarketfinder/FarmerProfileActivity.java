package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FarmerProfileActivity extends AppCompatActivity {

    private EditText etFarmerName, etFarmerEmail, etFarmerLocation, etFarmerContact;
    private Button btnSaveProfile, btnDeleteAccount;
    private DatabaseHandler db;
    private Farmer currentFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);

        db = new DatabaseHandler(this);

        int farmerId = getIntent().getIntExtra("FARMER_ID", -1);
        if (farmerId == -1) {
            Toast.makeText(this, "Error: Farmer not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentFarmer = db.getFarmer(farmerId);

        // Initialize UI elements
        etFarmerName = findViewById(R.id.etFarmerName);
        etFarmerEmail = findViewById(R.id.etFarmerEmail);
        etFarmerLocation = findViewById(R.id.etFarmerLocation);
        etFarmerContact = findViewById(R.id.etFarmerContact);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        // Populate fields with current farmer data
        etFarmerName.setText(currentFarmer.getName());
        etFarmerEmail.setText(currentFarmer.getEmail());
        etFarmerLocation.setText(currentFarmer.getLocation());
        etFarmerContact.setText(currentFarmer.getContactNumber());

        // Save button click listener
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update farmer details
                currentFarmer.setName(etFarmerName.getText().toString().trim());
                currentFarmer.setEmail(etFarmerEmail.getText().toString().trim());
                currentFarmer.setLocation(etFarmerLocation.getText().toString().trim());
                currentFarmer.setContactNumber(etFarmerContact.getText().toString().trim());

                db.updateFarmer(currentFarmer);

                Toast.makeText(FarmerProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete account button click listener
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFarmer(currentFarmer.getId());
                Toast.makeText(FarmerProfileActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                finish();  // Close the activity after deleting the account
            }
        });
    }
}
