package com.company.takenotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNotes(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, NoteUpdateActivity.class);
            intent.putExtra("id",note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("description", note.getDescription());
            startUpdateActivityIntent.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.top_menu:
                Intent i = new Intent(MainActivity.this, NoteAddActivity.class);
                startAddActivityIntent.launch(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override -- deprecated
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK){
//
//            String title = data.getStringExtra("title");
//            String description = data.getStringExtra("description");
//
//            Note note = new Note(title,description);
//            noteViewModel.insert(note);
//        }
//
//        else if(requestCode == 2 && resultCode == RESULT_OK){
//
//            String title = data.getStringExtra("lastTitle");
//            String description = data.getStringExtra("lastDescription");
//            int id = data.getIntExtra("lastId", -1);
//
//            Note note = new Note(title,description);
//            note.setId(id);
//
//            noteViewModel.update(note);
//        }
//    }

    ActivityResultLauncher<Intent> startAddActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if ( result.getResultCode() == RESULT_OK){

                        String title = result.getData().getStringExtra("title");
                        String description = result.getData().getStringExtra("description");

                        Note note = new Note(title,description);
                        noteViewModel.insert(note);
                    }

                }
            }
    );

    ActivityResultLauncher<Intent> startUpdateActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if ( result.getResultCode() == RESULT_OK){

                        String title = result.getData().getStringExtra("lastTitle");
                        String description = result.getData().getStringExtra("lastDescription");
                        int id = result.getData().getIntExtra("lastId", -1);

                        Note note = new Note(title,description);
                        note.setId(id);

                        noteViewModel.update(note);
                    }

                }
            }
    );
}