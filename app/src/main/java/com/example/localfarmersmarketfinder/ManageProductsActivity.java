package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class ManageProductsActivity extends AppCompatActivity {

    private ListView lvManageProducts;
    private DatabaseHandler db;
    private Farmer currentFarmer;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        db = new DatabaseHandler(this);

        // Get the farmer ID passed from the dashboard
        int farmerId = getIntent().getIntExtra("FARMER_ID", -1);
        if (farmerId == -1) {
            Toast.makeText(this, "Error: Farmer not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentFarmer = db.getFarmer(farmerId);

        lvManageProducts = findViewById(R.id.lvManageProducts);

        // Load the farmer's products
        updateProductList();

        // Handle product item click for editing
        lvManageProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);
                // Handle product edit or delete action
                showEditDeleteDialog(selectedProduct);
            }
        });
    }

    private void updateProductList() {
        productList = db.getAllProductsForFarmer(currentFarmer.getId());
        // Check if the product list is null
        if (productList == null || productList.isEmpty()) {
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        lvManageProducts.setAdapter(adapter);
    }

    private void showEditDeleteDialog(final Product product) {
        // Show a dialog with options to edit or delete the product
        new android.app.AlertDialog.Builder(this)
                .setTitle("Manage Product")
                .setMessage("What would you like to do with this product?")
                .setPositiveButton("Edit", (dialog, which) -> {
                    // Launch activity or show dialog to edit product details
                    showEditProductDialog(product);
                })
                .setNegativeButton("Delete", (dialog, which) -> {
                    db.deleteProduct(product.getId());
                    Toast.makeText(ManageProductsActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                    updateProductList();  // Refresh the product list
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void showEditProductDialog(final Product product) {
        // Show a dialog to edit the product details (similar to adding product)
        View editView = getLayoutInflater().inflate(R.layout.dialog_edit_product, null);
        EditText etEditProductName = editView.findViewById(R.id.etEditProductName);
        EditText etEditQuantity = editView.findViewById(R.id.etEditQuantity);
        EditText etEditUnit = editView.findViewById(R.id.etEditUnit);
        EditText etEditPrice = editView.findViewById(R.id.etEditPrice);

        // Pre-fill the product details
        etEditProductName.setText(product.getName());
        etEditQuantity.setText(String.valueOf(product.getQuantity()));
        etEditUnit.setText(product.getUnit());
        etEditPrice.setText(String.valueOf(product.getPrice()));

        new android.app.AlertDialog.Builder(this)
                .setTitle("Edit Product")
                .setView(editView)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Update the product details
                    product.setName(etEditProductName.getText().toString().trim());
                    product.setQuantity(Integer.parseInt(etEditQuantity.getText().toString().trim()));
                    product.setUnit(etEditUnit.getText().toString().trim());
                    product.setPrice(Double.parseDouble(etEditPrice.getText().toString().trim()));

                    db.updateProduct(product);
                    Toast.makeText(ManageProductsActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
                    updateProductList();  // Refresh the product list
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
