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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

public class Notes extends AppCompatActivity {

    //<editor-fold desc="Global Variables">
    private MyAdapter adapter;

    //<editor-fold desc="UI components">
    private RecyclerView recyclerView;
    private Button btnAddNote;
    private EditText filterSubtitle, filterCategory, filterUrgently;
    private CheckBox filterCbLearning, filterCbGeneral, filterCbToday;

    //<editor-fold desc="Note display UI components">
    EditText dialogTitle;
    EditText dialogSubtitle;
    EditText dialogDescription;
    EditText dialogCategory;
    Spinner dialogSpinnerUrgent;
    CheckBox dialogCbLearning;
    CheckBox dialogCbGeneral;
    CheckBox dialogCbToday;
    TextView tvLearningCatLabel;
    Spinner dialogSpinnerLearningCat;
    EditText dialogDeadline;
    //</editor-fold>

    //</editor-fold>
    //</editor-fold>

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        setupUIComponents();
        addNotesOnStart();
    }


    private void setupUIComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // --- SWIPE TO DELETE INTEGRATION ---
        androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(0, androidx.recyclerview.widget.ItemTouchHelper.LEFT | androidx.recyclerview.widget.ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false; // No drag-and-drop support needed
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getBindingAdapterPosition();

                        new AlertDialog.Builder(Notes.this)
                                .setTitle("Delete Task")
                                .setMessage("Are you sure you want to delete this task?")
                                .setPositiveButton("Delete", (dialog, which) -> {
                                    // Pass the Context (Notes.this) to save the updated list to JSON
                                    adapter.deleteItem(position, Notes.this);
                                })
                                .setNegativeButton("Cancel", (dialog, which) -> {
                                    // If the user cancels, restore the item to its original state
                                    adapter.notifyItemChanged(position);
                                })
                                .setCancelable(false)
                                .show();
                    }
                };

        new androidx.recyclerview.widget.ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        // -------------------------------------


// --- ADD THE ON-ITEM-CLICK LISTENER FOR EDITING ---
        adapter.setOnItemClickListener((position, note) -> {
            showEditNoteDialog(position, note);
        });
// --------------------------------------------------

        filterSubtitle = findViewById(R.id.filterSubtitle);
