package com.pitroq.kevinmobile.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.pitroq.kevinmobile.Notepad;
import com.pitroq.kevinmobile.R;

public class NoteActivity extends AppCompatActivity {
    private Notepad notepad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setTitle(R.string.note_title);

        notepad = new Notepad(this);

        String noteId = getIntent().getStringExtra("noteId");
        System.out.println("NOTE: " + noteId);
    }

}
