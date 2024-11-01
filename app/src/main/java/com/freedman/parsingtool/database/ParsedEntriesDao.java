package com.freedman.parsingtool.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.freedman.parsingtool.tables.ParsedEntries;

import java.util.List;

@Dao
public interface ParsedEntriesDao {

    @Insert
    void createRow(ParsedEntries parsedEntries);

    @Query("SELECT * FROM parsedEntries WHERE tableName = :tableName")
    List<ParsedEntries> getUserSpecifiedTable(String tableName);

    @Query("SELECT tableName FROM parsedEntries")
    List<String> getTableNames();

    @Query("SELECT * FROM parsedEntries")
    List<ParsedEntries> getAllTable();


}
