package com.lukaszjag.diet_tracker_android.gui.notes.calendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.settings.AppSettings;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class NotesCalendarDialog extends Dialog {

    public interface OnCalendarFilterAppliedListener {
        void onFilterApplied(Set<String> selectedDates);
    }

    private final Map<String, Integer> notesCountPerDay;
    private final Set<String> selectedDates;
    private final OnCalendarFilterAppliedListener listener;

    private Calendar currentDisplayCalendar;
    private TextView monthYearTextView;
    private TextView selectedCountTextView;
    private RecyclerView daysRecyclerView;
    private CalendarDayAdapter dayAdapter;

    private final int heatmapBaseColor;
    private final float uiScale;
    private int maxNotesCountInMonth = 1;

    public NotesCalendarDialog(@NonNull Context context,
                               Map<String, Integer> notesCountPerDay,
                               Set<String> currentlySelectedDates,
                               OnCalendarFilterAppliedListener listener) {
        super(context);
        this.notesCountPerDay = notesCountPerDay;
        this.selectedDates = new HashSet<>(currentlySelectedDates);
        this.listener = listener;

        this.heatmapBaseColor = AppSettings.getCalendarHeatmapColor(context);
        this.uiScale = AppSettings.getCalendarScale(context);

        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notes_calendar);

        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // Starts at CURRENT month
        currentDisplayCalendar = Calendar.getInstance();
        currentDisplayCalendar.set(Calendar.DAY_OF_MONTH, 1);

        monthYearTextView = findViewById(R.id.calMonthYearTextView);
        selectedCountTextView = findViewById(R.id.calSelectedCountTextView);
        daysRecyclerView = findViewById(R.id.calDaysRecyclerView);

        Button prevBtn = findViewById(R.id.calPrevMonthButton);
        Button nextBtn = findViewById(R.id.calNextMonthButton);
        Button uncheckAllBtn = findViewById(R.id.calBtnUncheckAll);
        Button applyBtn = findViewById(R.id.calBtnApply);

        // Apply UI Scale to dialog elements
        monthYearTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 * uiScale);
        uncheckAllBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11 * uiScale);
        applyBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11 * uiScale);

        // Apply theme to dialog buttons
        ThemeHelper.applyColorToButton(applyBtn, AppSettings.getButtonColor(getContext()));

        // Month Navigation
        prevBtn.setOnClickListener(v -> {
            currentDisplayCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        nextBtn.setOnClickListener(v -> {
            currentDisplayCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        // Uncheck all days button
        uncheckAllBtn.setOnClickListener(v -> {
            selectedDates.clear();
            dayAdapter.notifyDataSetChanged();
            updateSelectedCountLabel();
        });

        // Apply filter button
        applyBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFilterApplied(new HashSet<>(selectedDates));
            }
            dismiss();
        });

        daysRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        updateCalendar();
    }

    private void updateCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        monthYearTextView.setText(sdf.format(currentDisplayCalendar.getTime()));

        // Calculate leading blanks (Monday-first: Mon=0 ... Sun=6)
        Calendar temp = (Calendar) currentDisplayCalendar.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK); // Sun=1, Mon=2
        int offset = (firstDayOfWeek == Calendar.SUNDAY) ? 6 : (firstDayOfWeek - Calendar.MONDAY);

        int daysInMonth = currentDisplayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Find max notes count in displayed month for proper gradient scaling
        int maxNotes = 1;
        int year = currentDisplayCalendar.get(Calendar.YEAR);
        int month = currentDisplayCalendar.get(Calendar.MONTH) + 1;
        for (int day = 1; day <= daysInMonth; day++) {
            String dateKey = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
            Integer count = notesCountPerDay.get(dateKey);
            if (count != null && count > maxNotes) {
                maxNotes = count;
            }
        }
        this.maxNotesCountInMonth = maxNotes;

        List<Integer> dayNumbers = new ArrayList<>();
        for (int i = 0; i < offset; i++) {
            dayNumbers.add(0); // empty cell placeholder
        }
        for (int day = 1; day <= daysInMonth; day++) {
            dayNumbers.add(day);
        }

        dayAdapter = new CalendarDayAdapter(dayNumbers);
        daysRecyclerView.setAdapter(dayAdapter);
        updateSelectedCountLabel();
    }

    private void updateSelectedCountLabel() {
        if (selectedDates.isEmpty()) {
            selectedCountTextView.setText("No days selected (Showing all notes)");
        } else {
            selectedCountTextView.setText("Selected: " + selectedDates.size() + " day(s)");
        }
    }

    // --- Grid Adapter for Days ---
    private class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayAdapter.DayViewHolder> {
        private final List<Integer> days;

        public CalendarDayAdapter(List<Integer> days) {
            this.days = days;
        }

        @NonNull
        @Override
        public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
            // Apply scale to cell height
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null) {
                float density = getContext().getResources().getDisplayMetrics().density;
                lp.height = (int) (44 * density * uiScale);
                view.setLayoutParams(lp);
            }
            return new DayViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
            int dayNumber = days.get(position);
            if (dayNumber == 0) {
                holder.root.setVisibility(View.INVISIBLE);
                holder.root.setOnClickListener(null);
                return;
            }

            holder.root.setVisibility(View.VISIBLE);
            holder.tvDayNumber.setText(String.valueOf(dayNumber));
            holder.tvDayNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13 * uiScale);

            Calendar dayCal = (Calendar) currentDisplayCalendar.clone();
            dayCal.set(Calendar.DAY_OF_MONTH, dayNumber);
            boolean isSunday = (dayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

            // Sunday number MUST be dark red (#8B0000)
            if (isSunday) {
                holder.tvDayNumber.setTextColor(Color.parseColor("#8B0000"));
                holder.tvDayNumber.setTypeface(null, Typeface.BOLD);
            } else {
                holder.tvDayNumber.setTextColor(Color.parseColor("#222222"));
                holder.tvDayNumber.setTypeface(null, Typeface.NORMAL);
            }

            // Date string yyyy-MM-dd
            int year = currentDisplayCalendar.get(Calendar.YEAR);
            int month = currentDisplayCalendar.get(Calendar.MONTH) + 1;
            String dateKey = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, dayNumber);

            // Note count heatmap calculation
            int noteCount = 0;
            if (notesCountPerDay.containsKey(dateKey)) {
                noteCount = notesCountPerDay.get(dateKey);
            }

            int backgroundColor;
            if (noteCount == 0) {
                // White if none
                backgroundColor = Color.WHITE;
            } else {
                // More intensive color as notes increase
                float intensity = 0.25f + 0.75f * ((float) noteCount / Math.max(1, maxNotesCountInMonth));
                backgroundColor = ColorUtils.blendARGB(Color.WHITE, heatmapBaseColor, Math.min(1.0f, intensity));
            }

            // Selection state
            boolean isSelected = selectedDates.contains(dateKey);
            GradientDrawable borderDrawable = new GradientDrawable();
            borderDrawable.setColor(backgroundColor);

            float density = getContext().getResources().getDisplayMetrics().density;
            if (isSelected) {
                borderDrawable.setStroke((int) (2.5f * density), Color.parseColor("#1565C0"));
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                borderDrawable.setStroke((int) (0.5f * density), Color.parseColor("#E0E0E0"));
                holder.ivCheck.setVisibility(View.GONE);
            }
            holder.root.setBackground(borderDrawable);

            // Toggle date selection on click
            holder.root.setOnClickListener(v -> {
                if (selectedDates.contains(dateKey)) {
                    selectedDates.remove(dateKey);
                } else {
                    selectedDates.add(dateKey);
                }
                notifyItemChanged(position);
                updateSelectedCountLabel();
            });
        }

        @Override
        public int getItemCount() {
            return days.size();
        }

        class DayViewHolder extends RecyclerView.ViewHolder {
            FrameLayout root;
            TextView tvDayNumber;
            TextView ivCheck;

            public DayViewHolder(@NonNull View itemView) {
                super(itemView);
                root = itemView.findViewById(R.id.calDayRoot);
                tvDayNumber = itemView.findViewById(R.id.calDayNumberTextView);
                ivCheck = itemView.findViewById(R.id.calDayCheckIndicator);
            }
        }
    }
}