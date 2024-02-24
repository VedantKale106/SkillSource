package com.vedant.skillsource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    private RecyclerView CategorysRV;
    private static final int ADD_Category_REQUEST = 1;
    private static final int EDIT_Category_REQUEST = 2;
    private ViewCategory viewmodal;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        // initializing our variable for our recycler view and fab. 
        CategorysRV = findViewById(R.id.idRVCourses);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        // adding on click listener for floating action button. 
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting a new activity for adding a new Category  
                // and passing a constant value in it. 
                Intent intent = new Intent(MainActivity.this, AddCategory.class);
                startActivityForResult(intent, ADD_Category_REQUEST);
            }
        });

        // setting layout manager to our adapter class. 
        CategorysRV.setLayoutManager(new LinearLayoutManager(this));
        CategorysRV.setHasFixedSize(true);

        // initializing adapter for recycler view. 
        final CategoryRVAdapter adapter = new CategoryRVAdapter();

        // setting adapter class for recycler view. 
        CategorysRV.setAdapter(adapter);

        // passing a data from view modal. 
        viewmodal = ViewModelProviders.of(this).get(ViewCategory.class);

        // below line is use to get all the Categorys from view modal. 
        viewmodal.getAllCourses().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> models) {
                // when the data is changed in our models we are 
                // adding that list to our adapter class. 
                adapter.submitList(models);
            }
        });
        // below method is use to add swipe to delete method for item of recycler view. 
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view. 
                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Category deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view. 
                        attachToRecyclerView(CategorysRV);
        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new CategoryRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category model) {
                Toast.makeText(MainActivity.this, model.getCategoryName(), Toast.LENGTH_SHORT).show();
                edit = findViewById(R.id.edit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddCategory.class);
                        intent.putExtra(AddCategory.EXTRA_ID, model.getId());
                        intent.putExtra(AddCategory.EXTRA_Category_NAME, model.getcategoryName());
                        intent.putExtra(AddCategory.EXTRA_DESCRIPTION, model.getcategoryDescription());
                        intent.putExtra(AddCategory.EXTRA_DURATION, model.getcategoryRates());

                        // below line is to start a new activity and
                        // adding a edit course constant.
                        startActivityForResult(intent, EDIT_Category_REQUEST);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_Category_REQUEST && resultCode == RESULT_OK) {
            String CategoryName = data.getStringExtra(AddCategory.EXTRA_Category_NAME);
            String CategoryDescription = data.getStringExtra(AddCategory.EXTRA_DESCRIPTION);
            String CategoryDuration = data.getStringExtra(AddCategory.EXTRA_DURATION);
            Category model = new Category(CategoryName, CategoryDescription, CategoryDuration);
            viewmodal.insert(model);
            Toast.makeText(this, "Category saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_Category_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddCategory.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Category can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String CategoryName = data.getStringExtra(AddCategory.EXTRA_Category_NAME);
            String CategoryDesc = data.getStringExtra(AddCategory.EXTRA_DESCRIPTION);
            String CategoryDuration = data.getStringExtra(AddCategory.EXTRA_DURATION);
            Category model = new Category(CategoryName, CategoryDesc, CategoryDuration);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Category updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Category not saved", Toast.LENGTH_SHORT).show();
        }










    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainact , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mprofile){
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
        }
        if (id == R.id.mlogout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}