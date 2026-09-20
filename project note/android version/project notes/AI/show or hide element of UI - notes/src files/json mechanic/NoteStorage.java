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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper; // Added import
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

    // Checkboxes used to hide section displays
    private CheckBox hideCbSection1, hideCbSection2, hideCbSection3, hideCbSection4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // Bind clicks on individual notes to the edit flow
        adapter.setOnItemClickListener((position, note) -> {
            showEditNoteDialog(position, note);
        });

        // Setup Swipe-to-Delete functionality (Left Swiping only)
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No drag-and-drop actions needed
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Removes the note from adapter's active and original list structures
                    adapter.deleteItem(position);
                    // Persists the changes directly to the JSON storage
                    NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        filterSubtitle = findViewById(R.id.filterSubtitle);
        filterCategory = findViewById(R.id.filterCategory);
        filterUrgently = findViewById(R.id.filterUrgently);

        filterCbLearning = findViewById(R.id.filterCbLearning);
        filterCbGeneral = findViewById(R.id.filterCbGeneral);
        filterCbToday = findViewById(R.id.filterCbToday);

        hideCbSection1 = findViewById(R.id.hideCbSection1);
        hideCbSection2 = findViewById(R.id.hideCbSection2);
        hideCbSection3 = findViewById(R.id.hideCbSection3);
        hideCbSection4 = findViewById(R.id.hideCbSection4);

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

        CompoundButton.OnCheckedChangeListener hideCheckWatcher = (buttonView, isChecked) -> updateSectionVisibilities();
        hideCbSection1.setOnCheckedChangeListener(hideCheckWatcher);
        hideCbSection2.setOnCheckedChangeListener(hideCheckWatcher);
        hideCbSection3.setOnCheckedChangeListener(hideCheckWatcher);
        hideCbSection4.setOnCheckedChangeListener(hideCheckWatcher);

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

    private void updateSectionVisibilities() {
        boolean hideS1 = hideCbSection1.isChecked();
        boolean hideS2 = hideCbSection2.isChecked();
        boolean hideS3 = hideCbSection3.isChecked();
        boolean hideS4 = hideCbSection4.isChecked();
        adapter.setSectionVisibilities(hideS1, hideS2, hideS3, hideS4);
    }

    private void showEditNoteDialog(int position, Note note) {
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

        // Pre-fill existing fields
        dialogTitle.setText(note.getNoteTitle());
        dialogSubtitle.setText(note.getNoteSubtitle());
        dialogDescription.setText(note.getNoteDescription());
        dialogCategory.setText(note.getNoteCategory());
        dialogDeadline.setText(note.getDateDeadline());

        dialogCbLearning.setChecked(note.isLearning());
        dialogCbGeneral.setChecked(note.isGeneralToDo());
        dialogCbToday.setChecked(note.isIisTodayTask());

        ArrayAdapter<String> urgentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, note.getUrgentScaleEnglish());
        urgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinnerUrgent.setAdapter(urgentAdapter);

        int urgentIndex = note.getUrgentScaleEnglish().indexOf(note.getNoteUrgently());
        if (urgentIndex != -1) {
            dialogSpinnerUrgent.setSelection(urgentIndex);
        }

        List<LearningCategories> learningCatOptions = new ArrayList<>();
        learningCatOptions.add(new LearningCategories("Software Engineering (Java)", true, false));
        learningCatOptions.add(new LearningCategories("Android Core Frameworks", true, false));
        learningCatOptions.add(new LearningCategories("Database Systems Concepts", false, true));
        learningCatOptions.add(new LearningCategories("Artificial Intelligence Basics", false, true));

        ArrayAdapter<LearningCategories> learnCatAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, learningCatOptions);
        learnCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinnerLearningCat.setAdapter(learnCatAdapter);

        // Setup visibility depending on stored checkbox state
        if (note.isLearning()) {
            tvLearningCatLabel.setVisibility(View.VISIBLE);
            dialogSpinnerLearningCat.setVisibility(View.VISIBLE);
            if (note.getNoteLearningCategories() != null) {
                for (int i = 0; i < learningCatOptions.size(); i++) {
                    if (learningCatOptions.get(i).getCategoryName().equals(note.getNoteLearningCategories().getCategoryName())) {
                        dialogSpinnerLearningCat.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            tvLearningCatLabel.setVisibility(View.GONE);
            dialogSpinnerLearningCat.setVisibility(View.GONE);
        }

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

        builder.setPositiveButton("Save", (dialog, which) -> {
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

            // Maintain the note's original creation date
            String dateCreated = note.getDateCreated();

            Note updatedNote = new Note(title, subtitle, description, category, urgent,
                    isLearning, isGeneral, isToday, selectedLearningCat, dateCreated, deadline);

            adapter.setItem(position, updatedNote);
            NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
        });

        builder.setNeutralButton("Delete", (dialog, which) -> {
            adapter.deleteItem(position);
            NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
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