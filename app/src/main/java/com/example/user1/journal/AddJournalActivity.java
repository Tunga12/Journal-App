package com.example.user1.journal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user1.journal.data.JournalContract;

import static com.example.user1.journal.MainActivity.getAllJournals;
import static com.example.user1.journal.MainActivity.mAdapter;
import static com.example.user1.journal.MainActivity.mDb;

public class AddJournalActivity extends AppCompatActivity {

    private EditText mJournalTitle;
    private EditText mJournalBody;

    public static final String EXTRA_JOURNAL_ID = "id";
    public static final long DEFAULT_JOURNAL_ID = -5;

    private long journalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        mJournalTitle = (EditText) findViewById(R.id.add_note_title);
        mJournalBody = (EditText) findViewById(R.id.add_note_body);

        Intent intent =  getIntent();
        if(intent != null && intent.hasExtra(EXTRA_JOURNAL_ID)){

            journalID = intent.getLongExtra(EXTRA_JOURNAL_ID, DEFAULT_JOURNAL_ID);
            populateUI(findJournalByID(journalID));

        }else{
            journalID = DEFAULT_JOURNAL_ID;
        }

    }

    private void populateUI(Cursor cursor) {
        if(cursor == null){
            return;
        }

        cursor.moveToFirst();

        mJournalTitle.setText(cursor.getString(cursor.getColumnIndex(JournalContract.JournalEntry.COLUMN_TITLE)));
        mJournalBody.setText(cursor.getString(cursor.getColumnIndex(JournalContract.JournalEntry.COLUMN_BODY)));

        cursor.close();
    }

    public void addToJournalList() {

        if(mJournalTitle.length() == 0 || mJournalBody.length() == 0){
            Toast.makeText(this, "The title and body of your journal CANNOT be empty.", Toast.LENGTH_LONG).show();
            return;
        }

        addJournal(journalID ,mJournalTitle.getText().toString(), mJournalBody.getText().toString());

        mAdapter.swapCursor(getAllJournals());

        finish();
    }

    private long  addJournal(long id, String title, String body){

        ContentValues cv = new ContentValues();
        cv.put(JournalContract.JournalEntry.COLUMN_TITLE, title);
        cv.put(JournalContract.JournalEntry.COLUMN_BODY, body);

        if(journalID == DEFAULT_JOURNAL_ID){
            return mDb.insert(JournalContract.JournalEntry.TABLE_NAME, null, cv);

        }else{
            return mDb.update(JournalContract.JournalEntry.TABLE_NAME,
                    cv,
                    JournalContract.JournalEntry._ID + "= " + id,
                    null);
        }


    }


    private Cursor findJournalByID(long id){
        return mDb.query(JournalContract.JournalEntry.TABLE_NAME,
                null,
                JournalContract.JournalEntry._ID + "= " + id,
                null,
                null,
                null,
                null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_journal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_save){
            addToJournalList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
