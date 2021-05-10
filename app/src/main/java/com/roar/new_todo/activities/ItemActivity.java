package com.roar.new_todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.roar.new_todo.R;
import com.roar.new_todo.model.Task;

public class ItemActivity extends AppCompatActivity {
    private Task current;

    private EditText activity_name;
    private EditText activity_text;
    private Spinner activity_spinner;
    private SwitchCompat activity_important;
    private SwitchCompat activity_have_date;
    private EditText activity_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        activity_name = findViewById(R.id.activityItemNameText);
        activity_text = findViewById(R.id.activityItemDescText);
        activity_spinner = findViewById(R.id.activityItemStatusSpinner);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
                R.array.statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_spinner.setAdapter(adapter);
        activity_important = findViewById(R.id.activityItemImpSwitch);
        activity_have_date = findViewById(R.id.activityItemDateSwitch);
        activity_date = findViewById(R.id.activityItemDate);

        Bundle args = getIntent().getExtras();
        current = args.getParcelable("task");

        activity_name.setText(current.name);
        activity_text.setText(current.text);
        activity_spinner.setSelection(current.status);
        activity_important.setChecked(current.important);
        activity_have_date.setChecked(current.have_date);
        activity_have_date.setOnClickListener(v -> {
            if (activity_have_date.isChecked()) {
                activity_date.setVisibility(View.VISIBLE);
            } else {
                activity_date.setVisibility(View.INVISIBLE);
            }
        });

        if (current.have_date) {
            activity_date.setVisibility(View.VISIBLE);
            activity_date.setText(current.getDateInString());
        }
    }

    public void onSaveButtonClick(View v) {
        current.name = activity_name.getText().toString();
        current.text = activity_text.getText().toString();
        current.status = activity_spinner.getSelectedItemPosition();
        current.important = activity_important.isChecked();
        current.have_date = activity_have_date.isChecked();
        if (current.have_date) {
            current.setDate(activity_date.getText().toString());
        }

        Intent intent = new Intent();
        intent.putExtra("task", current);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onDeleteButtonClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("task", current);
        intent.putExtra("DELETE", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}