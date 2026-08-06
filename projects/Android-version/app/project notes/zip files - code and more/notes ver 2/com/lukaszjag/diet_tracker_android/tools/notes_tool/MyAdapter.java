package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> noteList;
    private List<Note> originalList;

    public MyAdapter() {
        this.noteList = new ArrayList<>();
        this.originalList = new ArrayList<>();
    }

    public List<Note> getOriginalList() {
        return originalList;
    }

    public void addItem(Note note) {
        noteList.add(note);
        originalList.add(note);
        notifyItemInserted(noteList.size() - 1);
    }

    public Note getItem(int position) {
        if (position >= 0 && position < noteList.size()) {
            return noteList.get(position);
        }
        return null;
    }

    public void setItem(int position, Note note) {
        if (position >= 0 && position < noteList.size()) {
            Note oldNote = noteList.get(position);
            int origIdx = originalList.indexOf(oldNote);
            if (origIdx != -1) {
                originalList.set(origIdx, note);
            }
            noteList.set(position, note);
            notifyItemChanged(position);
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < noteList.size()) {
            Note note = noteList.get(position);
            originalList.remove(note);
            noteList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, noteList.size());
        }
    }

    public void filter(String subtitle, String category, String urgently) {
        noteList.clear();
        String qSub = subtitle != null ? subtitle.toLowerCase().trim() : "";
        String qCat = category != null ? category.toLowerCase().trim() : "";
        String qUrg = urgently != null ? urgently.toLowerCase().trim() : "";

        for (Note note : originalList) {
            boolean matchSub = qSub.isEmpty() || (note.getNoteSubtitle() != null && note.getNoteSubtitle().toLowerCase().contains(qSub));
            boolean matchCat = qCat.isEmpty() || (note.getNoteCategory() != null && note.getNoteCategory().toLowerCase().contains(qCat));
            boolean matchUrg = qUrg.isEmpty() || (note.getNoteUrgently() != null && note.getNoteUrgently().toLowerCase().contains(qUrg));

            if (matchSub && matchCat && matchUrg) {
                noteList.add(note);
            }
        }
        notifyDataSetChanged();
    }

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

        holder.tvCategory.setText("Category: " + (currentItem.getNoteCategory() != null ? currentItem.getNoteCategory() : "-"));
        holder.tvUrgently.setText("Urgently: " + (currentItem.getNoteUrgently() != null ? currentItem.getNoteUrgently() : "-"));

        holder.cbIsLearning.setChecked(currentItem.isLearning());
        holder.cbIsGeneralToDo.setChecked(currentItem.isGeneralToDo());
        holder.cbIsTodayTask.setChecked(currentItem.isIisTodayTask());

        String createdDate = currentItem.getDateCreated() != null ? currentItem.getDateCreated() : "-";
        String deadlineDate = (currentItem.getDateDeadline() != null && !currentItem.getDateDeadline().isEmpty()) ? currentItem.getDateDeadline() : "None";
        holder.tvDates.setText("Created: " + createdDate + " | Deadline: " + deadlineDate);

        long daysSince = currentItem.getDaysSinceCreation();
        holder.tvDaysSince.setText(daysSince + " days since create");
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;
        TextView tvCategory, tvUrgently, tvDates, tvDaysSince;
        CheckBox cbIsLearning, cbIsGeneralToDo, cbIsTodayTask;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView1);
            tv2 = itemView.findViewById(R.id.textView2);
            tv3 = itemView.findViewById(R.id.textView3);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvUrgently = itemView.findViewById(R.id.tvUrgently);
            tvDates = itemView.findViewById(R.id.tvDates);
            tvDaysSince = itemView.findViewById(R.id.tvDaysSince);
            cbIsLearning = itemView.findViewById(R.id.cbIsLearning);
            cbIsGeneralToDo = itemView.findViewById(R.id.cbIsGeneralToDo);
            cbIsTodayTask = itemView.findViewById(R.id.cbIsTodayTask);
        }
    }
}
