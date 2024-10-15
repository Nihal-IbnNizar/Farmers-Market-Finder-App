package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.List;

public class FarmerDashboardActivity extends AppCompatActivity {

    private EditText etProductName, etQuantity, etUnit, etPrice;
    private Button btnAddProduct, btnShowProfile, btnManageProducts;
    private ListView lvProducts;
    private DatabaseHandler db;
    private Farmer currentFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        db = new DatabaseHandler(this);

        // Get the current farmer's ID from the intent
        int farmerId = getIntent().getIntExtra("FARMER_ID", -1);
        if (farmerId == -1) {
            Toast.makeText(this, "Error: Farmer not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentFarmer = db.getFarmer(farmerId);

        // Initialize UI elements
        etProductName = findViewById(R.id.etProductName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etPrice = findViewById(R.id.etPrice);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnShowProfile = findViewById(R.id.btnShowProfile);
        btnManageProducts = findViewById(R.id.btnManageProducts);
        lvProducts = findViewById(R.id.lvProducts);

        // Show Profile button listener
        btnShowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDashboardActivity.this, FarmerProfileActivity.class);
                intent.putExtra("FARMER_ID", currentFarmer.getId());
                startActivity(intent);
            }
        });

        // Manage Products button listener
        btnManageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerDashboardActivity.this, ManageProductsActivity.class);
                intent.putExtra("FARMER_ID", currentFarmer.getId());
                startActivity(intent);
            }
        });

        // Set click listener for add product button
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString().trim();
                String quantityStr = etQuantity.getText().toString().trim();
                String unit = etUnit.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();

                if (productName.isEmpty() || quantityStr.isEmpty() || unit.isEmpty() || priceStr.isEmpty()) {
                    Toast.makeText(FarmerDashboardActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                Product newProduct = new Product(currentFarmer.getId(), productName, quantity, unit, price);
                db.addProduct(newProduct);

                Toast.makeText(FarmerDashboardActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();

                // Clear input fields
                etProductName.setText("");
                etQuantity.setText("");
                etUnit.setText("");
                etPrice.setText("");

                // Refresh the product list
                updateProductList();
            }
        });

        // Initial update of the product list
        updateProductList();
    }

    private void updateProductList() {
        List<Product> products = db.getAllProductsForFarmer(currentFarmer.getId());
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        lvProducts.setAdapter(adapter);
    }
}
