package com.freedman.parsingtool;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freedman.parsingtool.database.ParsedEntriesDao;
import com.freedman.parsingtool.database.ParsedEntriesDatabase;
import com.freedman.parsingtool.tables.ParsedEntries;

import java.util.ArrayList;
import java.util.List;

public class databaseInfoActivity extends AppCompatActivity {

    public ParsedEntriesDao parsedEntriesDao;

    List<ParsedEntries> list;
    List<String> tableNames;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_database);

        parsedEntriesDao = ParsedEntriesDatabase.getDatabase(this).parsedEntriesDao();


        new Thread(()->{

            this.tableNames = parsedEntriesDao.getTableNames();
            this.list = parsedEntriesDao.getAllTable();
            this.list = parsedEntriesDao.getAllTable();
        }).start();








    }
}
