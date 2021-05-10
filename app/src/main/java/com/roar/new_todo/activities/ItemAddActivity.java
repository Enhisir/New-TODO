package com.roar.new_todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.roar.new_todo.R;
import com.roar.new_todo.model.Task;

public class ItemAddActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_item_add);

        activity_name = findViewById(R.id.activityItemNameText2);
        activity_text = findViewById(R.id.activityItemDescText2);
        activity_spinner = findViewById(R.id.activityItemStatusSpinner2);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
                R.array.statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_spinner.setAdapter(adapter);
        activity_important = findViewById(R.id.activityItemImpSwitch2);
        activity_have_date = findViewById(R.id.activityItemDateSwitch2);
        activity_date = findViewById(R.id.activityItemDate2);

        Bundle args = getIntent().getExtras();
        current = args.getParcelable("task");

        activity_have_date.setOnClickListener(v -> {
            if (activity_have_date.isChecked()) {
                activity_date.setVisibility(View.VISIBLE);
            } else {
                activity_date.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onSaveButtonClick(View v) {
        if(activity_name.getText().toString().length()==0)
        {
            Toast.makeText(this,
                    "чтобы сохранить, дайте задаче название", Toast.LENGTH_SHORT).show();
            return;
        }

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

}