package com.roar.new_todo.data;

import android.os.Parcelable;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.roar.new_todo.model.Task;


@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}