package com.freedman.parsingtool.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


import java.io.Serializable;

@Entity(
        tableName = "parsedEntries",
        primaryKeys = {"row_number", "key"}
)
public class ParsedEntries implements Serializable {
    @ColumnInfo(name = "row_number")
    private int rowNumber;
    @NonNull
    private String key;
    private String value = "";
    private String tableName ="";


    public ParsedEntries(int rowNumber, @NonNull String key, String value, String tableName) {
        this.rowNumber = rowNumber;
        this.key = key;
        this.value = value;
        this.tableName = tableName;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public String getRowNumberAsString() {return String.valueOf(rowNumber);}

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getTableName() {
        return tableName;
    }

}


