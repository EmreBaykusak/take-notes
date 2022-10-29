package com.company.takenotes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")

public class Note
{
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Title;
    public String Description;

    public Note(String Title, String Description)
    {
        this.Title = Title;
        this.Description = Description;
    }

    public int getId()
    {
        return Id;
    }

    public String getTitle()
    {
        return Title;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setId(int id)
    {
        Id = id;
    }
}
