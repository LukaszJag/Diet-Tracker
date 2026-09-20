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

    private ArrayList<String> urgentScaleEnglish = new ArrayList<>(Arrays.asList(
            "no urgent scale",
            "Day task",
            "ASAP",
            "To end of month",
            "Week to done",
            "Optional"));

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

    //<editor-fold desc="Getters and Setters">
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

    //</editor-fold>
}
