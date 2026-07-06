package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories.LearningCategories;

public class Note {
    private String noteTitle;
    private String noteDescription;
    private String noteCategory;
// General object of Category
//    Cat
    LearningCategories noteLearningCategories;
    private boolean isLearning;
    private boolean isGeneralToDo;
    private boolean IisTodayTask;

    private static final String JSON_TITLE = "title";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IDEA = "idea";
    private static final String JSON_TODO = "todo";
    private static final String JSON_IMPORTANT = "important";

    public Note() {

    }



}

//    public Note(JSONObject jo) throws JSONException {
//
//        mTitle =  jo.getString(JSON_TITLE);
//        mDescription = jo.getString(JSON_DESCRIPTION);
//        mIdea = jo.getBoolean(JSON_IDEA);
//        mTodo = jo.getBoolean(JSON_TODO);
//        mImportant = jo.getBoolean(JSON_IMPORTANT);
//    }
//
//    // Now we must provide an empty default constructor
//    // for when we create a Note as we provide a
//    // specialized constructor.
//
//
//    public JSONObject convertToJSON() throws JSONException{
//
//        JSONObject jo = new JSONObject();
//
//        jo.put(JSON_TITLE, mTitle);
//        jo.put(JSON_DESCRIPTION, mDescription);
//        jo.put(JSON_IDEA, mIdea);
//        jo.put(JSON_TODO, mTodo);
//        jo.put(JSON_IMPORTANT, mImportant);
//
//        return jo;

