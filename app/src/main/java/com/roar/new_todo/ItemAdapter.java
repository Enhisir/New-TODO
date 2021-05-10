package com.roar.new_todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roar.new_todo.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Task> {
    public ItemAdapter(Context context, ArrayList<Task> arr) {
        super(context, R.layout.adapter_item, arr.toArray(new Task[arr.size()]));
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
        }

        ((TextView)convertView.findViewById(R.id.nameTextView)).setText(task.name);

        if (task.status == Task.ACTUAL) {
            ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(
                    R.drawable.in_progress);
        } else if (task.status == Task.DONE) {
            ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.done);
        } else if (task.status == Task.FAILED) {
            ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(
                    R.drawable.failed);
        }

        if (task.important) {
            ((TextView)convertView.findViewById(R.id.importantTextView)).setText("Важное");
        } else {
            ((TextView)convertView.findViewById(R.id.importantTextView)).setText("");
        }

        if (task.have_date) {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String value = "Дата завершения: " + format.format(task.getDate());
            ((TextView)convertView.findViewById(R.id.dateTextView)).setText(value);
        } else {
            ((TextView)convertView.findViewById(R.id.dateTextView)).setText("");
        }

        return convertView;
    }
}
