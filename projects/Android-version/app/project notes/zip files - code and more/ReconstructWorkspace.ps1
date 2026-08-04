$logPath = "reconstruction_log.txt"

try {
    # 1. Define Standard Android Target Directories
    $basePath = "app/src/main"
    $layoutDir = "$basePath/res/layout"
    $javaGuiDir = "$basePath/java/com/lukaszjag/diet_tracker_android/gui/notes"
    $javaToolDir = "$basePath/java/com/lukaszjag/diet_tracker_android/tools/notes_tool"
    $javaCatDir = "$basePath/java/com/lukaszjag/diet_tracker_android/tools/notes_tool/categories/learning_categories"

    # 2. Create target directories if they don't exist
    New-Item -ItemType Directory -Force -Path $layoutDir | Out-Null
    New-Item -ItemType Directory -Force -Path $javaGuiDir | Out-Null
    New-Item -ItemType Directory -Force -Path $javaToolDir | Out-Null
    New-Item -ItemType Directory -Force -Path $javaCatDir | Out-Null

    Write-Host "Creating layout and class files..." -ForegroundColor Cyan

    # --- File 1: item_layout.xml ---
    $itemLayout = @'
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    android:background="#EEEEEE"
    android:elevation="2dp"
    android:orientation="vertical"
    android:padding="12dp">

    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#000000"
        android:textSize="18sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#555555"
        android:textSize="15sp"
        android:layout_marginTop="2dp" />

    <TextView
        android:id="@+id/textView3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#888888"
        android:textSize="13sp"
        android:layout_marginTop="4dp" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="6dp">

        <TextView
            android:id="@+id/tvCategory"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:textColor="#2E7D32"
            android:textSize="12sp"
            android:text="Category: -" />

        <TextView
            android:id="@+id/tvUrgently"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:textColor="#C62828"
            android:textSize="12sp"
            android:text="Urgently: -" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="4dp">

        <CheckBox
            android:id="@+id/cbIsLearning"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Learning"
            android:textSize="11sp"
            android:clickable="false"
            android:focusable="false"
            android:scaleX="0.85"
            android:scaleY="0.85"/>

        <CheckBox
            android:id="@+id/cbIsGeneralToDo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="General"
            android:textSize="11sp"
            android:clickable="false"
            android:focusable="false"
            android:scaleX="0.85"
            android:scaleY="0.85"/>

        <CheckBox
            android:id="@+id/cbIsTodayTask"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Today"
            android:textSize="11sp"
            android:clickable="false"
            android:focusable="false"
            android:scaleX="0.85"
            android:scaleY="0.85"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="4dp">

        <TextView
            android:id="@+id/tvDates"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="#666666"
            android:textSize="11sp"
            android:text="Created: - | Deadline: -" />

        <View
            android:layout_width="0dp"
            android:layout_height="1dp"
            android:layout_weight="1"/>

        <TextView
            android:id="@+id/tvDaysSince"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="#D32F2F"
            android:textSize="11sp"
            android:textStyle="italic"
            android:text="0 days since create" />
    </LinearLayout>
</LinearLayout>
'@
    Set-Content -Path "$layoutDir/item_layout.xml" -Value $itemLayout -Encoding utf8

    # --- File 2: notes_view_layout.xml ---
    $notesViewLayout = @'
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimary"
        android:minHeight="?attr/actionBarSize"
        android:theme="?attr/actionBarTheme">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Notes"
            android:textColor="#FFFFFF"
            android:textSize="18sp"
            android:textStyle="bold" />

        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="end"
            android:layout_marginEnd="16dp"
            android:text="Add" />

    </androidx.appcompat.widget.Toolbar>

    <LinearLayout
        android:id="@+id/filterLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/toolbar2"
        android:orientation="vertical"
        android:padding="8dp"
        android:background="#F5F5F5">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Filter list by:"
            android:textStyle="bold"
            android:textSize="12sp"
            android:textColor="#555555"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:weightSum="3">

            <EditText
                android:id="@+id/filterSubtitle"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:hint="Subtitle"
                android:textSize="12sp"
                android:inputType="text" />

            <EditText
                android:id="@+id/filterCategory"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:hint="Category"
                android:textSize="12sp"
                android:inputType="text" />

            <EditText
                android:id="@+id/filterUrgently"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:hint="Urgency"
                android:textSize="12sp"
                android:inputType="text" />
        </LinearLayout>
    </LinearLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="vertical"
        android:layout_below="@id/filterLayout"/>
</RelativeLayout>
'@
    Set-Content -Path "$layoutDir/notes_view_layout.xml" -Value $notesViewLayout -Encoding utf8

    # --- File 3: dialog_note.xml ---
    $dialogNote = @'
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="New Note Details"
            android:textSize="18sp"
            android:textStyle="bold"
            android:layout_marginBottom="12dp"/>

        <EditText
            android:id="@+id/dialogTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Title"
            android:inputType="textCapSentences" />

        <EditText
            android:id="@+id/dialogSubtitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Subtitle"
            android:inputType="textCapSentences" />

        <EditText
            android:id="@+id/dialogDescription"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Description"
            android:inputType="textMultiLine"
            android:minLines="2" />

        <EditText
            android:id="@+id/dialogCategory"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Category"
            android:inputType="textCapSentences" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Urgency Scale:"
            android:layout_marginTop="8dp"
            android:textSize="12sp"
            android:textColor="#555555"/>

        <Spinner
            android:id="@+id/dialogSpinnerUrgent"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="4dp" />

        <CheckBox
            android:id="@+id/dialogCbLearning"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Is Learning Task"
            android:layout_marginTop="8dp"/>

        <CheckBox
            android:id="@+id/dialogCbGeneral"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Is General To-Do" />

        <CheckBox
            android:id="@+id/dialogCbToday"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Is Today Task" />

        <TextView
            android:id="@+id/tvLearningCatLabel"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Learning Category Option:"
            android:layout_marginTop="8dp"
            android:textSize="12sp"
            android:textColor="#555555"
            android:visibility="gone"/>

        <Spinner
            android:id="@+id/dialogSpinnerLearningCat"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="4dp"
            android:visibility="gone"/>

        <EditText
            android:id="@+id/dialogDeadline"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Deadline Date (Tap to select)"
            android:focusable="false"
            android:clickable="true"
            android:layout_marginTop="8dp" />
    </LinearLayout>
</ScrollView>
'@
    Set-Content -Path "$layoutDir/dialog_note.xml" -Value $dialogNote -Encoding utf8

    # --- File 4: Notes.java ---
    $notesJava = @'
package com.lukaszjag.diet_tracker_android.gui.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

        btnAddNote = findViewById(R.id.button);
        btnAddNote.setText("Add Note");
        btnAddNote.setOnClickListener(v -> showAddNoteDialog());

        populateDummyData();
    }

    private void applyFilters() {
        String subtitleQuery = filterSubtitle.getText().toString();
        String categoryQuery = filterCategory.getText().toString();
        String urgentlyQuery = filterUrgently.getText().toString();
        adapter.filter(subtitleQuery, categoryQuery, urgentlyQuery);
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
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void populateDummyData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        adapter.addItem(new Note("BitWarden Config", "Skonfigurowaæ BitWardena", "Za³o¿yæ konto, ustawiæ has³o, dodaæ kilka hase³", "Security", "High-Stakes Campaign", false, true, false, null, today, ""));
        adapter.addItem(new Note("Bookshelf Sort", "Dodaæ kategorie do ksi¹¿ek na pó³ce", "Przeniesienie bazy do serwisu lubimyczytaæ.pl", "Organize", "no urgent scale", false, false, false, null, today, ""));
        adapter.addItem(new Note("Savings Goal", "Wyznaczyæ kwote do od³o¿enia na monitor", "ZnaleŸæ konkretny model do kupienia i od³o¿yæ bud¿et", "Financial", "Strategic Initiative", false, true, true, null, today, "2026-12-31"));
    }
}
'@
    Set-Content -Path "$javaGuiDir/Notes.java" -Value $notesJava -Encoding utf8

    # --- File 5: MyAdapter.java ---
    $myAdapterJava = @'
