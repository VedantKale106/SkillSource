package com.vedant.skillsource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {

    // below line is the create a variable 
    // for dao and list for all courses. 
    private Dao dao;
    private LiveData<List<Category>> allCourses;

    // creating a constructor for our variables 
    // and passing the variables to it. 
    public CategoryRepository(Application application) {
        CategoryDatabase database = CategoryDatabase.getInstance(application);
        dao = database.Dao();
        allCourses = dao.getAllCourses();
    }

    // creating a method to insert the data to our database. 
    public void insert(Category model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database. 
    public void update(Category model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database. 
    public void delete(Category model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses. 
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    // below method is to read all the courses. 
    public LiveData<List<Category>> getAllCourses() {
        return allCourses;
    }

    // we are creating a async task method to insert new course. 
    private static class InsertCourseAsyncTask extends AsyncTask<Category, Void, Void> {
        private Dao dao;

        private InsertCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Category... model) {
            // below line is use to insert our modal in dao. 
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course. 
    private static class UpdateCourseAsyncTask extends AsyncTask<Category, Void, Void> {
        private Dao dao;

        private UpdateCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Category... models) {
            // below line is use to update 
            // our modal in dao. 
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course. 
    private static class DeleteCourseAsyncTask extends AsyncTask<Category, Void, Void> {
        private Dao dao;

        private DeleteCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Category... models) {
            // below line is use to delete 
            // our course modal in dao. 
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses. 
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllCoursesAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method 
            // to delete all courses. 
            dao.deleteAllCourses();
            return null;
        }
    }
}

