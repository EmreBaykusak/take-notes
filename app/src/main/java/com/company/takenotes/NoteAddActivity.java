package com.company.takenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteAddActivity extends AppCompatActivity {

    EditText title, description;
    Button cancel, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");
        setContentView(R.layout.activity_note_add);
    title = findViewById(R.id.editTextTitle);
    description = findViewById(R.id.editTextDescription);
    cancel = findViewById(R.id.buttonCancel);
    save = findViewById(R.id.buttonSave);

    cancel.setOnClickListener(view ->{
        Toast.makeText(this, "Nothing Saved", Toast.LENGTH_LONG).show();
        finish();
    });
    save.setOnClickListener(v -> {
        saveNote();
    });
    }

    public void saveNote(){
        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();

        Intent i = new Intent();
        i.putExtra("title", noteTitle);
        i.putExtra("description", noteDescription);
        setResult(RESULT_OK, i);
        finish();
    }
}