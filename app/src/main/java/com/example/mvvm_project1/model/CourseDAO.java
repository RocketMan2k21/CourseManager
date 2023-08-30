package com.example.mvvm_project1.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses WHERE category_id == :categoryId")
    LiveData<List<Course>> getCourses(int categoryId);
}
