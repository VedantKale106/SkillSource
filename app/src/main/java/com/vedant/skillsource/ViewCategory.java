package com.vedant.skillsource;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewCategory extends AndroidViewModel {

    // creating a new variable for course repository. 
    private CategoryRepository repository;

    // below line is to create a variable for live 
    // data where all the courses are present. 
    private LiveData<List<Category>> allCourses;

    // constructor for our view modal. 
    public ViewCategory(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCourses = repository.getAllCourses();
    }

    // below method is use to insert the data to our repository. 
    public void insert(Category model) {
        repository.insert(model);
    }

    // below line is to update data in our repository. 
    public void update(Category model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository. 
    public void delete(Category model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list. 
    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    // below method is to get all the courses in our list. 
    public LiveData<List<Category>> getAllCourses() {
        return allCourses;
    }
}

