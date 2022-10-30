package com.example.notesappmvvm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappmvvm.Activity.UpdateNoteActivity;
import com.example.notesappmvvm.MainActivity;
import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;

import java.io.Serializable;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;

    public NotesRecyclerAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        Notes note = notes.get(holder.getAdapterPosition());

        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubtitle);
        holder.notes.setText(note.notes);

        holder.date.setText(note.notesDate);

        if(note.priority.equals("1"))
            holder.priority.setBackgroundResource(R.drawable.green_circle_shape);
        else if(note.priority.equals("2"))
            holder.priority.setBackgroundResource(R.drawable.yellow_circle_shape);
        else
            holder.priority.setBackgroundResource(R.drawable.red_circle_shape);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);
            intent.putExtra("note", (Serializable) note);
            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class notesViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView subtitle;
        TextView notes;
        TextView date;
        View priority;

        public notesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            notes = itemView.findViewById(R.id.notes);
            date = itemView.findViewById(R.id.date);
            priority = itemView.findViewById(R.id.priotityView);
        }
    }
}
