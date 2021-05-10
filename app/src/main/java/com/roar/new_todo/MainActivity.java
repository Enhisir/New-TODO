package com.roar.new_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.roar.new_todo.activities.ActualActivity;
import com.roar.new_todo.activities.DoneActivity;
import com.roar.new_todo.activities.FailedActivity;
import com.roar.new_todo.model.Task;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.actualButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActualActivity.class);
            startActivityForResult(intent, Task.ACTUAL);
        });

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DoneActivity.class);
            startActivityForResult(intent, Task.DONE);
        });

        findViewById(R.id.failedButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FailedActivity.class);
            startActivityForResult(intent, Task.FAILED);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    }
}