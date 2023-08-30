package com.example.mvvm_project1;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mvvm_project1.model.Course;

import java.util.ArrayList;

public class CourseDiffCallback extends DiffUtil.Callback {

    private ArrayList<Course> oldCourseList;
    private ArrayList<Course> newCourseList;

    public CourseDiffCallback(ArrayList<Course> oldCourseList, ArrayList<Course> newCourseList) {
        this.oldCourseList = oldCourseList;
        this.newCourseList = newCourseList;
    }

    @Override
    public int getOldListSize() {
        return oldCourseList!=null ? oldCourseList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newCourseList!=null ? newCourseList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCourseList.get(oldItemPosition).getCourseId() ==
                newCourseList.get(newItemPosition).getCourseId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCourseList.get(oldItemPosition).equals(newCourseList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) { //compares positions in the memory of two objects
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
