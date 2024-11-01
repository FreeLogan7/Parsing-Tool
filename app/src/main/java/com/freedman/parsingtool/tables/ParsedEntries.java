package com.freedman.parsingtool.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "parsedEntries")
public class ParsedEntries implements Serializable {
    @ColumnInfo(name = "row_number")
    @PrimaryKey
    private int rowNumber;
    @PrimaryKey
    private String key;
    private String value = "";


    public ParsedEntries(int rowNumber, String key, String value) {
        this.rowNumber = rowNumber;
        this.key = key;
        this.value = value;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


