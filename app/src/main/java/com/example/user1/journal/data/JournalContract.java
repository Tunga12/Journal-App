package com.example.user1.journal.data;

import android.provider.BaseColumns;

/**
 * Created by user1 on 6/28/2018.
 */

public class JournalContract {

    public static final class JournalEntry implements BaseColumns{

        public static final String TABLE_NAME = "journalList";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
