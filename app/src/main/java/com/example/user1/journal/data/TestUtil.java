package com.example.user1.journal.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(JournalContract.JournalEntry.COLUMN_TITLE, "John");
        cv.put(JournalContract.JournalEntry.COLUMN_BODY, "heloo world");
        list.add(cv);

        cv = new ContentValues();
        cv.put(JournalContract.JournalEntry.COLUMN_TITLE, "Joy");
        cv.put(JournalContract.JournalEntry.COLUMN_BODY, "my journal");
        list.add(cv);

        cv = new ContentValues();
        cv.put(JournalContract.JournalEntry.COLUMN_TITLE, "Grace");
        cv.put(JournalContract.JournalEntry.COLUMN_BODY, "today was amazing");
        list.add(cv);

        cv = new ContentValues();
        cv.put(JournalContract.JournalEntry.COLUMN_TITLE, "Jessica");
        cv.put(JournalContract.JournalEntry.COLUMN_BODY, "i have got no light!! tooo bad.");
        list.add(cv);

        cv = new ContentValues();
        cv.put(JournalContract.JournalEntry.COLUMN_TITLE, "Tim");
        cv.put(JournalContract.JournalEntry.COLUMN_BODY, "doing my homework...");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (JournalContract.JournalEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(JournalContract.JournalEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}