// ... (the rest of setupUIComponents remains identical)

        filterSubtitle = findViewById(R.id.filterSubtitle);
        filterCategory = findViewById(R.id.filterCategory);
        filterUrgently = findViewById(R.id.filterUrgently);

        filterCbLearning = findViewById(R.id.filterCbLearning);
        filterCbGeneral = findViewById(R.id.filterCbGeneral);
        filterCbToday = findViewById(R.id.filterCbToday);

        TextWatcher filterTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
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

        dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogSubtitle = dialogView.findViewById(R.id.dialogSubtitle);
        dialogDescription = dialogView.findViewById(R.id.dialogDescription);
        dialogCategory = dialogView.findViewById(R.id.dialogCategory);
        dialogSpinnerUrgent = dialogView.findViewById(R.id.dialogSpinnerUrgent);
        dialogCbLearning = dialogView.findViewById(R.id.dialogCbLearning);
        dialogCbGeneral = dialogView.findViewById(R.id.dialogCbGeneral);
        dialogCbToday = dialogView.findViewById(R.id.dialogCbToday);
        tvLearningCatLabel = dialogView.findViewById(R.id.tvLearningCatLabel);
        dialogSpinnerLearningCat = dialogView.findViewById(R.id.dialogSpinnerLearningCat);
        dialogDeadline = dialogView.findViewById(R.id.dialogDeadline);

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

    private void addNotesOnStartOld() {
        // 1. Define the physical path to your file on disk
        java.io.File physicalFile = new java.io.File(
                android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS),
                "diet-tracker-data/notes_data.json"
        );

        // 2. Try to load the notes using MediaStore
        List<Note> loadedNotes = NoteStorage.loadNotes(this);

        if (loadedNotes.isEmpty()) {
            // 3. Check if the file physically exists on disk
            if (physicalFile.exists()) {
                // The file is physically there. We shouldn't overwrite it with dummy data.
                // This indicates either a MediaStore indexing lag or a syntax error in your JSON.
                System.out.println("DEBUG: The file physically exists, but could not be parsed or indexed. Skipping dummy data to protect your edits.");
                android.widget.Toast.makeText(this, "Notes file detected but couldn't be loaded (Check JSON format or restart device)", android.widget.Toast.LENGTH_LONG).show();
            } else {
                // The file truly does not exist anywhere, safe to create dummy data.
                populateDummyData();
            }
        } else {
            // Load the notes normally
            int counter = 0;
            for (Note note : loadedNotes) {
                System.out.println(counter + " -> " + note.getNoteTitle());
                adapter.addItem(note);
                counter++;
            }
        }
    }

    private void populateDummyData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        adapter.addItem(new Note("BitWarden Config", "Skonfigurować BitWardena", "Założyć konto, ustawić hasło, dodać kilka haseł", "Security", "High-Stakes Campaign", false, true, false, null, today, ""));
        adapter.addItem(new Note("Bookshelf Sort", "Dodać kategorie do książek na półce", "Przeniesienie bazy do serwisu lubimyczytać.pl", "Organize", "no urgent scale", false, false, false, null, today, ""));
        adapter.addItem(new Note("Savings Goal", "Wyznaczyć kwotę do odłożenia na monitor", "Znaleźć konkretny model do kupienia i odłożyć budżet", "Financial", "Strategic Initiative", false, true, true, null, today, "2026-12-31"));

        NoteStorage.saveNotes(this, adapter.getOriginalList());
    }

    private void addNotesOnStart() {
        List<Note> loadedNotes = NoteStorage.loadNotes(this);

        if (loadedNotes.isEmpty()) {
            populateDummyData();
        } else {
            int counter = 0;
            for (Note note : loadedNotes) {
                System.out.println(counter + " -> " + note.getNoteTitle());
                adapter.addItem(note);
                counter++;
            }
        }
    }

    // Notes.java

    private void showEditNoteDialog(int position, Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogSubtitle = dialogView.findViewById(R.id.dialogSubtitle);
        dialogDescription = dialogView.findViewById(R.id.dialogDescription);
        dialogCategory = dialogView.findViewById(R.id.dialogCategory);
        dialogSpinnerUrgent = dialogView.findViewById(R.id.dialogSpinnerUrgent);
        dialogCbLearning = dialogView.findViewById(R.id.dialogCbLearning);
        dialogCbGeneral = dialogView.findViewById(R.id.dialogCbGeneral);
        dialogCbToday = dialogView.findViewById(R.id.dialogCbToday);
        tvLearningCatLabel = dialogView.findViewById(R.id.tvLearningCatLabel);
        dialogSpinnerLearningCat = dialogView.findViewById(R.id.dialogSpinnerLearningCat);
        dialogDeadline = dialogView.findViewById(R.id.dialogDeadline);

        // 1. Pre-populate the dialog text fields
        dialogTitle.setText(note.getNoteTitle());
        dialogSubtitle.setText(note.getNoteSubtitle());
        dialogDescription.setText(note.getNoteDescription());
        dialogCategory.setText(note.getNoteCategory());
        dialogDeadline.setText(note.getDateDeadline());

        // 2. Pre-populate checkboxes
        dialogCbLearning.setChecked(note.isLearning());
        dialogCbGeneral.setChecked(note.isGeneralToDo());
        dialogCbToday.setChecked(note.isIisTodayTask());

        // 3. Set the visibility status for the learning spinner
        int initialVisibility = note.isLearning() ? View.VISIBLE : View.GONE;
        tvLearningCatLabel.setVisibility(initialVisibility);
        dialogSpinnerLearningCat.setVisibility(initialVisibility);

        Note sampleNote = new Note();
        ArrayAdapter<String> urgentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sampleNote.getUrgentScaleEnglish());
        urgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinnerUrgent.setAdapter(urgentAdapter);

        // 4. Set the selection for the urgent spinner
        int urgentPos = sampleNote.getUrgentScaleEnglish().indexOf(note.getNoteUrgently());
        if (urgentPos != -1) {
            dialogSpinnerUrgent.setSelection(urgentPos);
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

        // 5. Pre-select the learning category spinner if applicable
        if (note.isLearning() && note.getNoteLearningCategories() != null) {
            for (int i = 0; i < learningCatOptions.size(); i++) {
                if (learningCatOptions.get(i).getCategoryName().equals(note.getNoteLearningCategories().getCategoryName())) {
                    dialogSpinnerLearningCat.setSelection(i);
                    break;
                }
            }
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

            // Keep the original creation date unchanged
            String dateCreated = note.getDateCreated();

            Note updatedNote = new Note(title, subtitle, description, category, urgent,
                    isLearning, isGeneral, isToday, selectedLearningCat, dateCreated, deadline);

            // Save updated data to adapter and storage file
            adapter.setItem(position, updatedNote, Notes.this);
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}