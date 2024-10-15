package com.example.localfarmersmarketfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MarketBrowseActivity extends AppCompatActivity {

    private EditText etProduct; // Removed etLocation since it's not needed
    private Button btnSearch;
    private RecyclerView rvMarkets;
    private DatabaseHandler db;
    private MarketAdapter marketAdapter;
    private List<Farmer> farmers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_browse);

        // Initialize the database handler
        db = new DatabaseHandler(this);

        // Initialize UI elements
        etProduct = findViewById(R.id.etProduct); // No etLocation
        btnSearch = findViewById(R.id.btnSearch);
        rvMarkets = findViewById(R.id.rvMarkets);

        // Set up RecyclerView
        farmers = new ArrayList<>();
        marketAdapter = new MarketAdapter(farmers);
        rvMarkets.setLayoutManager(new LinearLayoutManager(this));
        rvMarkets.setAdapter(marketAdapter);

        // Set click listener for search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = etProduct.getText().toString().trim(); // Only product

                if (product.isEmpty()) {
                    Toast.makeText(MarketBrowseActivity.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchMarkets(product); // Removed location from the search
            }
        });
    }

    // Search for markets by product
    private void searchMarkets(String product) {
        List<Farmer> searchResults = db.searchMarketsByProduct(product); // Search by product only

        farmers.clear(); // Clear previous results
        farmers.addAll(searchResults); // Add new search results
        marketAdapter.notifyDataSetChanged(); // Notify adapter of data changes

        if (farmers.isEmpty()) {
            Toast.makeText(this, "No farmers found", Toast.LENGTH_SHORT).show();
        }
    }
}
