package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    private static final String FILE_NAME = "notes_data.json";

    // Resolves dynamically to the standard Download/diet-tracker-data directory on any Android device
    private static final File TARGET_DIRECTORY = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "diet-tracker-data"
    );

    /**
     * Resolves the target directory and ensures the folder structure is created.
     */
    private static File getTargetFile() {
        if (!TARGET_DIRECTORY.exists()) {
            TARGET_DIRECTORY.mkdirs(); // Automatically creates "diet-tracker-data" if missing
        }
        return new File(TARGET_DIRECTORY, FILE_NAME);
    }

    /**
     * Saves the list of notes to the specified external storage path.
     */
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

            File file = getTargetFile();
            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                osw.write(jsonContent);
            }
        } catch (Exception e) {
            android.util.Log.e("NoteStorage", "Error saving notes", e);
        }
    }

    /**
     * Loads the list of notes from the specified external storage path.
     */
    public static List<Note> loadNotes(Context context) {
        List<Note> notes = new ArrayList<>();
        File file = getTargetFile();

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

            // Strip hidden UTF-8 BOM if present (often added by Windows/external text editors)
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
            android.util.Log.e("NoteStorage", "Error reading or parsing notes JSON file", e);
        }

        return notes;
    }

    /**
     * Imports external JSON files via Uri, validates structure, and overwrites local notes storage.
     */
    public static boolean importNotes(Context context, Uri sourceUri) {
        try (InputStream in = context.getContentResolver().openInputStream(sourceUri)) {
            if (in == null) return false;

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
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

            JSONArray jsonArray = new JSONArray(jsonContent);
            List<Note> importedNotes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Note note = Note.fromJsonObject(jsonArray.getJSONObject(i));
                if (note != null) {
                    importedNotes.add(note);
                }
            }

            // Write validated imported notes directly to the target external storage
            saveNotes(context, importedNotes);
            return true;
        } catch (Exception e) {
            android.util.Log.e("NoteStorage", "Error importing notes", e);
            return false;
        }
    }
}