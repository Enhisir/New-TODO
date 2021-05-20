package com.roar.new_todo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roar.new_todo.R;
import com.roar.new_todo.model.Note;
import java.util.ArrayList;


public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, ArrayList<Note> arr) {
        super(context, R.layout.adapter_item, arr.toArray(new Note[arr.size()]));
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Note current = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_note, null);
        }

        ((TextView)convertView.findViewById(R.id.noteNameText)).setText(current.name);
        ((TextView)convertView.findViewById(R.id.noteDesсText)).setText(current.text);

        return convertView;
    }
}

