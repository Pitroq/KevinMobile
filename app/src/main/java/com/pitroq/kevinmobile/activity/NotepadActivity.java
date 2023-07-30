package com.pitroq.kevinmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pitroq.kevinmobile.Note;
import com.pitroq.kevinmobile.Notepad;
import com.pitroq.kevinmobile.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotepadActivity extends AppCompatActivity {
    private static final String API_URL = "http://192.168.1.40:8080/kevin/api/";
    private Notepad notepad;
    private LinearLayout notesListLayout;
    private final String API_KEY = "1230983218901";

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
        Toast.makeText(this, "A new note has been created", Toast.LENGTH_SHORT).show();
    }

    private void fillList() {
        notesListLayout.removeAllViews();
        List<Note> notes = notepad.getNotes();
        for (Note note : notes) {
            Button button = new Button(getApplicationContext());
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.secondary_background_color));
            button.setTextColor(ContextCompat.getColor(this, R.color.font_color));
            button.setTextSize(16);
            button.setTransformationMethod(null);
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

    public void loadNotepadFromDB(View view) {
        String url = API_URL + "getLatestNotepadJSON.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.isEmpty()) {
                        Toast.makeText(this, "An error occurred while receiving data from the database", Toast.LENGTH_LONG).show();
                        return;
                    }
                    notepad.fillNotes(response);
                    fillList();
                    Toast.makeText(this, "Successfully received data from the database", Toast.LENGTH_LONG).show();
                },
                error -> Toast.makeText(this, "An error occurred while receiving data from the database", Toast.LENGTH_LONG).show()
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("api_key", API_KEY);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void sendNotepadToDB(View view) {
        String url = API_URL + "sendNotepadJSON.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Successfully sent data to the database", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(this, "An error occurred while sending data to the database", Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                String notepadJSON = notepad.getFileContent();
                params.put("api_key", API_KEY);
                params.put("notepadJSON", notepadJSON);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}