package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> noteList;
    private List<Note> originalList;

    public MyAdapter() {
        this.noteList = new ArrayList<>();
        this.originalList = new ArrayList<>();
    }

    public void addItem(Note note) {
        noteList.add(note);
        originalList.add(note);
        notifyItemInserted(noteList.size() - 1);
    }

    public Note getItem(int position) {
        if (position >= 0 && position < noteList.size()) {
            return noteList.get(position);
        }
        return null;
    }

    public void setItem(int position, Note note) {
        if (position >= 0 && position < noteList.size()) {
            Note oldNote = noteList.get(position);
            int origIdx = originalList.indexOf(oldNote);
            if (origIdx != -1) {
                originalList.set(origIdx, note);
            }
            noteList.set(position, note);
            notifyItemChanged(position);
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < noteList.size()) {
            Note note = noteList.get(position);
            originalList.remove(note);
            noteList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, noteList.size());
        }
    }

    public void filter(String subtitle, String category, String urgently) {
        noteList.clear();
        String qSub = subtitle != null ? subtitle.toLowerCase().trim() : "";
        String qCat = category != null ? category.toLowerCase().trim() : "";
        String qUrg = urgently != null ? urgently.toLowerCase().trim() : "";

        for (Note note : originalList) {
            boolean matchSub = qSub.isEmpty() || (note.getNoteSubtitle() != null && note.getNoteSubtitle().toLowerCase().contains(qSub));
            boolean matchCat = qCat.isEmpty() || (note.getNoteCategory() != null && note.getNoteCategory().toLowerCase().contains(qCat));
            boolean matchUrg = qUrg.isEmpty() || (note.getNoteUrgently() != null && note.getNoteUrgently().toLowerCase().contains(qUrg));

            if (matchSub && matchCat && matchUrg) {
                noteList.add(note);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note currentItem = noteList.get(position);

        holder.tv1.setText(currentItem.getNoteTitle());
        holder.tv2.setText(currentItem.getNoteSubtitle());
        holder.tv3.setText(currentItem.getNoteDescription());

        holder.tvCategory.setText("Category: " + (currentItem.getNoteCategory() != null ? currentItem.getNoteCategory() : "-"));
        holder.tvUrgently.setText("Urgently: " + (currentItem.getNoteUrgently() != null ? currentItem.getNoteUrgently() : "-"));

        holder.cbIsLearning.setChecked(currentItem.isLearning());
        holder.cbIsGeneralToDo.setChecked(currentItem.isGeneralToDo());
        holder.cbIsTodayTask.setChecked(currentItem.isIisTodayTask());

        String createdDate = currentItem.getDateCreated() != null ? currentItem.getDateCreated() : "-";
        String deadlineDate = (currentItem.getDateDeadline() != null && !currentItem.getDateDeadline().isEmpty()) ? currentItem.getDateDeadline() : "None";
        holder.tvDates.setText("Created: " + createdDate + " | Deadline: " + deadlineDate);

        long daysSince = currentItem.getDaysSinceCreation();
        holder.tvDaysSince.setText(daysSince + " days since create");
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;
        TextView tvCategory, tvUrgently, tvDates, tvDaysSince;
        CheckBox cbIsLearning, cbIsGeneralToDo, cbIsTodayTask;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView1);
            tv2 = itemView.findViewById(R.id.textView2);
            tv3 = itemView.findViewById(R.id.textView3);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvUrgently = itemView.findViewById(R.id.tvUrgently);
            tvDates = itemView.findViewById(R.id.tvDates);
            tvDaysSince = itemView.findViewById(R.id.tvDaysSince);
            cbIsLearning = itemView.findViewById(R.id.cbIsLearning);
            cbIsGeneralToDo = itemView.findViewById(R.id.cbIsGeneralToDo);
            cbIsTodayTask = itemView.findViewById(R.id.cbIsTodayTask);
        }
    }
}
'@
    Set-Content -Path "$javaToolDir/MyAdapter.java" -Value $myAdapterJava -Encoding utf8

    # --- File 6: Note.java ---
    $noteJava = @'
