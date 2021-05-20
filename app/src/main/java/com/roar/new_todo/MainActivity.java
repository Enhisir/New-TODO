package com.roar.new_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.roar.new_todo.activities.ActualActivity;
import com.roar.new_todo.activities.DoneActivity;
import com.roar.new_todo.activities.FailedActivity;
import com.roar.new_todo.activities.NotebookActivity;
import com.roar.new_todo.data.AppDatabase;
import com.roar.new_todo.data.TaskDao;
import com.roar.new_todo.model.Task;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Главная");

        // startService(new Intent(this, ToDoService.class));
        updateDatabase();

        findViewById(R.id.actualButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActualActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DoneActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.failedButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FailedActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.notebookButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotebookActivity.class);
            startActivity(intent);
        });
    }

    protected void updateDatabase() {
        AppDatabase db =  Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "table")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        TaskDao taskDao = db.taskDao();
        ArrayList<Task> tasks = new ArrayList<>(taskDao.getByStatus(Task.ACTUAL));
        for (Task task : tasks) {
            if (task.have_date && task.date < (new Date()).getTime()) {
                task.status = Task.FAILED;
                taskDao.insertOne(task);
            }
        }
    }
}