package com.pitroq.kevinmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.pitroq.kevinmobile.Note;
import com.pitroq.kevinmobile.Notepad;
import com.pitroq.kevinmobile.R;

public class NoteActivity extends AppCompatActivity {
    private Notepad notepad;
    private int noteId;
    private EditText noteTitleEditText;
    private EditText noteContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setTitle(R.string.note_title);

        notepad = new Notepad(this);
        notepad.loadNotesFromFile();

        noteTitleEditText = findViewById(R.id.noteTitleEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteId = Integer.parseInt(getIntent().getStringExtra("noteId"));
        loadNote();
    }

    private void loadNote() {
        Note note = notepad.getNote(noteId);

        noteTitleEditText.setText(note.getTitle());

        String noteContent = note.getNoteContent();
        noteContent = noteContent.replace("{enter}", "\n");
        noteContentEditText.setText(noteContent);
    }

    public void deleteNote(View view) {
        String noteTitle = notepad.getNote(noteId).getTitle();
        notepad.deleteNote(noteId);
        Toast.makeText(this, "The note '" + noteTitle + "' has been deleted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NotepadActivity.class);
        startActivity(intent);
    }

    public void updateNote(View view) {
        String newTitle = noteTitleEditText.getText().toString();
        String newContent = noteContentEditText.getText().toString();
        newContent = newContent.replace("\n", "{enter}");
        notepad.updateNote(noteId, newTitle, newContent);
        Toast.makeText(this, "The note has been updated", Toast.LENGTH_SHORT).show();
    }
}