package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories.LearningCategories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Note {
    private String noteTitle;
    private String noteSubtitle;
    private String noteDescription;
    private String noteCategory;
    private String noteUrgently;

    private LearningCategories noteLearningCategories;
    private boolean isLearning;
    private boolean isGeneralToDo;
    private boolean IisTodayTask;

    private String dateCreated;
    private String dateDeadline;

    private ArrayList<String> urgentScaleEnglish = new ArrayList<>(Arrays.asList("no urgent scale", "Priority Deliverable",
            "Mission-Critical Sprint", "High-Stakes Campaign", "Marathon Project",
            "Strategic Initiative", "Monumental Undertaking", "Magnum Opus / Grand Challenge"));
    private ArrayList<String> urgentScalePolish = new ArrayList<>(Arrays.asList("brak ram czasowych", "Nagl¹ce zlecenie priorytetowe", "Krytyczna faza mobilizacji",
            "Z³o¿ona operacja celowa", "Wyzwanie d³ugodystansowe (Projekt-maraton)", "Strategiczna transformacja", "Fundamentalne przedsiêwziêcie",
            "Dzie³o ¿ycia (Opus Magnum)"));

    public Note() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.dateCreated = sdf.format(new Date());
        this.dateDeadline = "";
        this.noteCategory = "General";
        this.noteUrgently = "no urgent scale";
    }

    public Note(String noteTitle, String noteSubtitle, String noteDescription) {
        this();
        this.noteTitle = noteTitle;
        this.noteSubtitle = noteSubtitle;
        this.noteDescription = noteDescription;
    }

    public Note(String noteTitle, String noteSubtitle, String noteDescription, String noteCategory, String noteUrgently,
                boolean isLearning, boolean isGeneralToDo, boolean IisTodayTask,
                LearningCategories noteLearningCategories, String dateCreated, String dateDeadline) {
        this.noteTitle = noteTitle;
        this.noteSubtitle = noteSubtitle;
        this.noteDescription = noteDescription;
        this.noteCategory = noteCategory;
        this.noteUrgently = noteUrgently;
        this.isLearning = isLearning;
        this.isGeneralToDo = isGeneralToDo;
        this.IisTodayTask = IisTodayTask;
        this.noteLearningCategories = noteLearningCategories;
        this.dateCreated = dateCreated;
        this.dateDeadline = dateDeadline;
    }

    public long getDaysSinceCreation() {
        if (dateCreated == null || dateCreated.isEmpty()) return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date createdDate = sdf.parse(dateCreated);
            Date systemToday = new Date();
            Date systemTodayClean = sdf.parse(sdf.format(systemToday));
            long diffInMs = systemTodayClean.getTime() - createdDate.getTime();
            return TimeUnit.DAYS.convert(diffInMs, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            return 0;
        }
    }

    public String getNoteSubtitle() {
        return noteSubtitle;
    }

    public void setNoteSubtitle(String noteSubtitle) {
        this.noteSubtitle = noteSubtitle;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteCategory() {
        return noteCategory;
    }

    public void setNoteCategory(String noteCategory) {
        this.noteCategory = noteCategory;
    }

    public String getNoteUrgently() {
        return noteUrgently;
    }

    public void setNoteUrgently(String noteUrgently) {
        this.noteUrgently = noteUrgently;
    }

    public boolean isLearning() {
        return isLearning;
    }

    public void setLearning(boolean learning) {
        isLearning = learning;
    }

    public boolean isGeneralToDo() {
        return isGeneralToDo;
    }

    public void setGeneralToDo(boolean generalToDo) {
        isGeneralToDo = generalToDo;
    }

    public boolean isIisTodayTask() {
        return IisTodayTask;
    }

    public void setIisTodayTask(boolean iisTodayTask) {
        IisTodayTask = iisTodayTask;
    }

    public LearningCategories getNoteLearningCategories() {
        return noteLearningCategories;
    }

    public void setNoteLearningCategories(LearningCategories noteLearningCategories) {
        this.noteLearningCategories = noteLearningCategories;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(String dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public ArrayList<String> getUrgentScaleEnglish() {
        return urgentScaleEnglish;
    }

    public ArrayList<String> getUrgentScalePolish() {
        return urgentScalePolish;
    }
}
'@
    Set-Content -Path "$javaToolDir/Note.java" -Value $noteJava -Encoding utf8

    # --- File 7: LearningCategories.java ---
    $learningCategoriesJava = @'
package com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories;

public class LearningCategories {
    private String categoryName;
    private boolean isMainCategory;
    private boolean iSubCategory;

    public LearningCategories(String categoryName, boolean isMainCategory, boolean iSubCategory) {
        this.categoryName = categoryName;
        this.isMainCategory = isMainCategory;
        this.iSubCategory = iSubCategory;
    }

    @Override
    public String toString() {
        return categoryName != null ? categoryName : "Uncategorized";
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isMainCategory() {
        return isMainCategory;
    }

    public void setMainCategory(boolean mainCategory) {
        isMainCategory = mainCategory;
    }

    public boolean isiSubCategory() {
        return iSubCategory;
    }

    public void setiSubCategory(boolean iSubCategory) {
        this.iSubCategory = iSubCategory;
    }
}
'@
    Set-Content -Path "$javaCatDir/LearningCategories.java" -Value $learningCategoriesJava -Encoding utf8

    # 3. Log Success Status
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $successMsg = "[$timestamp] SUCCESS: All folders and 7 source code files have been written directly to disk under 'app/src/main/'."
    Set-Content -Path $logPath -Value $successMsg
    
    Write-Host $successMsg -ForegroundColor Green
}
catch {
    # 4. Log Failure Status
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $errorMsg = "[$timestamp] ERROR: Workspace reconstruction failed. Details: $_"
    Set-Content -Path $logPath -Value $errorMsg
    
    Write-Error "Workspace reconstruction failed. Read '$logPath' for logs."
}