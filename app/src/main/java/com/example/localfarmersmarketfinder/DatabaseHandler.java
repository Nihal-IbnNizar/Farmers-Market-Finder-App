package com.example.localfarmersmarketfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "farmersMarketDB";

    // Table names
    private static final String TABLE_FARMERS = "farmers";
    private static final String TABLE_PRODUCTS = "products";

    // Common column names
    private static final String KEY_ID = "id";

    // Farmers Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CONTACT_NUMBER = "contact_number";

    // Products Table Columns names
    private static final String KEY_FARMER_ID = "farmer_id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_UNIT = "unit";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FARMERS_TABLE = "CREATE TABLE " + TABLE_FARMERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_PASSWORD + " TEXT,"
                + KEY_LOCATION + " TEXT," + KEY_CONTACT_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_FARMERS_TABLE);

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARMER_ID + " INTEGER,"
                + KEY_PRODUCT_NAME + " TEXT," + KEY_QUANTITY + " INTEGER,"
                + KEY_UNIT + " TEXT," + KEY_PRICE + " REAL" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Adding a new farmer
    public void addFarmer(Farmer farmer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, farmer.getName());
        values.put(KEY_EMAIL, farmer.getEmail());
        values.put(KEY_PASSWORD, farmer.getPassword());
        values.put(KEY_LOCATION, farmer.getLocation());
        values.put(KEY_CONTACT_NUMBER, farmer.getContactNumber());
        db.insert(TABLE_FARMERS, null, values);
        db.close();
    }

    // Getting a single farmer
    public Farmer getFarmer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FARMERS, new String[]{KEY_ID,
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_LOCATION, KEY_CONTACT_NUMBER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Farmer farmer = new Farmer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        cursor.close();
        return farmer;
    }

    // Getting a farmer by email
    public Farmer getFarmerByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FARMERS, new String[]{KEY_ID,
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_LOCATION, KEY_CONTACT_NUMBER},
                KEY_EMAIL + "=?", new String[]{email}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Farmer farmer = new Farmer(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5));
                cursor.close();
                return farmer;
            }
        }
        return null; // Return null if no farmer is found with that email
    }

    // Deleting a farmer
    public void deleteFarmer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FARMERS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Updating a farmer
    public int updateFarmer(Farmer farmer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, farmer.getName());
        values.put(KEY_EMAIL, farmer.getEmail());
        values.put(KEY_PASSWORD, farmer.getPassword());
        values.put(KEY_LOCATION, farmer.getLocation());
        values.put(KEY_CONTACT_NUMBER, farmer.getContactNumber());

        // updating row
        return db.update(TABLE_FARMERS, values, KEY_ID + "=?", new String[]{String.valueOf(farmer.getId())});
    }

    // Adding a new product
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARMER_ID, product.getFarmerId()); // Ensure this is correct
        values.put(KEY_PRODUCT_NAME, product.getName());
        values.put(KEY_QUANTITY, product.getQuantity());
        values.put(KEY_UNIT, product.getUnit());
        values.put(KEY_PRICE, product.getPrice());

        long result = db.insert(TABLE_PRODUCTS, null, values);
        if (result == -1) {
            Log.d("Database", "Product insertion failed");
        } else {
            Log.d("Database", "Product inserted with ID: " + result);
        }
        db.close();
    }

    // Deleting a product
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Updating a product
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARMER_ID, product.getFarmerId());
        values.put(KEY_PRODUCT_NAME, product.getName());
        values.put(KEY_QUANTITY, product.getQuantity());
        values.put(KEY_UNIT, product.getUnit());
        values.put(KEY_PRICE, product.getPrice());

        // updating row
        return db.update(TABLE_PRODUCTS, values, KEY_ID + "=?", new String[]{String.valueOf(product.getId())});
    }

    // Getting all products for a farmer
    public List<Product> getAllProductsForFarmer(int farmerId) {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + KEY_FARMER_ID + " = " + farmerId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setFarmerId(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setQuantity(cursor.getInt(3));
                product.setUnit(cursor.getString(4));
                product.setPrice(cursor.getDouble(5));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    // Searching farmers by product
    public List<Farmer> searchMarketsByProduct(String productName) {
        List<Farmer> farmerList = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT f.* FROM " + TABLE_FARMERS + " f " +
                "JOIN " + TABLE_PRODUCTS + " p ON f.id = p.farmer_id " +
                "WHERE p.product_name = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{productName});
        if (cursor.moveToFirst()) {
            do {
                Farmer farmer = new Farmer();
                farmer.setId(Integer.parseInt(cursor.getString(0)));
                farmer.setName(cursor.getString(1));
                farmer.setEmail(cursor.getString(2));
                farmer.setPassword(cursor.getString(3));
                farmer.setLocation(cursor.getString(4));
                farmer.setContactNumber(cursor.getString(5));
                farmerList.add(farmer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return farmerList;
    }
}

