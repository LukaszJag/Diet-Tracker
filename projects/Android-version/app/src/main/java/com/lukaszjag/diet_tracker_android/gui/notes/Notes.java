package com.lukaszjag.diet_tracker_android.gui.notes;

import android.os.Bundle;
import android.view.View;
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

        findViewById(R.id.button).setOnClickListener(v -> showAddNoteDialog());

        // Fetch data from Azure on load
        fetchNotesFromAzure();
    }

    private void fetchNotesFromAzure() {
        String query = QueryMaker.getAllNotesQuery();
        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                List<Note> notesList = new ArrayList<>();
                for (RowInTable row : resultTable) {
                    Note note = new Note(
                            row.getValue("title"),
                            row.getValue("subtitle"),
                            row.getValue("description"),
                            row.getValue("category"),
                            row.getValue("is_urgently"), // Simplified
                            row.getValue("is_learning").equals("true") || row.getValue("is_learning").equals("1"),
                            row.getValue("is_general_to_do").equals("true") || row.getValue("is_general_to_do").equals("1"),
                            row.getValue("is_today_task").equals("true") || row.getValue("is_today_task").equals("1"),
                            row.getValue("is_to_buy_task").equals("true") || row.getValue("is_to_buy_task").equals("1"),
                            null, // LearningCategories mapping
                            row.getValue("date_created"),
                            row.getValue("date_deadline")
                    );
                    notesList.add(note);
                }
                runOnUiThread(() -> {
                    for(Note n : notesList) adapter.addItem(n);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> Toast.makeText(Notes.this, "Failed to load: " + errorMessage, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showAddNoteDialog() {
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

        builder.setPositiveButton("Create", (dialog, which) -> {
            String q = QueryMaker.insertNoteQuery(
                    title.getText().toString(), subtitle.getText().toString(),
                    desc.getText().toString(), cat.getText().toString(),
                    false, cbLearn.isChecked(), cbGen.isChecked(),
                    cbToday.isChecked(), cbBuy.isChecked(), "2026-10-08", null
            );

            GetFromSQLDatabase.runAzureQueryAIChat(q, new AzureDataCallback() {
                @Override public void onSuccess(ArrayList<RowInTable> res) {
                    runOnUiThread(() -> Toast.makeText(Notes.this, "Saved!", Toast.LENGTH_SHORT).show());
                }
                @Override public void onFailure(String err) {
                    runOnUiThread(() -> Toast.makeText(Notes.this, "Error: " + err, Toast.LENGTH_SHORT).show());
                }
            });
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}