package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.graphics.Color;
import android.graphics.Paint;
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
import java.util.List;
import java.util.Set;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static final int SORT_NONE = 0;
    public static final int SORT_DATE_NEWEST = 1;
    public static final int SORT_DATE_OLDEST = 2;
    public static final int SORT_URGENCY_HIGH = 3;
    public static final int SORT_URGENCY_LOW = 4;

    public interface OnItemClickListener {
        void onItemClick(int position, Note note);
    }

    // --- VIEWHOLDER ---
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNoteIndex;
        public TextView tv1, tv2, tv3, tvCategory, tvUrgently, tvDates, tvDaysSince;
        public CheckBox cbIsLearning, cbIsGeneralToDo, cbIsTodayTask, cbIsToBuy;
        public View section1, section2, section3, section4;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteIndex = itemView.findViewById(R.id.tvNoteIndex);
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
            cbIsToBuy = itemView.findViewById(R.id.cbIsToBuy);

            section1 = itemView.findViewById(R.id.section1);
            section2 = itemView.findViewById(R.id.section2);
            section3 = itemView.findViewById(R.id.section3);
            section4 = itemView.findViewById(R.id.section4);
        }
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

    public void setItem(int position, Note note) {
        if (position >= 0 && position < noteList.size()) {
            Note oldNote = noteList.get(position);
            int origIdx = originalList.indexOf(oldNote);
            if (origIdx != -1) originalList.set(origIdx, note);
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
        Collections.sort(noteList, (n1, n2) -> {
            switch (sortCriteria) {
                case SORT_DATE_NEWEST:
                    return (n2.getDateCreated() != null ? n2.getDateCreated() : "")
                            .compareTo(n1.getDateCreated() != null ? n1.getDateCreated() : "");
                case SORT_DATE_OLDEST:
                    return (n1.getDateCreated() != null ? n1.getDateCreated() : "")
                            .compareTo(n2.getDateCreated() != null ? n2.getDateCreated() : "");
                case SORT_URGENCY_HIGH:
                    return Integer.compare(getUrgencyWeight(n2), getUrgencyWeight(n1));
                case SORT_URGENCY_LOW:
                    return Integer.compare(getUrgencyWeight(n1), getUrgencyWeight(n2));
                default:
                    return 0;
            }
        });
    }

    private int getUrgencyWeight(Note note) {
        if (note == null) return 0;
        int idx = note.getUrgentScaleEnglish().indexOf(note.getNoteUrgently());
        return (idx != -1) ? idx : 0;
    }

    public void filter(String subtitle, String selectedMainCategory, String selectedSubcategory,
                       boolean showLearningOnly, boolean showGeneralOnly, boolean showTodayOnly, boolean showToBuyOnly) {
        filter(subtitle, selectedMainCategory, selectedSubcategory, showLearningOnly, showGeneralOnly, showTodayOnly, showToBuyOnly, "ALL", null);
    }

    public void filter(String subtitle, String selectedMainCategory, String selectedSubcategory,
                       boolean showLearningOnly, boolean showGeneralOnly, boolean showTodayOnly, boolean showToBuyOnly,
                       Set<String> selectedDates) {
        filter(subtitle, selectedMainCategory, selectedSubcategory, showLearningOnly, showGeneralOnly, showTodayOnly, showToBuyOnly, "ALL", selectedDates);
    }

    public void filter(String subtitle, String selectedMainCategory, String selectedSubcategory,
                       boolean showLearningOnly, boolean showGeneralOnly, boolean showTodayOnly, boolean showToBuyOnly,
                       String statusFilter) {
        filter(subtitle, selectedMainCategory, selectedSubcategory, showLearningOnly, showGeneralOnly, showTodayOnly, showToBuyOnly, statusFilter, null);
    }

    public void filter(String subtitle, String selectedMainCategory, String selectedSubcategory,
                       boolean showLearningOnly, boolean showGeneralOnly, boolean showTodayOnly, boolean showToBuyOnly,
                       String statusFilter, Set<String> selectedDates) {
        noteList.clear();
        String qSub = (subtitle != null) ? subtitle.toLowerCase().trim() : "";
        boolean isAllCategories = (selectedMainCategory == null || selectedMainCategory.equals("All Categories"));
        boolean isAllSubcategories = (selectedSubcategory == null || selectedSubcategory.equals("All Subcategories"));

        boolean hasDateFilter = (selectedDates != null && !selectedDates.isEmpty());

        for (Note note : originalList) {
            boolean matchSub = qSub.isEmpty() || (note.getNoteSubtitle() != null && note.getNoteSubtitle().toLowerCase().contains(qSub));

            boolean matchCat = true;
            if (!isAllCategories) {
                matchCat = note.getNoteCategory() != null && note.getNoteCategory().equalsIgnoreCase(selectedMainCategory.trim());
                if (matchCat && !isAllSubcategories) {
                    matchCat = note.getNoteSubcategory() != null && note.getNoteSubcategory().equalsIgnoreCase(selectedSubcategory.trim());
                }
            }

            boolean matchLearning = !showLearningOnly || note.isLearning();
            boolean matchGeneral = !showGeneralOnly || note.isGeneralToDo();
            boolean matchToday = !showTodayOnly || note.isTodayTask();
            boolean matchToBuy = !showToBuyOnly || note.isToBuyTask();

            boolean matchStatus = true;
            if ("DONE".equalsIgnoreCase(statusFilter)) {
                matchStatus = note.isDone();
            } else if ("DELETED".equalsIgnoreCase(statusFilter)) {
                matchStatus = note.isDeleted();
            }

            boolean matchDate = true;
            if (hasDateFilter) {
                String created = note.getDateCreated();
                if (created != null && created.length() >= 10) {
                    created = created.substring(0, 10);
                }
                matchDate = (created != null && selectedDates.contains(created));
            }

            if (matchSub && matchCat && matchLearning && matchGeneral && matchToday && matchToBuy && matchStatus && matchDate) {
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

        // Display the database ID from Azure SQL (id column)
        if (holder.tvNoteIndex != null) {
            holder.tvNoteIndex.setText("#" + currentItem.getId());
        }

        holder.section1.setVisibility(hideSection1 ? View.GONE : View.VISIBLE);
        holder.section2.setVisibility(hideSection2 ? View.GONE : View.VISIBLE);
        holder.section3.setVisibility(hideSection3 ? View.GONE : View.VISIBLE);
        holder.section4.setVisibility(hideSection4 ? View.GONE : View.VISIBLE);

        String titlePrefix = "";
        holder.tv1.setPaintFlags(holder.tv1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        holder.tv1.setTextColor(Color.BLACK);

        if (currentItem.isDeleted()) {
            titlePrefix = "[DELETED] ";
            holder.tv1.setTextColor(Color.RED);
            holder.tv1.setPaintFlags(holder.tv1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (currentItem.isDone()) {
            titlePrefix = "[DONE ✓] ";
            holder.tv1.setTextColor(Color.parseColor("#2E7D32"));
        }

        String title = currentItem.getNoteTitle() != null ? currentItem.getNoteTitle().trim() : "";
        if (titlePrefix.isEmpty() && title.isEmpty()) {
            holder.tv1.setVisibility(View.GONE);
        } else {
            holder.tv1.setVisibility(View.VISIBLE);
            holder.tv1.setText(titlePrefix + title);
        }

        String subtitle = currentItem.getNoteSubtitle() != null ? currentItem.getNoteSubtitle().trim() : "";
        if (subtitle.isEmpty()) {
            holder.tv2.setVisibility(View.GONE);
        } else {
            holder.tv2.setVisibility(View.VISIBLE);
            holder.tv2.setText(subtitle);
        }

        String description = currentItem.getNoteDescription() != null ? currentItem.getNoteDescription().trim() : "";
        if (description.isEmpty()) {
            holder.tv3.setVisibility(View.GONE);
        } else {
            holder.tv3.setVisibility(View.VISIBLE);
            holder.tv3.setText(description);
        }

        String catDisplay = (currentItem.getNoteCategory() != null && !currentItem.getNoteCategory().isEmpty())
                ? currentItem.getNoteCategory() : "-";
        if (currentItem.getNoteSubcategory() != null && !currentItem.getNoteSubcategory().trim().isEmpty()) {
            catDisplay += " / " + currentItem.getNoteSubcategory();
        }
        holder.tvCategory.setText("Category: " + catDisplay);

        holder.tvUrgently.setText("Urgently: " + (currentItem.getNoteUrgently() != null ? currentItem.getNoteUrgently() : "-"));

        holder.cbIsLearning.setChecked(currentItem.isLearning());
        holder.cbIsGeneralToDo.setChecked(currentItem.isGeneralToDo());
        holder.cbIsTodayTask.setChecked(currentItem.isTodayTask());
        holder.cbIsToBuy.setChecked(currentItem.isToBuyTask());

        String createdDate = currentItem.getDateCreated() != null ? currentItem.getDateCreated() : "-";
        String deadlineDate = (currentItem.getDateDeadline() != null && !currentItem.getDateDeadline().isEmpty())
                ? currentItem.getDateDeadline() : "None";
        holder.tvDates.setText("Created: " + createdDate + " | Deadline: " + deadlineDate);
        holder.tvDaysSince.setText(currentItem.getDaysSinceCreation() + " days since create");

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) listener.onItemClick(pos, noteList.get(pos));
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void clearAll() {
        this.noteList.clear();
        this.originalList.clear();
        notifyDataSetChanged();
    }
}