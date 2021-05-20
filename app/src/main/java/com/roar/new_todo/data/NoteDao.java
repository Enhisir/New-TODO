package com.roar.new_todo.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.roar.new_todo.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE id in (:note_ids)")
    List<Note> getByIds(int[] note_ids);

    @Query("SELECT * FROM note WHERE id == :id LIMIT 1")
    Note findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Note... notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Note> notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Note note);

    @Delete
    void delete(Note note);
}
