package com.freedman.parsingtool;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.freedman.parsingtool.adapter.DataAdapter;
import com.freedman.parsingtool.database.ParsedEntriesDao;
import com.freedman.parsingtool.database.ParsedEntriesDatabase;
import com.freedman.parsingtool.tables.ParsedEntries;

import java.util.ArrayList;
import java.util.List;

public class databaseInfoActivity extends AppCompatActivity implements DataAdapter.sendMeInfoListener {

    public ParsedEntriesDao parsedEntriesDao;
    List<ParsedEntries> parsedEntriesList;
    List<String> tableNames;
    RecyclerView recyclerView;
    DataAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_database);
        //Setting recyclerView and adapter here work better? issues with thread occur if not.
         recyclerView = findViewById(R.id.recycler_view_data);
         adapter = new DataAdapter(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        performIdCheck(id);



    }

    private void performIdCheck(int id) {
        parsedEntriesDao = ParsedEntriesDatabase.getDatabase(this).parsedEntriesDao();
        switch (id){
            case(-1): {break;}
            case (1): { //sent from View Database Button
                getAllTable(parsedEntriesDao);
                break;
            }
            case (2): { //sent from Reload Button
                getTableNamesToReload(parsedEntriesDao);
                break;
            }
        }
    }

    private void getTableNamesToReload(ParsedEntriesDao parsedEntriesDao) {
        new Thread(() -> {
            List<String> tableNames = parsedEntriesDao.getTableNames();
            runOnUiThread(() -> {
                this.tableNames = tableNames;
                if(this.tableNames != null){
                    List<ParsedEntries> adapterCompatibleArrayNames = new ArrayList<>();
                    for(int index = 0; index<this.tableNames.size(); index++){
                        //Workaround for incompatible adapter type, without making new adapter
                        adapterCompatibleArrayNames.add( new ParsedEntries(0,"null", "null", this.tableNames.get(index)));
                    }
                    adapter.setParsedEntries(adapterCompatibleArrayNames);
                    attachAdapter(adapter);
                }
            });
        }).start();
    }

    private void getAllTable(ParsedEntriesDao parsedEntriesDao) {
        new Thread(() -> {
            List<ParsedEntries> parsedEntriesList = parsedEntriesDao.getAllTable();
            runOnUiThread(() -> {
                this.parsedEntriesList = parsedEntriesList;
                if(this.parsedEntriesList != null){
                    adapter.setParsedEntries(this.parsedEntriesList);
                    attachAdapter(adapter);
                }
            });
        }).start();
    }

    public void attachAdapter(DataAdapter adapter) {
        this.recyclerView.setAdapter(adapter);
    }

    public void sendMeTableName(String name){

    }

}