package com.lukaszjag.diet_tracker_android.gui.notes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private static final int STORAGE_PERMISSION_CODE = 101;

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private Button btnAddNote;
    private EditText filterSubtitle;
    private CheckBox filterCbLearning, filterCbGeneral, filterCbToday;

    private CheckBox hideCbSection1, hideCbSection2, hideCbSection3, hideCbSection4;

    private Spinner sortSpinner;

    // Filter controls for Categories and Urgency
    private TextView filterCategorySpinner;
    private Spinner filterUrgentlySpinner;

    // Multichoice State Fields
    private List<String> uniqueCategoriesList = new ArrayList<>();
    private boolean[] checkedCategories;
    private List<String> selectedCategories = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // Swipe-to-Delete Configuration
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    adapter.deleteItem(position);
                    NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
                    setupCategorySpinner(); // Refreshes categories in case deleted note was the only one in its category
                }
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return 0.3f;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        filterSubtitle = findViewById(R.id.filterSubtitle);
        filterCategorySpinner = findViewById(R.id.filterCategorySpinner);
        filterUrgentlySpinner = findViewById(R.id.filterUrgentlySpinner);

        filterCbLearning = findViewById(R.id.filterCbLearning);
        filterCbGeneral = findViewById(R.id.filterCbGeneral);
        filterCbToday = findViewById(R.id.filterCbToday);

        hideCbSection1 = findViewById(R.id.hideCbSection1);
        hideCbSection2 = findViewById(R.id.hideCbSection2);
        hideCbSection3 = findViewById(R.id.hideCbSection3);
        hideCbSection4 = findViewById(R.id.hideCbSection4);

        sortSpinner = findViewById(R.id.sortSpinner);

        // Sorting Spinner Configuration
        List<String> sortOptions = new ArrayList<>();
        sortOptions.add("None");
        sortOptions.add("Date Created (Newest First)");
        sortOptions.add("Date Created (Oldest First)");
        sortOptions.add("Urgency (High to Low)");
        sortOptions.add("Urgency (Low to High)");

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int criteria = MyAdapter.SORT_NONE;
                switch (position) {
                    case 1:
                        criteria = MyAdapter.SORT_DATE_NEWEST;
                        break;
                    case 2:
                        criteria = MyAdapter.SORT_DATE_OLDEST;
                        break;
                    case 3:
                        criteria = MyAdapter.SORT_URGENCY_HIGH;
                        break;
                    case 4:
                        criteria = MyAdapter.SORT_URGENCY_LOW;
                        break;
                }
                adapter.setSortCriteria(criteria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Subtitle text filter
        filterSubtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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

        // Check/Request storage permissions before initializing load
        checkAndRequestPermissions();

        // Initialize custom selection spinners
        setupCategorySpinner();
        setupUrgencySpinner();
    }

    /**
     * Checks if the app has permission to manage external files on Android 11+
     * or standard read/write permissions on Android 6.0 - 10.
     */
    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 (API 30) and above
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            } else {
                loadNotesOnStartup();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 (API 23) to Android 10 (API 29)
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, STORAGE_PERMISSION_CODE);
            } else {
                loadNotesOnStartup();
            }
        } else {
            loadNotesOnStartup();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                loadNotesOnStartup();
                setupCategorySpinner();
            } else {
                Toast.makeText(this, "Storage permission is required to access your notes from the public directory.", Toast.LENGTH_LONG).show();
                loadNotesOnStartup(); // Fallback load attempt
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Automatically checks if permission was granted when returning from settings screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager() && adapter.getOriginalList().isEmpty()) {
                loadNotesOnStartup();
                setupCategorySpinner();
            }
        }
    }

    /**
     * Resets the adapter to clean lists to avoid duplicated items on reload,
     * then reads the JSON storage file and populates the UI.
     */
    private void loadNotesOnStartup() {
        List<Note> loadedNotes = NoteStorage.loadNotes(this);

        // Re-initialize adapter to avoid duplicate items on re-execution (e.g., inside onResume)
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position, note) -> {
            showEditNoteDialog(position, note);
        });

        if (loadedNotes.isEmpty()) {
            System.out.println("loadedNotes.isEmpty");
            populateDummyData();
        } else {
            System.out.println("NOT loadedNotes.isEmpty");
            System.out.println("loadedNotes.size(): " + loadedNotes.size());
            for (Note note : loadedNotes) {
                System.out.println(note.getNoteTitle());
                adapter.addItem(note);
            }
        }
    }

    private void applyFilters() {
        String subtitleQuery = filterSubtitle.getText().toString();

        // Obtains selected Urgency from spinner selection
        String urgencyQuery = filterUrgentlySpinner.getSelectedItem() != null ?
                filterUrgentlySpinner.getSelectedItem().toString() : "All Urgencies";

        boolean showLearningOnly = filterCbLearning.isChecked();
        boolean showGeneralOnly = filterCbGeneral.isChecked();
        boolean showTodayOnly = filterCbToday.isChecked();

        adapter.filter(subtitleQuery, selectedCategories, urgencyQuery,
                showLearningOnly, showGeneralOnly, showTodayOnly);
    }

    private void updateSectionVisibilities() {
        boolean hideS1 = hideCbSection1.isChecked();
        boolean hideS2 = hideCbSection2.isChecked();
        boolean hideS3 = hideCbSection3.isChecked();
        boolean hideS4 = hideCbSection4.isChecked();
        adapter.setSectionVisibilities(hideS1, hideS2, hideS3, hideS4);
    }

    // Gathers unique categories dynamically, maintaining checks on pre-existing filters
    private void setupCategorySpinner() {
        List<String> previouslyChecked = new ArrayList<>(selectedCategories);

        uniqueCategoriesList.clear();
        for (Note note : adapter.getOriginalList()) {
            String cat = note.getNoteCategory();
            if (cat != null && !cat.trim().isEmpty() && !uniqueCategoriesList.contains(cat.trim())) {
                uniqueCategoriesList.add(cat.trim());
            }
        }

        // Hardcoded defaults to ensure categories list is never empty
        String[] defaults = {"Security", "Organize", "Financial", "General"};
        for (String d : defaults) {
            if (!uniqueCategoriesList.contains(d)) {
                uniqueCategoriesList.add(d);
            }
        }

        checkedCategories = new boolean[uniqueCategoriesList.size()];
        selectedCategories.clear();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < uniqueCategoriesList.size(); i++) {
            String currentCat = uniqueCategoriesList.get(i);
            if (previouslyChecked.contains(currentCat)) {
                checkedCategories[i] = true;
                selectedCategories.add(currentCat);
                if (sb.length() > 0) sb.append(", ");
                sb.append(currentCat);
            }
        }

        if (selectedCategories.isEmpty()) {
            filterCategorySpinner.setText("All Categories");
        } else {
            filterCategorySpinner.setText(sb.toString());
        }

        filterCategorySpinner.setOnClickListener(v -> showCategorySelectionDialog());
    }

    private void showCategorySelectionDialog() {
        String[] items = uniqueCategoriesList.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Categories");
        builder.setMultiChoiceItems(items, checkedCategories, (dialog, which, isChecked) -> {
            checkedCategories[which] = isChecked;
        });
        builder.setPositiveButton("OK", (dialog, which) -> {
            selectedCategories.clear();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < checkedCategories.length; i++) {
                if (checkedCategories[i]) {
                    selectedCategories.add(uniqueCategoriesList.get(i));
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(uniqueCategoriesList.get(i));
                }
            }
            if (selectedCategories.isEmpty()) {
                filterCategorySpinner.setText("All Categories");
            } else {
                filterCategorySpinner.setText(sb.toString());
            }
            applyFilters();
        });
        builder.setNegativeButton("Cancel", null);
        builder.setNeutralButton("Clear All", (dialog, which) -> {
            for (int i = 0; i < checkedCategories.length; i++) {
                checkedCategories[i] = false;
            }
            selectedCategories.clear();
            filterCategorySpinner.setText("All Categories");
            applyFilters();
        });
        builder.create().show();
    }

    private void setupUrgencySpinner() {
        Note sampleNote = new Note();
        List<String> urgencyOptions = new ArrayList<>();
        urgencyOptions.add("All Urgencies");

        for (String u : sampleNote.getUrgentScaleEnglish()) {
            if (!urgencyOptions.contains(u)) urgencyOptions.add(u);
        }
        for (String u : sampleNote.getUrgentScalePolish()) {
            if (!urgencyOptions.contains(u)) urgencyOptions.add(u);
        }

        ArrayAdapter<String> urgencyFilterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, urgencyOptions);
        urgencyFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterUrgentlySpinner.setAdapter(urgencyFilterAdapter);

        filterUrgentlySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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

            String dateCreated = note.getDateCreated();

            Note updatedNote = new Note(title, subtitle, description, category, urgent,
                    isLearning, isGeneral, isToday, selectedLearningCat, dateCreated, deadline);

            adapter.setItem(position, updatedNote);
            NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
            setupCategorySpinner(); // Rebuilds the categories filter list dynamically on update
        });

        builder.setNeutralButton("Delete", (dialog, which) -> {
            adapter.deleteItem(position);
            NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
            setupCategorySpinner();
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
            setupCategorySpinner(); // Rebuilds the category multichoice options dynamically for any newly added category
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void populateDummyData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        adapter.addItem(new Note("Test task", "-1", "empty", "empty", "no urgent scale", false, false, false, null, "", ""));

        NoteStorage.saveNotes(this, adapter.getOriginalList());
    }
}   