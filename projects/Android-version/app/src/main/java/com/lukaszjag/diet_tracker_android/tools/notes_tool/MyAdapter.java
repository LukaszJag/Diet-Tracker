package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //<editor-fold desc="Global variables">
    private List<Note> noteList;
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public MyAdapter() {
        this.noteList = new ArrayList<>();
    }
    //</editor-fold>

    // --- CUSTOM METHODS TO MANAGE DATA ---

    //<editor-fold desc="ADD, GET, SET, DELETE - object">
    // ADD an object
    public void addItem(Note note) {
        noteList.add(note);
        // Notify the adapter that an item was inserted at the very end
        notifyItemInserted(noteList.size() - 1);
    }

    // GET an object
    public Note getItem(int position) {
        if (position >= 0 && position < noteList.size()) {
            return noteList.get(position);
        }
        return null;
    }

    // SET (Update) an object
    public void setItem(int position, Note note) {
        if (position >= 0 && position < noteList.size()) {
            noteList.set(position, note);
            // Notify the adapter that a specific item changed
            notifyItemChanged(position);
        }
    }

    // DELETE an object
    public void deleteItem(int position) {
        if (position >= 0 && position < noteList.size()) {
            noteList.remove(position);
            // Notify the adapter to animate removal and shift remaining items
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, noteList.size());
        }
    }
    //</editor-fold>

    // --- RECYCLERVIEW OVERRIDE METHODS ---

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note currentItem = noteList.get(position);

        holder.tv1.setText(currentItem.getNoteTitle());
        holder.tv2.setText(currentItem.getNoteSubtitle());
        holder.tv3.setText(currentItem.getNoteDescription());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // ViewHolder class maps the views inside item_layout.xml
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView1);
            tv2 = itemView.findViewById(R.id.textView2);
            tv3 = itemView.findViewById(R.id.textView3);
        }
    }
}