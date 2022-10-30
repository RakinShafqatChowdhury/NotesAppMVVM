package com.example.notesappmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.notesappmvvm.Activity.InsertNoteActivity;
import com.example.notesappmvvm.Adapter.NotesRecyclerAdapter;
import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNoteBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecyclerView;
    NotesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesRecyclerView = findViewById(R.id.mainNotesRV);

        addNoteBtn = findViewById(R.id.newNotesBtn);

        addNoteBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
        });

        notesViewModel.getAllNotes.observe(this, notes -> {
            notesRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
            adapter = new NotesRecyclerAdapter(MainActivity.this, notes);
            notesRecyclerView.setAdapter(adapter);
        });
    }
}