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
