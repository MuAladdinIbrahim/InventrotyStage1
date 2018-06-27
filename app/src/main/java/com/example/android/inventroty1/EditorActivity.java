package com.example.android.inventroty1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.inventroty1.data.BookContract;
import com.example.android.inventroty1.data.BookDbHelper;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the books title
     */
    private EditText mProductNameEditText;

    /**
     * EditText field to enter the books price
     */
    private EditText mProductPriceEditText;

    /**
     * EditText field to enter the books quantity;
     */
    private EditText mProductQuantityEditText;

    /**
     * EditText field to enter the suppliers phone number;
     */
    private EditText mSuppliersNumberEditText;

    /**
     * EditText field to enter the books supplierr
     */
    private Spinner mSupplierSpinner;

    /**
     * Supplier of the product. The possible valid values are in the BooksContract.java
     * <p>
     * * {@link BooksEntry#SUPPLIER_UNKNOWN}, {@link BooksEntry#SUPPLIER_PENGUIN}, {@link BooksEntry#SUPPLIER_PENGUIN},
     * {@link BooksEntry#SUPPLIER_HARPERCOLLINS},{@link BooksEntry#SUPPLIER_SIMONSCHUSTER},
     * {@link BooksEntry#SUPPLIER_HACHETTE}or {@link BooksEntry#SUPPLIER_MACMILLAN}
     */
    private int mSupplier = BookContract.BookEntry.SUPPLIER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mProductNameEditText =  findViewById(R.id.edit_product_name);
        mProductPriceEditText =  findViewById(R.id.edit_price);
        mProductQuantityEditText = findViewById(R.id.edit_quantity);
        mSupplierSpinner =  findViewById(R.id.spinner_supplier);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSupplierSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Nahda))) {
                        mSupplier = BookContract.BookEntry.SUPPLIER_NAHDA;
                    } else if (selection.equals(getString(R.string.Lebnan))) {
                        mSupplier = BookContract.BookEntry.SUPPLIER_LEBNAN;
                    } else if (selection.equals(getString(R.string.Salam))) {
                        mSupplier = BookContract.BookEntry.SUPPLIER_SALAM;
                    } else if (selection.equals(getString(R.string.Ahram))) {
                        mSupplier = BookContract.BookEntry.SUPPLIER_AHRAM;
                    } else if (selection.equals(getString(R.string.Shorouq))) {
                        mSupplier = BookContract.BookEntry.SUPPLIER_SHROUQ;
                    } else {
                        mSupplier = BookContract.BookEntry.SUPPLIER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSupplier = BookContract.BookEntry.SUPPLIER_UNKNOWN;
            }
        });
    }

    /**
     * Get user input from editor and save new product into database.
     */
    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String productNameString = mProductNameEditText.getText().toString().trim();

        String productPriceString = mProductPriceEditText.getText().toString().trim();
        int price = Integer.parseInt (productPriceString);

        String productQuantityString = mProductQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt (productQuantityString);

        String supplierNumberString = mSuppliersNumberEditText.getText().toString().trim();
        int number = Integer.parseInt (supplierNumberString);


        // Create database helper
        BookDbHelper mDbHelper = new BookDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and books attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_Name, productNameString);
        values.put(BookContract.BookEntry.COLUMN_Price, price);
        values.put(BookContract.BookEntry.COLUMN_Quantity, quantity);
        values.put(BookContract.BookEntry.COLUMN_Supplier_Name, mSupplier);
        values.put(BookContract.BookEntry.COLUMN_Supplier_Phone_Number, number);

        // Insert a new row for a book in the database, returning the ID of that new row.
        long newRowId = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save product to database
                insertBook();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
