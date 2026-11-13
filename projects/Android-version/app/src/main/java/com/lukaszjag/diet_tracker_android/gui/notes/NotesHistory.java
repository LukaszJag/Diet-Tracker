package com.lukaszjag.diet_tracker_android.gui.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.MyAdapter;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.Note;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.NoteCategoryData;
import com.lukaszjag.diet_tracker_android.tools.settings.AppSettings;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesHistory extends AppCompatActivity {

    private EditText filterSubtitle;
    private Spinner filterCategorySpinner;
    private Spinner filterSubcategorySpinner;
    private Spinner filterStatusSpinner;
    private CheckBox filterCbLearning, filterCbGeneral, filterCbToday, filterCbToBuy;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_history_layout);

        filterSubtitle = findViewById(R.id.historyFilterSubtitle);
        filterCategorySpinner = findViewById(R.id.historyFilterCategorySpinner);
        filterSubcategorySpinner = findViewById(R.id.historyFilterSubcategorySpinner);
        filterStatusSpinner = findViewById(R.id.historyStatusFilterSpinner);
        filterCbLearning = findViewById(R.id.historyFilterCbLearning);
        filterCbGeneral = findViewById(R.id.historyFilterCbGeneral);
        filterCbToday = findViewById(R.id.historyFilterCbToday);
        filterCbToBuy = findViewById(R.id.historyFilterCbToBuy);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position, note) -> showHistoryActionDialog(note));

        setupVisibilityListeners();
        setupFilterSpinners();
        setupFilterListeners();
        fetchHistoryFromAzure();

        AppSettings.syncFromAzure(this, success -> runOnUiThread(() -> {
            if (success) {
                ThemeHelper.applyButtonTheme(NotesHistory.this);
                if (adapter != null) adapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.applyButtonTheme(this);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
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

        // Status Filter: All, Done, Deleted
        List<String> statusList = Arrays.asList("All History", "Done Tasks", "Deleted Tasks");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterStatusSpinner.setAdapter(statusAdapter);
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
        filterSubcategorySpinner.setAdapter(subcatAdapter);
    }

    private void fetchHistoryFromAzure() {
        String query = QueryMaker.getAllHistoryNotesQuery();
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
                runOnUiThread(() -> Toast.makeText(NotesHistory.this, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showHistoryActionDialog(Note note) {
        String statusLabel = note.isDeleted() ? "Status: DELETED" : "Status: DONE";

        new AlertDialog.Builder(this)
                .setTitle(note.getNoteTitle())
                .setMessage(statusLabel + "\n\nSubtitle: " + note.getNoteSubtitle() +
                        "\nDescription: " + note.getNoteDescription() +
                        "\nCategory: " + note.getNoteCategory() + " / " + note.getNoteSubcategory())
                .setPositiveButton("Restore to Active", (dialog, which) -> {
                    String restoreQuery = QueryMaker.restoreNoteQuery(note.getId());
                    GetFromSQLDatabase.runAzureQueryAIChat(restoreQuery, new AzureDataCallback() {
                        @Override
                        public void onSuccess(ArrayList<RowInTable> resultTable) {
                            runOnUiThread(() -> {
                                Toast.makeText(NotesHistory.this, "Restored to active notes!", Toast.LENGTH_SHORT).show();
                                fetchHistoryFromAzure();
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            runOnUiThread(() -> Toast.makeText(NotesHistory.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNeutralButton("Delete Permanently", (dialog, which) -> {
                    new AlertDialog.Builder(NotesHistory.this)
                            .setTitle("Permanent Delete")
                            .setMessage("Are you sure? This cannot be undone.")
                            .setPositiveButton("Yes, Delete", (d, w) -> {
                                String permanentDeleteQuery = QueryMaker.deleteNotePermanentlyQuery(note.getId());
                                GetFromSQLDatabase.runAzureQueryAIChat(permanentDeleteQuery, new AzureDataCallback() {
                                    @Override
                                    public void onSuccess(ArrayList<RowInTable> resultTable) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(NotesHistory.this, "Permanently deleted", Toast.LENGTH_SHORT).show();
                                            fetchHistoryFromAzure();
                                        });
                                    }

                                    @Override
                                    public void onFailure(String errorMessage) {
                                        runOnUiThread(() -> Toast.makeText(NotesHistory.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                                    }
                                });
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void setupVisibilityListeners() {
        CheckBox cb1 = findViewById(R.id.historyHideCbSection1);
        CheckBox cb2 = findViewById(R.id.historyHideCbSection2);
        CheckBox cb3 = findViewById(R.id.historyHideCbSection3);
        CheckBox cb4 = findViewById(R.id.historyHideCbSection4);

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

        filterSubcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        filterStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        String selectedSubcat = filterSubcategorySpinner.getSelectedItem() != null ?
                filterSubcategorySpinner.getSelectedItem().toString() : "All Subcategories";

        String selectedStatus = "ALL";
        if (filterStatusSpinner.getSelectedItem() != null) {
            String choice = filterStatusSpinner.getSelectedItem().toString();
            if ("Done Tasks".equalsIgnoreCase(choice)) {
                selectedStatus = "DONE";
            } else if ("Deleted Tasks".equalsIgnoreCase(choice)) {
                selectedStatus = "DELETED";
            }
        }

        adapter.filter(
                subtitle,
                selectedCat,
                selectedSubcat,
                filterCbLearning.isChecked(),
                filterCbGeneral.isChecked(),
                filterCbToday.isChecked(),
                filterCbToBuy.isChecked(),
                selectedStatus
        );
    }
}