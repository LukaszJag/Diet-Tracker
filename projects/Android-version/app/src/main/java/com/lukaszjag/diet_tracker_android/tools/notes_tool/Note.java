package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories.LearningCategories;

import java.util.ArrayList;
import java.util.Arrays;

public class Note {
    private String noteTitle;
    private String noteSubtitle;
    private String noteDescription;
    private String noteCategory;
    private String noteUrgently;

    LearningCategories noteLearningCategories;
    private boolean isLearning;
    private boolean isGeneralToDo;
    private boolean IisTodayTask;


    private ArrayList<String> urgentScaleEnglish = new ArrayList<>(Arrays.asList("no urgent scale", "Priority Deliverable",
            "Mission-Critical Sprint", "High-Stakes Campaign", "Marathon Project",
            "Strategic Initiative", "Monumental Undertaking", "Magnum Opus / Grand Challenge"));
    private ArrayList<String> urgentScalePolish = new ArrayList<>(Arrays.asList("brak ram czasowych", "Naglące zlecenie priorytetowe", "Krytyczna faza mobilizacji",
            "Złożona operacja celowa", "Wyzwanie długodystansowe (Projekt-maraton)", "Strategiczna transformacja", "Fundamentalne przedsięwzięcie",
            "Dzieło życia (Opus Magnum)"));

    public Note() {}

    public Note(String noteTitle, String noteSubtitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteSubtitle = noteSubtitle;
        this.noteDescription = noteDescription;
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
}

