$logPath = "$PSScriptRoot\json_setup_log.txt"

try {
    # 1. Smart Location Detection
    $currentDir = $PSScriptRoot
    if (Test-Path "$currentDir\app\src\main") {
        # Executing from Android project root directory
        $layoutDir = "$currentDir\app\src\main\res\layout"
        $javaGuiDir = "$currentDir\app\src\main\java\com\lukaszjag\diet_tracker_android\gui\notes"
        $javaToolDir = "$currentDir\app\src\main\java\com\lukaszjag\diet_tracker_android\tools\notes_tool"
        Write-Host "Detected Android project root. Placing files directly in project src structure." -ForegroundColor Green
    } else {
        # Executing from staging folder/subdirectory (like 'notes ver 2')
        $layoutDir = "$currentDir\res\layout"
        $javaGuiDir = "$currentDir\com\lukaszjag\diet_tracker_android\gui\notes"
        $javaToolDir = "$currentDir\com\lukaszjag\diet_tracker_android\tools\notes_tool"
        Write-Host "Detected staging/sub-directory. Creating files locally inside: $currentDir" -ForegroundColor Yellow
    }

    # 2. Force Directory Creation using Robust .NET API
    [System.IO.Directory]::CreateDirectory($layoutDir) | Out-Null
    [System.IO.Directory]::CreateDirectory($javaGuiDir) | Out-Null
    [System.IO.Directory]::CreateDirectory($javaToolDir) | Out-Null

    Write-Host "Generating files..." -ForegroundColor Cyan

    # --- File 1: NoteStorage.java ---
    $noteStorageJava = @'
package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    private static final String FILE_NAME = "notes_data.json";

    public static void saveNotes(Context context, List<Note> notes) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Note note : notes) {
                JSONObject noteJson = note.toJsonObject();
                if (noteJson != null) {
                    jsonArray.put(noteJson);
                }
            }
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes("UTF-8"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Note> loadNotes(Context context) {
        List<Note> notes = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject noteJson = jsonArray.getJSONObject(i);
                Note note = Note.fromJsonObject(noteJson);
                if (note != null) {
                    notes.add(note);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            // Normal fallback state on initial startup when no database file exists
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }
}
'@
    Set-Content -Path "$javaToolDir/NoteStorage.java" -Value $noteStorageJava -Encoding utf8

    # --- File 2: Note.java ---
    $noteJava = @'
package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories.LearningCategories;

import org.json.JSONObject;
import org.json.JSONException;

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

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("noteTitle", noteTitle);
            json.put("noteSubtitle", noteSubtitle);
            json.put("noteDescription", noteDescription);
            json.put("noteCategory", noteCategory);
            json.put("noteUrgently", noteUglyUrgent(noteUrgently));
            json.put("isLearning", isLearning);
            json.put("isGeneralToDo", isGeneralToDo);
            json.put("IisTodayTask", IisTodayTask);
            json.put("dateCreated", dateCreated);
            json.put("dateDeadline", dateDeadline);

            if (noteLearningCategories != null) {
                JSONObject lcJson = new JSONObject();
                lcJson.put("categoryName", noteLearningCategories.getCategoryName());
                lcJson.put("isMainCategory", noteLearningCategories.isMainCategory());
                lcJson.put("iSubCategory", noteLearningCategories.isiSubCategory());
                json.put("noteLearningCategories", lcJson);
            }
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Note fromJsonObject(JSONObject json) {
        try {
            String title = json.optString("noteTitle", "");
            String subtitle = json.optString("noteSubtitle", "");
            String description = json.optString("noteDescription", "");
            String category = json.optString("noteCategory", "General");
            String urgently = json.optString("noteUrgently", "no urgent scale");
            boolean isLearning = json.optBoolean("isLearning", false);
            boolean isGeneralToDo = json.optBoolean("isGeneralToDo", false);
            boolean IisTodayTask = json.optBoolean("IisTodayTask", false);
            String dateCreated = json.optString("dateCreated", "");
            String dateDeadline = json.optString("dateDeadline", "");

            LearningCategories lc = null;
            if (json.has("noteLearningCategories")) {
                JSONObject lcJson = json.getJSONObject("noteLearningCategories");
                String lcName = lcJson.optString("categoryName", "");
                boolean lcMain = lcJson.optBoolean("isMainCategory", false);
                boolean lcSub = lcJson.optBoolean("iSubCategory", false);
                lc = new LearningCategories(lcName, lcMain, lcSub);
            }

            return new Note(title, subtitle, description, category, urgently,
                    isLearning, isGeneralToDo, IisTodayTask, lc, dateCreated, dateDeadline);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String noteUglyUrgent(String value) {
        return value != null ? value : "no urgent scale";
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

    # --- File 3: MyAdapter.java ---
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

    public List<Note> getOriginalList() {
        return originalList;
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

            NoteStorage.saveNotes(Notes.this, adapter.getOriginalList());
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

        NoteStorage.saveNotes(this, adapter.getOriginalList());
    }
}
'@
    Set-Content -Path "$javaGuiDir/Notes.java" -Value $notesJava -Encoding utf8

    # 3. Log Success status
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $successMsg = "[$timestamp] SUCCESS: JSON persistence classes compiled and setup successfully."
    Set-Content -Path $logPath -Value $successMsg
    
    Write-Host $successMsg -ForegroundColor Green
}
catch {
    # 4. Log Failure status
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $errorMsg = "[$timestamp] ERROR: Setup failed. Details: $_"
    Set-Content -Path $logPath -Value $errorMsg
    
    Write-Error "JSON Persist setup failed. Read '$logPath' for logs."
}