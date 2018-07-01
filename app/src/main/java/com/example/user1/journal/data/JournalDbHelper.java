package com.example.user1.journal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user1.journal.data.JournalContract.JournalEntry;

/**
 * Created by user1 on 6/28/2018.
 */

public class JournalDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "journalList.db";
    private static final int DATABASE_VERSION = 1;

    public JournalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " +
                JournalEntry.TABLE_NAME + " ( " +
                JournalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                JournalEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                JournalEntry.COLUMN_BODY + " TEXT NOT NULL, " +
                JournalEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + JournalEntry.TABLE_NAME);
        onCreate(db);
    }
}
