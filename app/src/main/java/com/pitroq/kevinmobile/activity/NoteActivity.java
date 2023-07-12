package com.pitroq.kevinmobile.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.pitroq.kevinmobile.Note;
import com.pitroq.kevinmobile.Notepad;
import com.pitroq.kevinmobile.R;

public class NoteActivity extends AppCompatActivity {
    private Notepad notepad;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setTitle(R.string.note_title);

        notepad = new Notepad(this);
        notepad.loadNotesFromFile();

        noteId = Integer.parseInt(getIntent().getStringExtra("noteId"));
        loadNote();
    }

    private void loadNote() {
        Note note = notepad.getNote(noteId);
        ((EditText) findViewById(R.id.noteTitleEditText)).setText(note.getTitle());

        String noteContent = note.getNoteContent();
        noteContent = noteContent.replace("{enter}", "\n");
        ((EditText) findViewById(R.id.noteContentEditText)).setText(noteContent);
    }

}
