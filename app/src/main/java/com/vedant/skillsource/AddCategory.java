package com.vedant.skillsource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {

    // creating a variables for our button and edittext. 
    private EditText CategoryNameEdt, CategoryDescEdt, CategoryDurationEdt;
    private Button CategoryBtn;

    // creating a constant string variable for our  
    // Category name, description and duration. 
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_Category_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_Category_NAME";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_Category_DESCRIPTION";
    public static final String EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_Category_DURATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        // initializing our variables for each view. 
        CategoryNameEdt = findViewById(R.id.idEdtCategoryName);
        CategoryDescEdt = findViewById(R.id.idEdtCategoryDescription);
        CategoryDurationEdt = findViewById(R.id.idEdtCategoryDuration);
        CategoryBtn = findViewById(R.id.idBtnSaveCategory);

        // below line is to get intent as we 
        // are getting data via an intent. 
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are 
            // setting values to our edit text fields. 
            CategoryNameEdt.setText(intent.getStringExtra(EXTRA_Category_NAME));
            CategoryDescEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            CategoryDurationEdt.setText(intent.getStringExtra(EXTRA_DURATION));
        }
        // adding on click listener for our save button. 
        CategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if 
                // the text fields are empty or not. 
                String CategoryName = CategoryNameEdt.getText().toString();
                String CategoryDesc = CategoryDescEdt.getText().toString();
                String CategoryDuration = CategoryDurationEdt.getText().toString();
                if (CategoryName.isEmpty() || CategoryDesc.isEmpty() || CategoryDuration.isEmpty()) {
                    Toast.makeText(AddCategory.this, "Please enter the valid Category details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our Category. 
                saveCategory(CategoryName, CategoryDesc, CategoryDuration);
            }
        });
    }

    private void saveCategory(String CategoryName, String CategoryDescription, String CategoryDuration) {
        // inside this method we are passing  
        // all the data via an intent. 
        Intent data = new Intent();

        // in below line we are passing all our Category detail. 
        data.putExtra(EXTRA_Category_NAME, CategoryName);
        data.putExtra(EXTRA_DESCRIPTION, CategoryDescription);
        data.putExtra(EXTRA_DURATION, CategoryDuration);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id. 
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data. 
        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data 
        Toast.makeText(this, "Category has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}