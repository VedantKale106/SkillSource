package com.vedant.skillsource;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// below line is for setting table name. 
@Entity(tableName = "category_table")
public class Category {

    // below line is to auto increment 
    // id for each course. 
    @PrimaryKey(autoGenerate = true)

    // variable for our id. 
    private int id;

    // below line is a variable 
    // for course name. 
    private String categoryName;

    // below line is use for 
    // course description. 
    private String categoryDescription;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryRates() {
        return categoryRates;
    }

    public void setCategoryRates(String categoryRates) {
        this.categoryRates = categoryRates;
    }

    // below line is use
    // for course duration. 
    private String categoryRates;

    // below line we are creating constructor class. 
    // inside constructor class we are not passing 
    // our id because it is incrementing automatically 
    public Category(String categoryName, String categoryDescription, String categoryRates) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryRates = categoryRates;
    }

    // on below line we are creating 
    // getter and setter methods. 
    public String getcategoryName() {
        return categoryName;
    }

    public void setcategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getcategoryDescription() {
        return categoryDescription;
    }

    public void setcategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getcategoryRates() {
        return categoryRates;
    }

    public void setcategoryRates(String categoryRates) {
        this.categoryRates = categoryRates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

