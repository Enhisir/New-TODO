package com.roar.new_todo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.roar.new_todo.R;
import com.roar.new_todo.model.Note;

public class NoteActivity extends AppCompatActivity {
    private Note current;

    private EditText activity_name;
    private EditText activity_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        activity_name = findViewById(R.id.nameEditNoteText2);
        activity_text = findViewById(R.id.descEditNoteText2);

        Bundle args = getIntent().getExtras();
        current = args.getParcelable("note");

        activity_name.setText(current.name);
        activity_text.setText(current.text);
    }


    public void onSaveButtonClick(View v) {
        current.name = activity_name.getText().toString();
        current.text = activity_text.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("note", current);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onDeleteButtonClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("note", current);
        intent.putExtra("DELETE", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}