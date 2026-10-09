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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static final int SORT_NONE = 0;
    public static final int SORT_DATE_NEWEST = 1;
    public static final int SORT_DATE_OLDEST = 2;
    public static final int SORT_URGENCY_HIGH = 3;
    public static final int SORT_URGENCY_LOW = 4;

    public interface OnItemClickListener {
        void onItemClick(int position, Note note);
    }

    private List<Note> noteList;
    private List<Note> originalList;
    private OnItemClickListener listener;

    private boolean hideSection1 = false;
    private boolean hideSection2 = false;
    private boolean hideSection3 = false;
    private boolean hideSection4 = false;

    private int sortCriteria = SORT_NONE;

    public MyAdapter() {
        this.noteList = new ArrayList<>();
        this.originalList = new ArrayList<>();
    }

    public List<Note> getOriginalList() {
        return originalList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setSectionVisibilities(boolean hideS1, boolean hideS2, boolean hideS3, boolean hideS4) {
        this.hideSection1 = hideS1;
        this.hideSection2 = hideS2;
        this.hideSection3 = hideS3;
        this.hideSection4 = hideS4;
        notifyDataSetChanged();
    }

    public void setSortCriteria(int criteria) {
        this.sortCriteria = criteria;
        applySort();
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        noteList.add(note);
        originalList.add(note);
        applySort();
        notifyItemInserted(noteList.indexOf(note));
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
            applySort();
            notifyDataSetChanged();
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

    private void applySort() {
        if (sortCriteria == SORT_NONE) return;

        Collections.sort(noteList, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                switch (sortCriteria) {
                    case SORT_DATE_NEWEST: {
                        String d1 = n1.getDateCreated() != null ? n1.getDateCreated() : "";
                        String d2 = n2.getDateCreated() != null ? n2.getDateCreated() : "";
                        return d2.compareTo(d1);
                    }
                    case SORT_DATE_OLDEST: {
                        String d1 = n1.getDateCreated() != null ? n1.getDateCreated() : "";
                        String d2 = n2.getDateCreated() != null ? n2.getDateCreated() : "";
                        return d1.compareTo(d2);
                    }
                    case SORT_URGENCY_HIGH:
                        return Integer.compare(getUrgencyWeight(n2), getUrgencyWeight(n1));
                    case SORT_URGENCY_LOW:
                        return Integer.compare(getUrgencyWeight(n1), getUrgencyWeight(n2));
                    default:
                        return 0;
                }
            }
        });
    }

    private int getUrgencyWeight(Note note) {
        if (note == null) return 0;
        String urgency = note.getNoteUrgently();
        if (urgency == null) return 0;

        int idx = note.getUrgentScaleEnglish().indexOf(urgency);
        if (idx != -1) return idx;

        return 0;
    }

    public void filter(String subtitle, List<String> selectedCategories, String selectedUrgency,
                       boolean showLearningOnly, boolean showGeneralOnly, boolean showTodayOnly) {
        noteList.clear();
        String qSub = subtitle != null ? subtitle.toLowerCase().trim() : "";
        String qUrg = (selectedUrgency == null || selectedUrgency.equals("All Urgencies")) ? "" : selectedUrgency.toLowerCase().trim();

        for (Note note : originalList) {
            boolean matchSub = qSub.isEmpty() || (note.getNoteSubtitle() != null && note.getNoteSubtitle().toLowerCase().contains(qSub));

            // Evaluates multi-selection list filter for Categories
            boolean matchCat = true;
            if (selectedCategories != null && !selectedCategories.isEmpty()) {
                matchCat = false;
                if (note.getNoteCategory() != null) {
                    String noteCat = note.getNoteCategory().trim().toLowerCase();
                    for (String selected : selectedCategories) {
                        if (selected.trim().toLowerCase().equals(noteCat)) {
                            matchCat = true;
                            break;
                        }
                    }
                }
            }

            boolean matchUrg = qUrg.isEmpty() || (note.getNoteUrgently() != null && note.getNoteUrgently().toLowerCase().contains(qUrg));

            boolean matchLearning = !showLearningOnly || note.isLearning();
            boolean matchGeneral = !showGeneralOnly || note.isGeneralToDo();
            boolean matchToday = !showTodayOnly || note.isIisTodayTask();

            if (matchSub && matchCat && matchUrg && matchLearning && matchGeneral && matchToday) {
                noteList.add(note);
            }
        }
        applySort();
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

        holder.section1.setVisibility(hideSection1 ? View.GONE : View.VISIBLE);
        holder.section2.setVisibility(hideSection2 ? View.GONE : View.VISIBLE);
        holder.section3.setVisibility(hideSection3 ? View.GONE : View.VISIBLE);
        holder.section4.setVisibility(hideSection4 ? View.GONE : View.VISIBLE);

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

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(pos, noteList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;
        TextView tvCategory, tvUrgently, tvDates, tvDaysSince;
        CheckBox cbIsLearning, cbIsGeneralToDo, cbIsTodayTask;
        View section1, section2, section3, section4;

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

            section1 = itemView.findViewById(R.id.section1);
            section2 = itemView.findViewById(R.id.section2);
            section3 = itemView.findViewById(R.id.section3);
            section4 = itemView.findViewById(R.id.section4);
        }
    }
}