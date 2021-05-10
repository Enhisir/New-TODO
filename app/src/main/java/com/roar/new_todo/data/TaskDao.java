package com.roar.new_todo.data;

import com.roar.new_todo.model.Task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE id in (:task_ids)")
    List<Task> getByIds(int[] task_ids);

    @Query("SELECT * FROM task WHERE status == :status ORDER BY important")
    List<Task> getByStatus(int status);

    @Query("SELECT * FROM task WHERE id == :id LIMIT 1")
    Task findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Task task);

    @Delete
    void delete(Task task);
}
