package com.lukaszjag.diet_tracker_android.tools.notes_tool;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    private static final String FILE_NAME = "notes_data.json";

    public static void saveNotes(Context context, List<Note> notes) {
        try {
            // 1. Serialize the list of notes to a JSON String (your original logic)
            JSONArray jsonArray = new JSONArray();
            for (Note note : notes) {
                JSONObject noteJson = note.toJsonObject();
                if (noteJson != null) {
                    jsonArray.put(noteJson);
                }
            }
            String jsonContent = jsonArray.toString(4);

            // 2. Define the file name and find the MediaStore collection
            String fileName = "notes_data.json"; // You can replace this with your FILE_NAME constant
            ContentResolver resolver = context.getContentResolver();
            Uri collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI;

            Uri existingUri = null;

            // 3. Check if notes_data.json already exists in the Download directory
            String[] projection = new String[]{MediaStore.MediaColumns._ID};
            String selection = MediaStore.MediaColumns.DISPLAY_NAME + " = ? AND " + MediaStore.MediaColumns.RELATIVE_PATH + " = ?";
            // Points the query to "Download/diet-tracker-data/"
            String targetFolder = Environment.DIRECTORY_DOWNLOADS + "/diet-tracker-data/";
            String[] selectionArgs = new String[]{fileName, targetFolder};

            try (Cursor cursor = resolver.query(collection, projection, selection, selectionArgs, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
                    long id = cursor.getLong(idColumn);
                    existingUri = ContentUris.withAppendedId(collection, id);
                }
            }

            // 4. Use the existing file's URI or create a new entry if it doesn't exist
            Uri finalUri;
            if (existingUri != null) {
                finalUri = existingUri;
            } else {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "application/json");
                // Instructs the system to save inside "Download/diet-tracker-data"
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/diet-tracker-data");
                finalUri = resolver.insert(collection, values);
            }

            // 5. Write the converted JSON string to the target file
            if (finalUri != null) {
                // "wt" stands for write-truncate, which clears older content before writing
                try (OutputStream out = resolver.openOutputStream(finalUri, "wt")) {
                    if (out != null) {
                        out.write(jsonContent.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Note> loadNotes(Context context) {
        List<Note> notes = new ArrayList<>();
        String fileName = "notes_data.json"; // Replace with your FILE_NAME constant if needed
        ContentResolver resolver = context.getContentResolver();
        Uri collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI;

        Uri fileUri = null;

        // 1. Query MediaStore to locate "notes_data.json" in the Download folder
        String[] projection = new String[]{MediaStore.MediaColumns._ID};
        String selection = MediaStore.MediaColumns.DISPLAY_NAME + " = ? AND " + MediaStore.MediaColumns.RELATIVE_PATH + " = ?";
        // Points the query to "Download/diet-tracker-data/"
        String targetFolder = Environment.DIRECTORY_DOWNLOADS + "/diet-tracker-data/";
        String[] selectionArgs = new String[]{fileName, targetFolder};

        try (Cursor cursor = resolver.query(collection, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
                long id = cursor.getLong(idColumn);
                fileUri = ContentUris.withAppendedId(collection, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If the file is not found, return an empty list (replaces the old FileNotFoundException block)
        if (fileUri == null) {
            return notes;
        }

        // 2. Open the input stream using ContentResolver and load the data
        try (InputStream fis = resolver.openInputStream(fileUri); InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8); BufferedReader bufferedReader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject noteJson = jsonArray.getJSONObject(i);
                Note note = Note.fromJsonObject(noteJson);
                if (note != null) {
                    notes.add(note);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notes;
    }
}
