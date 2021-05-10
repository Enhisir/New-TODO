package com.roar.new_todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.roar.new_todo.ItemAdapter;
import com.roar.new_todo.R;
import com.roar.new_todo.data.AppDatabase;
import com.roar.new_todo.data.TaskDao;
import com.roar.new_todo.model.Task;

import java.util.ArrayList;

public class DoneActivity extends AppCompatActivity {

    private AppDatabase db;
    private TaskDao taskDao;

    private ArrayList<Task> tasks;
    private ListView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        db =  Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "table")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        taskDao = db.taskDao();
        done = findViewById(R.id.listDone);

        tasks = new ArrayList<>(taskDao.getByStatus(Task.DONE));
        setAdapter();

        done.setOnItemLongClickListener((parent, view, position, id) -> {
            Task current = (Task) done.getAdapter().getItem(position);
            Intent intent = new Intent(DoneActivity.this, ItemActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("task", current);
            startActivityForResult(intent, Task.DONE);
            return true;
        });

        done.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(getApplicationContext(),
                        "сделайте долгий клик, чтобы открыть задачу",
                        Toast.LENGTH_SHORT).show());
    }

    private void setAdapter() {
        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), tasks);
        done.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            assert data != null;
            Bundle args = data.getExtras();
            if (requestCode == Task.DONE) {
                Task current = args.getParcelable("task");
                if (args.getBoolean("DELETE")) {
                    taskDao.delete(current);
                    if (current.status == Task.DONE) {
                        tasks.remove(current);
                        setAdapter();
                    }
                } else {
                    int position = 0;
                    for (int i = 0; i < tasks.size(); ++i) {
                        if (tasks.get(i).id == current.id) {
                            position = i;
                            break;
                        }
                    }
                    if (current.status == Task.DONE) {
                        tasks.set(position, current);
                    } else {
                        tasks.remove(position);
                    }
                    taskDao.insertOne(current);
                    setAdapter();
                }
            } else if (requestCode == Task.NEW) {
                Task current = args.getParcelable("task");
                taskDao.insertOne(current);
                if (current.status == Task.DONE) {
                    tasks.add(current);
                    setAdapter();
                }
            }
        }
    }

    public void onAddClick(View v) {
        Task task = new Task();
        Intent intent = new Intent(getApplicationContext(), ItemAddActivity.class);
        intent.putExtra("task", task);
        startActivityForResult(intent, Task.NEW);
    }
}