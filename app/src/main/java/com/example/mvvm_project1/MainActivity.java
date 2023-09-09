package com.example.mvvm_project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mvvm_project1.databinding.ActivityMainBinding;
import com.example.mvvm_project1.model.Category;
import com.example.mvvm_project1.model.Course;
import com.example.mvvm_project1.model.CourseShopRepository;
import com.example.mvvm_project1.model.OnItemClickListener;
import com.example.mvvm_project1.model.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

//Roman Duda

public class MainActivity extends AppCompatActivity {

    //Request codes

    private static final int ADD_NEW_COURSE_REQUEST_CODE = 1;
    private static final int EDIT_COURSE_REQUEST_CODE = 2;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivityClickHandlers clickHandlers;
    private ArrayList<Category> categoriesList;
    private ActivityMainBinding activityMainBinding;

    private Category selectedCategory;
    private Course selectedCourse;

    // Recycler view

    private RecyclerView courseRecyclerView;
    private  CourseAdapter courseAdapter;
    private ArrayList<Course> coursesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        clickHandlers = new MainActivityClickHandlers();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setClickHandlers(clickHandlers);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList = (ArrayList<Category>) categories;

                for (Category c : categories) {
                    Log.i("TAG", c.getCategoryName() + " id:" + c.getId());
                }

                showOnSpinner();
            }
        });

        mainActivityViewModel.getCoursesOfSelectedCategory(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for (Course c : courses) {
                    Log.v("TAG", c.getCourseName() + " category_id: " + c.getCategoryId());
                }
            }
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoriesList
        );

        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    public void LoadCoursesArrayList(int categoryID){
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                coursesList = (ArrayList<Course>) courses;
                LoadRecyclerView();
            }
        });
    }

    private void LoadRecyclerView() {
        courseRecyclerView = activityMainBinding.secondaryLayout.recyclerview;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setHasFixedSize(true);

        courseAdapter = new CourseAdapter();
        courseAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                selectedCourse = course;
                Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
                intent.putExtra("category_id", course.getCategoryId());
                intent.putExtra("course_name", course.getCourseName());
                intent.putExtra("course_price", course.getUnitPrice());
                Log.i("0CSV", course.getCourseName() + " ");
                startActivityForResult(intent, EDIT_COURSE_REQUEST_CODE);
            }
        });

        courseRecyclerView.setAdapter(courseAdapter);
        courseAdapter.setCourses(coursesList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainActivityViewModel.deleteCourse(coursesList.get(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(courseRecyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NEW_COURSE_REQUEST_CODE && resultCode == RESULT_OK){
            String courseName = data.getStringExtra(AddEditActivity.COURSE_NAME);
            String coursePrice = data.getStringExtra(AddEditActivity.COURSE_PRICE);
            mainActivityViewModel.addNewCourse(new Course(0, courseName, coursePrice, selectedCategory.getId()));
        }

        if(requestCode == EDIT_COURSE_REQUEST_CODE && resultCode == RESULT_OK){
            String courseName = data.getStringExtra(AddEditActivity.COURSE_NAME);
            String coursePrice = data.getStringExtra(AddEditActivity.COURSE_PRICE);
            selectedCourse.setCourseName(courseName);
            selectedCourse.setUnitPrice(coursePrice);
            mainActivityViewModel.updateCourse(selectedCourse);
        }
    }

    public class MainActivityClickHandlers{

        public void onFabClicked(View view){
            Toast.makeText(MainActivity.this, "FAB is clicked", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), AddEditActivity.class);
            startActivityForResult(i, ADD_NEW_COURSE_REQUEST_CODE);
        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id){
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is: " + selectedCategory.getId() +
                    "\n name is" + selectedCategory.getCategoryName();

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            LoadCoursesArrayList(selectedCategory.getId());
        }
    }
}
