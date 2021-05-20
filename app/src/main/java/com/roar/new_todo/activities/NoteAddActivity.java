package com.roar.new_todo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.roar.new_todo.R;
import com.roar.new_todo.model.Note;

public class NoteAddActivity extends AppCompatActivity {
    private Note current;

    private EditText activity_name;
    private EditText activity_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        activity_name = findViewById(R.id.nameEditNoteText);
        activity_text = findViewById(R.id.descEditNoteText);

        Bundle args = getIntent().getExtras();
        current = args.getParcelable("note");

    }

    public void onSaveButtonClick(View v) {
        if(activity_name.getText().toString().length()==0)
        {
            Toast.makeText(this,
                    "чтобы сохранить, дайте заметке название", Toast.LENGTH_SHORT).show();
            return;
        }

        current.name = activity_name.getText().toString();
        current.text = activity_text.getText().toString();

        Intent intent = new Intent();
        System.out.println(current);
        intent.putExtra("note", current);
        setResult(RESULT_OK, intent);
        finish();
    }
}