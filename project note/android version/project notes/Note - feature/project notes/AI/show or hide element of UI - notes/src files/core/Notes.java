package com.lukaszjag.diet_tracker_android.gui.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.MyAdapter;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.Note;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.NoteStorage;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories.LearningCategories;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Notes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private Button btnAddNote;
    private EditText filterSubtitle, filterCategory, filterUrgently;
    private CheckBox filterCbLearning, filterCbGeneral, filterCbToday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        filterSubtitle = findViewById(R.id.filterSubtitle);
        filterCategory = findViewById(R.id.filterCategory);
        filterUrgently = findViewById(R.id.filterUrgently);

        filterCbLearning = findViewById(R.id.filterCbLearning);
        filterCbGeneral = findViewById(R.id.filterCbGeneral);
        filterCbToday = findViewById(R.id.filterCbToday);

        TextWatcher filterTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        filterSubtitle.addTextChangedListener(filterTextWatcher);
        filterCategory.addTextChangedListener(filterTextWatcher);
        filterUrgently.addTextChangedListener(filterTextWatcher);

        CompoundButton.OnCheckedChangeListener filterCheckWatcher = (buttonView, isChecked) -> applyFilters();
        filterCbLearning.setOnCheckedChangeListener(filterCheckWatcher);
        filterCbGeneral.setOnCheckedChangeListener(filterCheckWatcher);
        filterCbToday.setOnCheckedChangeListener(filterCheckWatcher);

        btnAddNote = findViewById(R.id.button);
        btnAddNote.setText("Add Note");
        btnAddNote.setOnClickListener(v -> showAddNoteDialog());

        List<Note> loadedNotes = NoteStorage.loadNotes(this);
        if (loadedNotes.isEmpty()) {
            populateDummyData();
        } else {
            for (Note note : loadedNotes) {
                adapter.addItem(note);
            }
        }
    }

    private void applyFilters() {
        String subtitleQuery = filterSubtitle.getText().toString();
        String categoryQuery = filterCategory.getText().toString();
        String urgentlyQuery = filterUrgently.getText().toString();

        boolean showLearningOnly = filterCbLearning.isChecked();
        boolean showGeneralOnly = filterCbGeneral.isChecked();
        boolean showTodayOnly = filterCbToday.isChecked();

        adapter.filter(subtitleQuery, categoryQuery, urgentlyQuery,
                showLearningOnly, showGeneralOnly, showTodayOnly);
    }

    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final EditText dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        final EditText dialogSubtitle = dialogView.findViewById(R.id.dialogSubtitle);
        final EditText dialogDescription = dialogView.findViewById(R.id.dialogDescription);
        final EditText dialogCategory = dialogView.findViewById(R.id.dialogCategory);
        final Spinner dialogSpinnerUrgent = dialogView.findViewById(R.id.dialogSpinnerUrgent);
        final CheckBox dialogCbLearning = dialogView.findViewById(R.id.dialogCbLearning);
        final CheckBox dialogCbGeneral = dialogView.findViewById(R.id.dialogCbGeneral);
        final CheckBox dialogCbToday = dialogView.findViewById(R.id.dialogCbToday);
        final TextView tvLearningCatLabel = dialogView.findViewById(R.id.tvLearningCatLabel);
        final Spinner dialogSpinnerLearningCat = dialogView.findViewById(R.id.dialogSpinnerLearningCat);
        final EditText dialogDeadline = dialogView.findViewById(R.id.dialogDeadline);

        Note sampleNote = new Note();
        ArrayAdapter<String> urgentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sampleNote.getUrgentScaleEnglish());
        urgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinnerUrgent.setAdapter(urgentAdapter);

        List<LearningCategories> learningCatOptions = new ArrayList<>();
        learningCatOptions.add(new LearningCategories("Software Engineering (Java)", true, false));
        learningCatOptions.add(new LearningCategories("Android Core Frameworks", true, false));
        learningCatOptions.add(new LearningCategories("Database Systems Concepts", false, true));
        learningCatOptions.add(new LearningCategories("Artificial Intelligence Basics", false, true));

        ArrayAdapter<LearningCategories> learnCatAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, learningCatOptions);
        learnCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinnerLearningCat.setAdapter(learnCatAdapter);

        dialogCbLearning.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;
            tvLearningCatLabel.setVisibility(visibility);
            dialogSpinnerLearningCat.setVisibility(visibility);
        });

        dialogDeadline.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Notes.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                String dateStr = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                dialogDeadline.setText(dateStr);
            }, year, month, day);
            datePickerDialog.show();
        });

        builder.setPositiveButton("Create", (dialog, which) -> {
            String title = dialogTitle.getText().toString();
            String subtitle = dialogSubtitle.getText().toString();
            String description = dialogDescription.getText().toString();
            String category = dialogCategory.getText().toString();
            String urgent = dialogSpinnerUrgent.getSelectedItem().toString();

            boolean isLearning = dialogCbLearning.isChecked();
            boolean isGeneral = dialogCbGeneral.isChecked();
            boolean isToday = dialogCbToday.isChecked();

            LearningCategories selectedLearningCat = isLearning ? (LearningCategories) dialogSpinnerLearningCat.getSelectedItem() : null;
            String deadline = dialogDeadline.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateCreated = sdf.format(new Date());

            Note newNote = new Note(title, subtitle, description, category, urgent,
                    isLearning, isGeneral, isToday, selectedLearningCat, dateCreated, deadline);

            adapter.addItem(newNote);

            NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void populateDummyData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        adapter.addItem(new Note("BitWarden Config", "Skonfigurować BitWardena", "Założyć konto, ustawić hasło, dodać kilka haseł", "Security", "High-Stakes Campaign", false, true, false, null, today, ""));
        adapter.addItem(new Note("Bookshelf Sort", "Dodać kategorie do książek na półce", "Przeniesienie bazy do serwisu lubimyczytać.pl", "Organize", "no urgent scale", false, false, false, null, today, ""));
        adapter.addItem(new Note("Savings Goal", "Wyznaczyć kwotę do odłożenia na monitor", "Znaleźć konkretny model do kupienia i odłożyć budżet", "Financial", "Strategic Initiative", false, true, true, null, today, "2026-12-31"));

        NoteStorage.saveNotes(this, adapter.getOriginalList());
    }
}