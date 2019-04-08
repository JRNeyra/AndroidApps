package edu.floridapoly.cop4656.spring19.Neyra;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //Variables
    private ArrayList<String> mNoteNames = new ArrayList<>();
    private ArrayList<String> mNoteDates = new ArrayList<>();

    //Widgets
    private View addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: started");

        getNotes();

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewNote = new Intent(getApplicationContext(), NewNoteActivity.class);
                toNewNote.putExtra("noteId", 0);
                startActivity(toNewNote);
            }
        });
    }

    private void getNotes(){
        Log.d(TAG, "getNotes: getting notes");

        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes (name VARCHAR, date VARCHAR, note VARCHAR, id INTEGER PRIMARY KEY)");

            Cursor c = myDatabase.rawQuery("SELECT * FROM notes", null);
            int nameIndex = c.getColumnIndex("name");
            int dateIndex = c.getColumnIndex("date");

            c.moveToFirst();

            while (c != null){
                mNoteNames.add(c.getString(nameIndex));
                mNoteDates.add(c.getString(dateIndex));
                c.moveToNext();
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNoteNames, mNoteDates, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
