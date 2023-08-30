package com.example.mvvm_project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mvvm_project1.databinding.ActivityAddBinding;
import com.example.mvvm_project1.model.Course;

public class AddEditActivity extends AppCompatActivity {

    private ActivityAddBinding activityAddBinding;
    private AddEditActivityClickHandlers clickHandlers;
    private Course course;
    private Intent intent;
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_PRICE = "course_price";
    public static final String CATEGORY_ID = "category_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Binding
        activityAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        clickHandlers = new AddEditActivityClickHandlers(this);
        activityAddBinding.setClickHandler(clickHandlers);

        // Data
        course = new Course();
        activityAddBinding.setCourse(course);

        intent = getIntent();
        if(intent.hasExtra(CATEGORY_ID)) {
            setTitle("Edit Course");
            //update course
            String course_name = intent.getStringExtra(COURSE_NAME);
            String course_price = intent.getStringExtra(COURSE_PRICE);
            Log.i("CSV", course_name + " " + course_price);
            course.setCourseName(course_name);
            course.setUnitPrice(course_price);
        }else {
            setTitle("Add New Course");
        }

    }

    public class AddEditActivityClickHandlers{
        Context context;

        public AddEditActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSubmitClick(View view){
            if(course.getCourseName() != null){
                //add new course
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(COURSE_NAME, course.getCourseName());
                i.putExtra(COURSE_PRICE, course.getUnitPrice());

                setResult(RESULT_OK, i);
                finish();
            }else{
                Toast.makeText(context, "Course name is empty", Toast.LENGTH_SHORT).show();
            }

        }
    }
}