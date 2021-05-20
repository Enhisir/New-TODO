package com.roar.new_todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.roar.new_todo.R;
import com.roar.new_todo.adapters.NoteAdapter;
import com.roar.new_todo.data.AppDatabase;
import com.roar.new_todo.data.NoteDao;
import com.roar.new_todo.model.Note;

import java.util.ArrayList;

public class NotebookActivity extends AppCompatActivity {

    private AppDatabase db;
    private NoteDao noteDao;

    private ArrayList<Note> notes;
    private ListView notebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);
        setTitle("Заметки");

        db =  Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "table")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        noteDao = db.noteDao();
        notebook = findViewById(R.id.listNotebook);

        notebook.setOnItemLongClickListener((parent, view, position, id) -> {
            Note current = (Note) notebook.getAdapter().getItem(position);
            Intent intent = new Intent(NotebookActivity.this, NoteActivity.class);
            intent.putExtra("note", current);
            startActivityForResult(intent, Note.EDIT);
            return true;
        });

        notebook.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(getApplicationContext(),
                        "сделайте долгий клик, чтобы открыть заметку",
                        Toast.LENGTH_SHORT).show());

        notes = new ArrayList<>(noteDao.getAll());
        setAdapter();
    }

    private void setAdapter() {
        NoteAdapter adapter = new NoteAdapter(getApplicationContext(), notes);
        notebook.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            assert data != null;

            Bundle args = data.getExtras();
            Note current = args.getParcelable("note");

            if (requestCode == Note.EDIT) {
                if (args.getBoolean("DELETE")) {
                    noteDao.delete(current);
                    int position = 0;
                    for (int i = 0; i < notes.size(); ++i) {
                        if (notes.get(i).id == current.id) {
                            position = i;
                            break;
                        }
                    }
                    notes.remove(position);
                } else {
                    int position = 0;
                    for (int i = 0; i < notes.size(); ++i) {
                        if (notes.get(i).id == current.id) {
                            position = i;
                            break;
                        }
                    }
                    notes.set(position, current);
                    noteDao.insertOne(current);
                }
                setAdapter();
            } else if (requestCode == Note.NEW) {
                System.out.println(current);
                noteDao.insertOne(current);
                notes.add(current);
                setAdapter();
            }
        }
    }

    public void onAddClick(View v) {
        Note current = new Note();
        Intent intent = new Intent(getApplicationContext(), NoteAddActivity.class);
        intent.putExtra("note", current);
        startActivityForResult(intent, Note.NEW);
    }

}