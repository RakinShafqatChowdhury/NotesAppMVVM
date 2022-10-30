package com.example.notesappmvvm.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.example.notesappmvvm.databinding.ActivityUpdateNoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    String priority = "1";
    Notes selectedNote = new Notes();
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater()); 
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        selectedNote = (Notes) getIntent().getSerializableExtra("note");

        binding.updateTitle.setText(selectedNote.notesTitle);
        binding.updateSubtitle.setText(selectedNote.notesSubtitle);
        binding.updateNotes.setText(selectedNote.notes);

        if (selectedNote.priority.equals("1")) {
            priority = "1";
            binding.greenPriority.setImageResource(R.drawable.ic_done);
        } else if (selectedNote.priority.equals("2")) {
            priority = "2";
            binding.yellowPriority.setImageResource(R.drawable.ic_done);
        } else {
            priority = "3";
            binding.redPriority.setImageResource(R.drawable.ic_done);
        }


        binding.greenPriority.setOnClickListener(view -> {
            binding.greenPriority.setImageResource(R.drawable.ic_done);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "1";
        });
        binding.yellowPriority.setOnClickListener(view -> {
            binding.yellowPriority.setImageResource(R.drawable.ic_done);
            binding.greenPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "2";
        });
        binding.redPriority.setOnClickListener(view -> {
            binding.redPriority.setImageResource(R.drawable.ic_done);
            binding.yellowPriority.setImageResource(0);
            binding.greenPriority.setImageResource(0);
            priority = "3";
        });

        binding.updateNotesBtn.setOnClickListener(view -> {
            String title = binding.updateTitle.getText().toString();
            String subtitle = binding.updateSubtitle.getText().toString();
            String notes = binding.updateNotes.getText().toString();

            UpdateNotes(title, subtitle, notes);

            Toast.makeText(this, "Notes updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void UpdateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date.getTime());

        Notes updateNote = new Notes();
        updateNote.id = selectedNote.id;
        updateNote.notesTitle = title;
        updateNote.notesSubtitle = subtitle;
        updateNote.notes = notes;
        updateNote.notesDate = stringDate;
        updateNote.priority = priority;

        notesViewModel.updateNote(updateNote);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_menu) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateNoteActivity.this, R.style.BottomSheetStyle);
            View view = LayoutInflater.from(UpdateNoteActivity.this).inflate(R.layout.delete_bottom_sheet, findViewById(R.id.bottomSheet));
            bottomSheetDialog.setContentView(view);

            TextView yes = view.findViewById(R.id.deleteYes);
            TextView no = view.findViewById(R.id.deleteNO);

            no.setOnClickListener(view1 -> {
                bottomSheetDialog.dismiss();
            });

            yes.setOnClickListener(view1 -> {
                notesViewModel.deleteNote(selectedNote.id);
                Toast.makeText(this, "Notes deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            });

            bottomSheetDialog.show();

        }
        return true;
    }
}