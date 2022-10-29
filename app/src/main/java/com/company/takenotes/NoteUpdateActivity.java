package com.company.takenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteUpdateActivity extends AppCompatActivity {

    EditText title, description;
    Button cancel, save;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Note");
        setContentView(R.layout.activity_note_update);

        title = findViewById(R.id.editTextTitleUpdate);
        description = findViewById(R.id.editTextDescriptionUpdate);
        cancel = findViewById(R.id.buttonCancelUpdate);
        save = findViewById(R.id.buttonSaveUpdate);

        getData();


        cancel.setOnClickListener(view ->{
            Toast.makeText(this, "Nothing Updated", Toast.LENGTH_LONG).show();
            finish();
        });
        save.setOnClickListener(v -> {
            updateNote();
        });
    }

    private void updateNote() {
        String lastTitle = title.getText().toString();
        String lastdescription = description.getText().toString();

        Intent intent = new Intent();

        intent.putExtra("lastTitle", lastTitle);
        intent.putExtra("lastDescription",lastdescription);
        if (noteId != -1){
            intent.putExtra("lastId",noteId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void getData(){
        Intent i = getIntent();
        noteId = i.getIntExtra("id", -1);
        String noteTitle = i.getStringExtra("title");
        String noteDescription = i.getStringExtra("description");

        title.setText(noteTitle);
        description.setText(noteDescription);
    }
}
