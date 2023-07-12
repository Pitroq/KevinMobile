package com.pitroq.kevinmobile;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;


import java.util.ArrayList;
import java.util.List;

public class Notepad extends JsonFileManager {
    private final List<Note> notes = new ArrayList<>();
    private final Type notesType = new TypeToken<List<Note>>(){}.getType();
    private final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private int id = 0;

    public Notepad(Context context) {
        super("notes.json", context);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(String title, String noteCotent) {
        notes.add(new Note(title, noteCotent, id));
        id++;
        saveToFile();
    }

    public void saveToFile() {
        String json = String.valueOf(gson.toJsonTree(notes, notesType));
        saveFileContent(json);
    }

    public void fillNotes(String content) {
        Note[] fileNotes = gson.fromJson(content, Note[].class);
        notes.clear();
        id = 0;
        for (Note fileNote : fileNotes) {
            String title = fileNote.getTitle();
            String noteContent = fileNote.getNoteContent();
            addNote(title, noteContent);
        }
    }

    public void loadNotesFromFile() {
        notes.clear();
        String content = getFileContent();
        fillNotes(content);
    }

    public Note getNote(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    public void deleteNote(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                notes.remove(note);
                break;
            }
        }
        saveToFile();
    }

    public void updateNote(int id, String title, String content) {
        for (Note note : notes) {
            if (note.getId() == id) {
                note.setTitle(title);
                note.setContent(content);
                break;
            }
        }
        saveToFile();
    }
}
