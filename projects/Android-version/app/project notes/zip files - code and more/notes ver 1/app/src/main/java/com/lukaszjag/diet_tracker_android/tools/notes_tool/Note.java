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
    private ArrayList<String> urgentScalePolish = new ArrayList<>(Arrays.asList("brak ram czasowych", "Nagl�ce zlecenie priorytetowe", "Krytyczna faza mobilizacji",
            "Z�o�ona operacja celowa", "Wyzwanie d�ugodystansowe (Projekt-maraton)", "Strategiczna transformacja", "Fundamentalne przedsi�wzi�cie",
            "Dzie�o �ycia (Opus Magnum)"));

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
