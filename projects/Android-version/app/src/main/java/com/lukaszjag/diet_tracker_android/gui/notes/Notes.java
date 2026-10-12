package com.lukaszjag.diet_tracker_android.gui.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.util.ArrayList;
import java.util.List;

public class Notes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // Unified button listener: Pass null to indicate we are creating a new note
        findViewById(R.id.button).setOnClickListener(v -> showNoteDialog(null));

        // Unified item click listener: Pass the note to edit
        adapter.setOnItemClickListener((position, note) -> showNoteDialog(note));

        setupVisibilityListeners();
        fetchNotesFromAzure();
    }

    private void fetchNotesFromAzure() {
        String query = QueryMaker.getAllNotesQuery();
        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                runOnUiThread(() -> {
                    adapter.clearAll(); // Ensure this method exists in your MyAdapter
                    for (RowInTable row : resultTable) {
                        Note note = new Note(
                                Long.parseLong(row.getValue("id")),
                                row.getValue("title"),
                                row.getValue("subtitle"),
                                row.getValue("description"),
                                row.getValue("category"),
                                row.getValue("is_urgently"),
                                "true".equals(row.getValue("is_learning")) || "1".equals(row.getValue("is_learning")),
                                "true".equals(row.getValue("is_general_to_do")) || "1".equals(row.getValue("is_general_to_do")),
                                "true".equals(row.getValue("is_today_task")) || "1".equals(row.getValue("is_today_task")),
                                "true".equals(row.getValue("is_to_buy_task")) || "1".equals(row.getValue("is_to_buy_task")),
                                null, row.getValue("date_created"), row.getValue("date_deadline")
                        );
                        adapter.addItem(note);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> Toast.makeText(Notes.this, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show());
            }
        });
    }

    // Unified Dialog Method for BOTH Add and Edit
    private void showNoteDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        final EditText title = dialogView.findViewById(R.id.dialogTitle);
        final EditText subtitle = dialogView.findViewById(R.id.dialogSubtitle);
        final EditText desc = dialogView.findViewById(R.id.dialogDescription);
        final EditText cat = dialogView.findViewById(R.id.dialogCategory);
        final CheckBox cbLearn = dialogView.findViewById(R.id.dialogCbLearning);
        final CheckBox cbGen = dialogView.findViewById(R.id.dialogCbGeneral);
        final CheckBox cbToday = dialogView.findViewById(R.id.dialogCbToday);
        final CheckBox cbBuy = dialogView.findViewById(R.id.dialogCbToBuy);

        boolean isEditMode = (note != null);

        // Pre-fill fields if in Edit Mode
        if (isEditMode) {
            title.setText(note.getNoteTitle());
            subtitle.setText(note.getNoteSubtitle());
            desc.setText(note.getNoteDescription());
            cat.setText(note.getNoteCategory());
            cbLearn.setChecked(note.isLearning());
            cbGen.setChecked(note.isGeneralToDo());
            cbToday.setChecked(note.isTodayTask());
            cbBuy.setChecked(note.isToBuyTask());
        }

        builder.setPositiveButton(isEditMode ? "Update" : "Create", (dialog, which) -> {
            String q;
            if (isEditMode) {
                // Update existing note
                q = QueryMaker.updateNoteQuery(
                        note.getId(),
                        title.getText().toString(),
                        subtitle.getText().toString(),
                        desc.getText().toString(),
                        cat.getText().toString(),
                        false,
                        cbLearn.isChecked(),
                        cbGen.isChecked(),
                        cbToday.isChecked(),
                        cbBuy.isChecked(),
                        null
                );
            } else {
                // Create new note
                q = QueryMaker.insertNoteQuery(
                        title.getText().toString(),
                        subtitle.getText().toString(),
                        desc.getText().toString(),
                        cat.getText().toString(),
                        false,
                        cbLearn.isChecked(),
                        cbGen.isChecked(),
                        cbToday.isChecked(),
                        cbBuy.isChecked(),
                        "2026-10-08",
                        null
                );
            }

            GetFromSQLDatabase.runAzureQueryAIChat(q, new AzureDataCallback() {
                @Override public void onSuccess(ArrayList<RowInTable> res) {
                    runOnUiThread(() -> {
                        Toast.makeText(Notes.this, isEditMode ? "Updated!" : "Saved!", Toast.LENGTH_SHORT).show();
                        fetchNotesFromAzure(); // Refresh the full list
                    });
                }
                @Override public void onFailure(String err) {
                    runOnUiThread(() -> Toast.makeText(Notes.this, "Error: " + err, Toast.LENGTH_SHORT).show());
                }
            });
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
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
}