package com.freedman.parsingtool.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.freedman.parsingtool.tables.ParsedEntries;

@Dao
public interface ParsedEntriesDao {

    @Insert
    void createRow(ParsedEntries parsedEntries);
}
