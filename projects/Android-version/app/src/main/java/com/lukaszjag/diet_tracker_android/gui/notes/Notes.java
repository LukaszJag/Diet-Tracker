package com.lukaszjag.diet_tracker_android.gui.notes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.gui.notes.calendar.NotesCalendarDialog;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.MyAdapter;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.Note;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.NoteCategoryData;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class Notes extends AppCompatActivity {
    private EditText filterSubtitle;
    private Spinner filterCategorySpinner;
    private Spinner filterUrgentlySpinner;
    private CheckBox filterCbLearning, filterCbGeneral, filterCbToday, filterCbToBuy;
    private Button btnCalendarFilter;
    private Button btnClearDateFilter;
    private TextView tvNotesCount; // Displayed notes count
    private RecyclerView recyclerView;

    private Button pickDateDeadlineButton;

    private EditText dialogDeadlineEditTextLocal;

    private MyAdapter adapter;

    // Set of selected creation dates (yyyy-MM-dd)
    private final Set<String> selectedDates = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        filterSubtitle = findViewById(R.id.filterSubtitle);
        filterCategorySpinner = findViewById(R.id.filterCategorySpinner);
        filterUrgentlySpinner = findViewById(R.id.filterUrgentlySpinner);
        filterCbLearning = findViewById(R.id.filterCbLearning);
        filterCbGeneral = findViewById(R.id.filterCbGeneral);
        filterCbToday = findViewById(R.id.filterCbToday);
        filterCbToBuy = findViewById(R.id.filterCbToBuy);

        btnCalendarFilter = findViewById(R.id.btnCalendarFilter);
        btnClearDateFilter = findViewById(R.id.btnClearDateFilter);
        tvNotesCount = findViewById(R.id.tvNotesCount);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(v -> showNoteDialog(null));
        adapter.setOnItemClickListener((position, note) -> showNoteDialog(note));

        setupCalendarFilterButton();
        setupVisibilityListeners();
        setupFilterSpinners();
        setupFilterListeners();
        fetchNotesFromAzure();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.applyButtonTheme(this);
    }

    private void setupCalendarFilterButton() {
        btnCalendarFilter.setOnClickListener(v -> showCalendarDialog());

        btnClearDateFilter.setOnClickListener(v -> {
            selectedDates.clear();
            updateDateFilterButtonUI();
            applyFilters();
        });
    }

    private void showCalendarDialog() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Note note : adapter.getOriginalList()) {
            String created = note.getDateCreated();
            if (created != null && created.length() >= 10) {
                created = created.substring(0, 10);
                countMap.put(created, countMap.getOrDefault(created, 0) + 1);
            }
        }

        NotesCalendarDialog dialog = new NotesCalendarDialog(this, countMap, selectedDates, newSelectedDates -> {
            selectedDates.clear();
            selectedDates.addAll(newSelectedDates);
            updateDateFilterButtonUI();
            applyFilters();
        });
        dialog.show();
    }

    private void updateDateFilterButtonUI() {
        if (selectedDates.isEmpty()) {
            btnCalendarFilter.setText("📅 Choose Dates (All)");
            btnClearDateFilter.setVisibility(View.GONE);
        } else {
            btnCalendarFilter.setText("📅 Dates: " + selectedDates.size() + " selected");
            btnClearDateFilter.setVisibility(View.VISIBLE);
        }
    }

    private void setupFilterSpinners() {
        List<String> mainCategoriesFilter = new ArrayList<>();
        mainCategoriesFilter.add("All Categories");
        mainCategoriesFilter.addAll(NoteCategoryData.getMainCategories());

        ArrayAdapter<String> mainCatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mainCategoriesFilter);
        mainCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCategorySpinner.setAdapter(mainCatAdapter);

        updateSubcategoryFilterSpinner("All Categories");
    }

    private void updateSubcategoryFilterSpinner(String mainCategory) {
        List<String> subcategoriesFilter = new ArrayList<>();
        subcategoriesFilter.add("All Subcategories");

        if (mainCategory != null && !mainCategory.equals("All Categories")) {
            List<String> subs = NoteCategoryData.getSubcategories(mainCategory);
            if (subs != null && !subs.isEmpty()) {
                subcategoriesFilter.addAll(subs);
            }
        }

        ArrayAdapter<String> subcatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategoriesFilter);
        subcatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterUrgentlySpinner.setAdapter(subcatAdapter);
    }

    private void fetchNotesFromAzure() {
        String query = QueryMaker.getAllNotesQuery();
        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                runOnUiThread(() -> {
                    adapter.clearAll();
                    for (RowInTable row : resultTable) {
                        String subcat = row.getValue("subcategory");
                        if (subcat == null) subcat = "";

                        boolean isDone = "true".equalsIgnoreCase(row.getValue("is_done")) || "1".equals(row.getValue("is_done"));
                        boolean isDeleted = "true".equalsIgnoreCase(row.getValue("is_deleted")) || "1".equals(row.getValue("is_deleted"));

                        Note note = new Note(
                                Long.parseLong(row.getValue("id")),
                                row.getValue("title"),
                                row.getValue("subtitle"),
                                row.getValue("description"),
                                row.getValue("category"),
                                subcat,
                                row.getValue("is_urgently"),
                                "true".equals(row.getValue("is_learning")) || "1".equals(row.getValue("is_learning")),
                                "true".equals(row.getValue("is_general_to_do")) || "1".equals(row.getValue("is_general_to_do")),
                                "true".equals(row.getValue("is_today_task")) || "1".equals(row.getValue("is_today_task")),
                                "true".equals(row.getValue("is_to_buy_task")) || "1".equals(row.getValue("is_to_buy_task")),
                                isDone,
                                isDeleted,
                                null,
                                row.getValue("date_created"),
                                row.getValue("date_deadline")
                        );
                        adapter.addItem(note);
                    }
                    applyFilters();
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> Toast.makeText(Notes.this, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showNoteDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final EditText title = dialogView.findViewById(R.id.dialogTitle);
        final EditText subtitle = dialogView.findViewById(R.id.dialogSubtitle);
        final EditText desc = dialogView.findViewById(R.id.dialogDescription);
        final Spinner spinnerCategory = dialogView.findViewById(R.id.dialogSpinnerCategory);
        final Spinner spinnerSubcategory = dialogView.findViewById(R.id.dialogSpinnerSubcategory);
        final CheckBox cbLearn = dialogView.findViewById(R.id.dialogCbLearning);
        final CheckBox cbGen = dialogView.findViewById(R.id.dialogCbGeneral);
        final CheckBox cbToday = dialogView.findViewById(R.id.dialogCbToday);
        final CheckBox cbBuy = dialogView.findViewById(R.id.dialogCbToBuy);

        dialogDeadlineEditTextLocal = dialogView.findViewById(R.id.dialogDeadline);
        pickDateDeadlineButton = dialogView.findViewById(R.id.pickDaedlineButton);

        final LinearLayout actionButtonsLayout = dialogView.findViewById(R.id.dialogActionButtonsLayout);
        final Button btnMarkAsDone = dialogView.findViewById(R.id.dialogBtnMarkAsDone);
        final Button btnDelete = dialogView.findViewById(R.id.dialogBtnDelete);

        boolean isEditMode = (note != null);

        List<String> mainCategories = NoteCategoryData.getMainCategories();
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mainCategories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenMain = mainCategories.get(position);
                List<String> subcategories = NoteCategoryData.getSubcategories(chosenMain);
                List<String> displaySubs = new ArrayList<>();
                if (subcategories.isEmpty()) {
                    displaySubs.add("None");
                } else {
                    displaySubs.addAll(subcategories);
                }

                ArrayAdapter<String> subAdapter = new ArrayAdapter<>(Notes.this, android.R.layout.simple_spinner_item, displaySubs);
                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubcategory.setAdapter(subAdapter);

                if (isEditMode && note.getNoteSubcategory() != null) {
                    int subIndex = displaySubs.indexOf(note.getNoteSubcategory());
                    if (subIndex != -1) {
                        spinnerSubcategory.setSelection(subIndex);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (isEditMode) {
            actionButtonsLayout.setVisibility(View.VISIBLE);

            title.setText(note.getNoteTitle());
            subtitle.setText(note.getNoteSubtitle());
            desc.setText(note.getNoteDescription());

            if (note.getNoteCategory() != null) {
                int catIndex = mainCategories.indexOf(note.getNoteCategory());
                if (catIndex != -1) {
                    spinnerCategory.setSelection(catIndex);
                }
            }

            cbLearn.setChecked(note.isLearning());
            cbGen.setChecked(note.isGeneralToDo());
            cbToday.setChecked(note.isTodayTask());
            cbBuy.setChecked(note.isToBuyTask());
        }

        // 1. Configure the dialog positive and negative buttons on builder FIRST
        builder.setPositiveButton(isEditMode ? "Update" : "Create", (d, which) -> {
            String selectedMain = spinnerCategory.getSelectedItem() != null ?
                    spinnerCategory.getSelectedItem().toString() : "";
            String selectedSub = spinnerSubcategory.getSelectedItem() != null ?
                    spinnerSubcategory.getSelectedItem().toString() : "";
            if ("None".equals(selectedSub)) {
                selectedSub = "";
            }

            String q;
            if (isEditMode) {
                q = QueryMaker.updateNoteQuery(
                        note.getId(),
                        title.getText().toString(),
                        subtitle.getText().toString(),
                        desc.getText().toString(),
                        selectedMain,
                        selectedSub,
                        false,
                        cbLearn.isChecked(),
                        cbGen.isChecked(),
                        cbToday.isChecked(),
                        cbBuy.isChecked(),
                        null
                );
            } else {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                q = QueryMaker.insertNoteQuery(
                        title.getText().toString(),
                        subtitle.getText().toString(),
                        desc.getText().toString(),
                        selectedMain,
                        selectedSub,
                        false,
                        cbLearn.isChecked(),
                        cbGen.isChecked(),
                        cbToday.isChecked(),
                        cbBuy.isChecked(),
                        currentDate,
                        null
                );
            }

            GetFromSQLDatabase.runAzureQueryAIChat(q, new AzureDataCallback() {
                @Override public void onSuccess(ArrayList<RowInTable> res) {
                    runOnUiThread(() -> {
                        Toast.makeText(Notes.this, isEditMode ? "Updated!" : "Saved!", Toast.LENGTH_SHORT).show();
                        fetchNotesFromAzure();
                    });
                }
                @Override public void onFailure(String err) {
                    runOnUiThread(() -> Toast.makeText(Notes.this, "Error: " + err, Toast.LENGTH_SHORT).show());
                }
            });
        });

        builder.setNegativeButton("Cancel", null);

        // 2. CREATE the dialog instance HERE (now it is initialized before being used in listeners)
        AlertDialog dialog = builder.create();

        // 3. Attach listeners to the custom view buttons (dialog.dismiss() works with no error)
        if (isEditMode) {
            btnMarkAsDone.setOnClickListener(v -> {
                String markDoneQuery = QueryMaker.markNoteAsDoneQuery(note.getId());
                GetFromSQLDatabase.runAzureQueryAIChat(markDoneQuery, new AzureDataCallback() {
                    @Override
                    public void onSuccess(ArrayList<RowInTable> resultTable) {
                        runOnUiThread(() -> {
                            Toast.makeText(Notes.this, "Marked as done!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            fetchNotesFromAzure();
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        runOnUiThread(() -> Toast.makeText(Notes.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                    }
                });
            });

            btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(Notes.this)
                        .setTitle("Delete Note")
                        .setMessage("Move this note to deleted history?")
                        .setPositiveButton("Yes, Delete", (d, w) -> {
                            String deleteQuery = QueryMaker.markNoteAsDeletedQuery(note.getId());
                            GetFromSQLDatabase.runAzureQueryAIChat(deleteQuery, new AzureDataCallback() {
                                @Override
                                public void onSuccess(ArrayList<RowInTable> resultTable) {
                                    runOnUiThread(() -> {
                                        Toast.makeText(Notes.this, "Note deleted!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        fetchNotesFromAzure();
                                    });
                                }

                                @Override
                                public void onFailure(String errorMessage) {
                                    runOnUiThread(() -> Toast.makeText(Notes.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                                }
                            });
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        }

        // 4. Finally show the dialog
        dialog.show();

        addListeners();
    }

    private void setupVisibilityListeners() {
        CheckBox cb1 = findViewById(R.id.hideCbSection1);
        CheckBox cb2 = findViewById(R.id.hideCbSection2);
        CheckBox cb3 = findViewById(R.id.hideCbSection3);
        CheckBox cb4 = findViewById(R.id.hideCbSection4);

        android.widget.CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            adapter.setSectionVisibilities(
                    cb1.isChecked(),
                    cb2.isChecked(),
                    cb3.isChecked(),
                    cb4.isChecked()
            );
        };

        cb1.setOnCheckedChangeListener(listener);
        cb2.setOnCheckedChangeListener(listener);
        cb3.setOnCheckedChangeListener(listener);
        cb4.setOnCheckedChangeListener(listener);
    }

    private void setupFilterListeners() {
        filterSubtitle.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) { applyFilters(); }
            public void afterTextChanged(android.text.Editable s) {}
        });

        filterCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMainCat = parent.getItemAtPosition(position).toString();
                updateSubcategoryFilterSpinner(selectedMainCat);
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        filterUrgentlySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        android.widget.CompoundButton.OnCheckedChangeListener cbListener = (buttonView, isChecked) -> applyFilters();
        filterCbLearning.setOnCheckedChangeListener(cbListener);
        filterCbGeneral.setOnCheckedChangeListener(cbListener);
        filterCbToday.setOnCheckedChangeListener(cbListener);
        filterCbToBuy.setOnCheckedChangeListener(cbListener);
    }

    private void applyFilters() {
        String subtitle = filterSubtitle.getText().toString();

        String selectedCat = filterCategorySpinner.getSelectedItem() != null ?
                filterCategorySpinner.getSelectedItem().toString() : "All Categories";

        String selectedSubcat = filterUrgentlySpinner.getSelectedItem() != null ?
                filterUrgentlySpinner.getSelectedItem().toString() : "All Subcategories";

        // Filter through adapter
        adapter.filter(
                subtitle,
                selectedCat,
                selectedSubcat,
                filterCbLearning.isChecked(),
                filterCbGeneral.isChecked(),
                filterCbToday.isChecked(),
                filterCbToBuy.isChecked(),
                selectedDates
        );

        // Update the displayed notes counter
        updateNotesCountDisplay();
    }

    private void updateNotesCountDisplay() {
        if (tvNotesCount != null && adapter != null) {
            int displayedCount = adapter.getItemCount();
            int totalCount = adapter.getOriginalList().size();
            tvNotesCount.setText("Displayed: " + displayedCount + " (Total: " + totalCount + ")");
        }
    }

    private void addListeners(){
        pickDateDeadlineButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select a Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();


            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Ensure date formatting uses UTC to prevent 1-day timezone shifting
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedDate = sdf.format(new Date(selection));

                dialogDeadlineEditTextLocal.setText(formattedDate);
            });

            datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });
    }
}