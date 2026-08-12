package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
            String jsonContent = jsonArray.toString(4);

            // Save directly to the app's secure internal files directory
            File file = new File(context.getFilesDir(), FILE_NAME);
            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                osw.write(jsonContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Note> loadNotes(Context context) {
        List<Note> notes = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            return notes;
        }

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int numRead;
            while ((numRead = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, numRead);
            }

            String jsonContent = sb.toString();

            // Strip hidden UTF-8 BOM if present
            if (jsonContent.startsWith("\uFEFF")) {
                jsonContent = jsonContent.substring(1);
            }
            jsonContent = jsonContent.trim();

            if (!jsonContent.isEmpty()) {
                JSONArray jsonArray = new JSONArray(jsonContent);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject noteJson = jsonArray.getJSONObject(i);
                    Note note = Note.fromJsonObject(noteJson);
                    if (note != null) {
                        notes.add(note);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notes;
    }
}