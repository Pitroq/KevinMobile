package com.pitroq.kevinmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pitroq.kevinmobile.Note;
import com.pitroq.kevinmobile.Notepad;
import com.pitroq.kevinmobile.R;

import java.util.List;

public class NotepadActivity extends AppCompatActivity {
    private static final String HOST = "http://192.168.1.40:8080/kevin/api/";
    private Notepad notepad;
    private LinearLayout notesListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        getSupportActionBar().setTitle(R.string.notepad_title);

        notesListLayout = findViewById(R.id.notesListLayout);
        notepad = new Notepad(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        notepad.loadNotesFromFile();
        fillList();
    }

    public void createNewNote(View view) {
        notepad.addNote("Untitled note", "");
        fillList();
    }

    private void fillList() {
        notesListLayout.removeAllViews();
        List<Note> notes = notepad.getNotes();
        for (Note note : notes) {
            Button button = new Button(getApplicationContext());
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.secondary_background_color));
            button.setTextColor(ContextCompat.getColor(this, R.color.font_color));
            button.setTextSize(16);
            button.setPadding(0, 60, 0, 60);
            button.setText(note.getTitle());
            button.setTag(note.getId());
            button.setOnClickListener(view -> {
                Intent intent = new Intent(this, NoteActivity.class);
                intent.putExtra("noteId", view.getTag().toString());
                startActivity(intent);
            });
            notesListLayout.addView(button);
        }
    }

    public void loadFromDatabase(View view) {
        String url = HOST + "getLatestNotepadJSON.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    notepad.fillNotes(response);
                    fillList();
                },
                error -> System.out.println("error")
        );
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
