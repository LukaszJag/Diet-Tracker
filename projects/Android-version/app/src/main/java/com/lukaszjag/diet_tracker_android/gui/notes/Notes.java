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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Notes extends AppCompatActivity {

    public enum PrimaryCategory {
        JAVA_ECOSYSTEM(1, "Java ecosystem", List.of("Java", "Spring", "JUNIT", "Android", "Java – Web Scraping", "Java Diet Tracker")),
        FRONTEND(2, "Frontend", List.of("HTML, CSS", "JavaScript")),
        CLOUD_AND_INFRASTRUCTURE(3, "Cloud and Infrastructure", List.of("Linux", "PowerShell", "Network", "Cloud", "Windows Server", "Windows", "Containers")),
        GAMEDEV(4, "GameDev", List.of("UE5")),
        COMPUTER_SCIENCE(5, "Computer Science", List.of("Algorytmy", "General IT Knowledge")),
        SOFT_SKILLS(6, "Soft Skills", List.of("Szukanie pracy", "Japoński", "Angielski")),
        VERSATILE_FRAMEWORKS(7, "Versatile Frameworks", List.of("Python", ".NET(C#)")),
        WSB(8, "WSB", List.of("WSB")),
        CERTS(9, "Certs", List.of("CompTIA A+", "CompTIA Network +", "CompTIA Linux +")),
        BACKEND(10, "Backend", List.of("x")),
        DATA_AND_BI(11, "Data and BI", List.of("Excel", "Word", "Power Point", "Power BI")),
        LOW_LEVEL_LANGUAGES(12, "Low-Level Languages", List.of("C", "C++")),
        DEV_TOOLS(13, "Dev Tools", List.of("Git", "Jira", "Postman", "IDE")),
        AI_MACHINE_LEARNING(14, "AI & Machine Learning", List.of("AI Assistants", "Machine Learning Basics"));

        private final int id;
        private final String displayName;
        private final List<String> subcategories;

        PrimaryCategory(int id, String displayName, List<String> subcategories) {
            this.id = id;
            this.displayName = displayName;
            this.subcategories = subcategories;
        }

        // --- THESE METHODS MUST BE HERE, AFTER THE SEMICOLON ---
        public int getId() { return id; }
        public String getDisplayName() { return displayName; }
        public List<String> getSubcategories() { return subcategories; }

        public static List<String> getSubcategoriesByDisplayName(String displayName) {
            return Arrays.stream(values())
                    .filter(category -> category.getDisplayName().equalsIgnoreCase(displayName))
                    .findFirst()
                    .map(PrimaryCategory::getSubcategories)
                    .orElse(List.of());
        }
    }
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private EditText filterSubtitle;
    private CheckBox filterCbLearning, filterCbGeneral, filterCbToday, filterCbToBuy;
    private TextView filterCategorySpinner;
    private Spinner filterUrgentlySpinner;
    private List<String> selectedCategories = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        filterSubtitle = findViewById(R.id.filterSubtitle);
        filterCategorySpinner = findViewById(R.id.filterCategorySpinner);
        filterUrgentlySpinner = findViewById(R.id.filterUrgentlySpinner);
        filterCbLearning = findViewById(R.id.filterCbLearning);
        filterCbGeneral = findViewById(R.id.filterCbGeneral);
        filterCbToday = findViewById(R.id.filterCbToday);
        filterCbToBuy = findViewById(R.id.filterCbToBuy);

        CompoundButton.OnCheckedChangeListener filterCheckWatcher = (buttonView, isChecked) -> applyFilters();
        filterCbLearning.setOnCheckedChangeListener(filterCheckWatcher);
        filterCbGeneral.setOnCheckedChangeListener(filterCheckWatcher);
        filterCbToday.setOnCheckedChangeListener(filterCheckWatcher);
        filterCbToBuy.setOnCheckedChangeListener(filterCheckWatcher);

        findViewById(R.id.button).setOnClickListener(v -> showAddNoteDialog());

        checkAndRequestPermissions();
        setupCategorySpinner();
        setupUrgencySpinner();
    }

    private void applyFilters() {
        String subtitleQuery = filterSubtitle.getText().toString();
        String urgencyQuery = filterUrgentlySpinner.getSelectedItem() != null ?
                filterUrgentlySpinner.getSelectedItem().toString() : "All Urgencies";

        adapter.filter(subtitleQuery, selectedCategories, urgencyQuery,
                filterCbLearning.isChecked(),
                filterCbGeneral.isChecked(),
                filterCbToday.isChecked(),
                filterCbToBuy.isChecked());
    }

    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final EditText dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        final CheckBox dialogCbLearning = dialogView.findViewById(R.id.dialogCbLearning);
        final CheckBox dialogCbGeneral = dialogView.findViewById(R.id.dialogCbGeneral);
        final CheckBox dialogCbToday = dialogView.findViewById(R.id.dialogCbToday);
        final CheckBox dialogCbToBuy = dialogView.findViewById(R.id.dialogCbToBuy);

        builder.setPositiveButton("Create", (dialog, which) -> {
            boolean isToBuy = dialogCbToBuy.isChecked();
            // ... [Rest of your existing creation logic using isToBuy]
            // Note newNote = new Note(..., isToBuy, ...);
            // adapter.addItem(newNote);
            NoteStorage.saveNotes(this, adapter.getOriginalList());
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    // ... [Rest of your existing methods: setupCategorySpinner, setupUrgencySpinner, checkAndRequestPermissions, etc.]
    private void setupCategorySpinner() { /* Existing implementation */ }
    private void setupUrgencySpinner() { /* Existing implementation */ }
    private void checkAndRequestPermissions() { /* Existing implementation */ }
}