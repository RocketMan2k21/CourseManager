package com.example.mvvm_project1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_project1.databinding.CourseListItemBinding;
import com.example.mvvm_project1.model.Course;
import com.example.mvvm_project1.model.OnItemClickListener;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private ArrayList<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;


    class CourseViewHolder extends RecyclerView.ViewHolder{

        private CourseListItemBinding listItemBinding;

        public CourseViewHolder(@NonNull CourseListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;

            listItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();

                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(clickedPosition));
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseListItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.course_list_item,
                parent,
                false);

        return new CourseViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);

        holder.listItemBinding.setCourse(course);
    }

    @Override
    public int getItemCount() {
        return null!=courses ? courses.size():0;
    }

    public void setCourses(ArrayList<Course> newCourses) {
//        this.courses = courses;
//        notifyDataSetChanged();

        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new CourseDiffCallback(courses, newCourses));
        courses = newCourses;
        result.dispatchUpdatesTo(CourseAdapter.this);
    }

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
