package com.example.mvvm_project1.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseShopRepository {

    private CategoryDAO categoryDAO;
    private CourseDAO courseDAO;

    private LiveData<List<Category>> categories;
    private  LiveData<List<Course>> courses;

    public CourseShopRepository(Application application) {
        CourseDatabase courseDatabase = CourseDatabase.getInstance(application);
        categoryDAO = courseDatabase.getCategoryDAO();
        courseDAO = courseDatabase.getCourseDAO();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Course>> getCourses(int categoryId) {
        return courseDAO.getCourses(categoryId);
    }

    public void insertCategory(Category category){
        // Inserting categories
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                categoryDAO.insert(category);

            }
        });
    }

    public void insertCourse(Course course){
        // Inserting courses
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                courseDAO.insert(course);

            }
        });
    }

    public void deleteCategory(Category category){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //delete category
                categoryDAO.delete(category);


            }
        });
    }

    public void deleteCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //delete course
                courseDAO.delete(course);
            }
        });
    }

    public void updateCategory(Category category){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //update category
                categoryDAO.update(category);
            }
        });
    }

    public void updateCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //Update course
                courseDAO.update(course);
            }
        });
    }

}
