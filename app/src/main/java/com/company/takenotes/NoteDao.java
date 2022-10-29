package com.company.takenotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao
{
    @Insert
    public void Insert(Note note);

    @Update
    public void Update(Note note);

    @Delete
    public void Delete(Note note);

    @Query("Select * from Notes order by Id asc")
    LiveData<List<Note>> getAllNotes();
}
