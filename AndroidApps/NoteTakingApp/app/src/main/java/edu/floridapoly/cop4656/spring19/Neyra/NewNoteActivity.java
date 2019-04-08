package edu.floridapoly.cop4656.spring19.Neyra;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewNoteActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteActivity";

    //Variables
    private EditText noteNameEditText;
    private EditText noteEditText;

    SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Button deleteButton;
        Button saveButton;

        noteNameEditText = findViewById(R.id.noteNameEditText);
        noteEditText = findViewById(R.id.noteEditText);

        deleteButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        myDatabase = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes (name VARCHAR, date VARCHAR, note VARCHAR, id INTEGER PRIMARY KEY)");

        getIncomingIntent();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Note deleted!", Toast.LENGTH_LONG).show();
                myDatabase.execSQL("DELETE FROM notes WHERE name = '"+noteNameEditText.getText().toString()+"'");
                backToMain();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteName = noteNameEditText.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                String noteDate = dateFormat.format(currentTime);
                String note = noteEditText.getText().toString();

                myDatabase.execSQL("DELETE FROM notes WHERE name = '"+noteNameEditText.getText().toString()+"'");
                myDatabase.execSQL("INSERT INTO notes (name, date, note) VALUES ('"+noteName+"','"+noteDate+"','"+note+"')");

                Toast.makeText(getApplicationContext(),"Note saved!", Toast.LENGTH_LONG).show();
                backToMain();
            }
        });

    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("noteName")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            String noteName = getIntent().getStringExtra("noteName");

            noteNameEditText.setText(noteName);

            Cursor c = myDatabase.rawQuery("SELECT * FROM notes WHERE name = '" + noteName + "'", null);
            int saveIndex = c.getColumnIndex("note");
            c.moveToFirst();

            String saveNote = c.getString(saveIndex);
            c.close();
            noteEditText.setText(saveNote);

            editDataBase(noteName);
        }
    }

    private void editDataBase(String noteName){
        Log.d(TAG, "editDataBase: modifying database.");

        noteNameEditText.setText(noteName);

        Cursor c = myDatabase.rawQuery("SELECT * FROM notes WHERE name = '"+noteName+"'", null);
        int saveIndex = c.getColumnIndex("note");
        c.moveToFirst();
        String saveNote = c.getString(saveIndex);
        c.close();
        noteEditText.setText(saveNote);
    }

    private void backToMain(){
        Intent toMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(toMain);
    }
}
