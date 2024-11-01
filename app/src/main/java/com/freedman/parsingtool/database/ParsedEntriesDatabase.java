package com.freedman.parsingtool.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.freedman.parsingtool.tables.ParsedEntries;

@Database(entities = {ParsedEntries.class}, version = 1)
public abstract class ParsedEntriesDatabase extends RoomDatabase {
    public abstract ParsedEntriesDao parsedEntriesDao();

    private static volatile ParsedEntriesDatabase INSTANCE;

    public static ParsedEntriesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ParsedEntriesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ParsedEntriesDatabase.class, "parsed-entries-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}