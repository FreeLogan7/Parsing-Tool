package com.freedman.parsingtool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.freedman.parsingtool.adapter.DataAdapter;
import com.freedman.parsingtool.database.ParsedEntriesDao;
import com.freedman.parsingtool.database.ParsedEntriesDatabase;
import com.freedman.parsingtool.logicclass.FileConverter;
import com.freedman.parsingtool.tables.ParsedEntries;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class databaseInfoActivity extends AppCompatActivity implements DataAdapter.sendMeInfoListener, FileConverter.DisplayFileCreated {

    public ParsedEntriesDao parsedEntriesDao;
    List<ParsedEntries> parsedEntriesList;
    List<String> tableNames;
    RecyclerView recyclerView;
    EditText editTextReload;
    DataAdapter adapter;
    Button buttonReload;
    CheckBox checkboxConvertToJson;
    CheckBox checkboxConvertToCsv;
    CheckBox checkboxDatabase;


    private FileConverter converter = new FileConverter(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_database);
        //Placing recyclerView and adapter here, work better. issues with thread occur if not?
        recyclerView = findViewById(R.id.recycler_view_data);
        adapter = new DataAdapter(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        performIdCheck(id);
        setViews();
        buttonReloadClicked();
        convertButtons();
    }

    private void setViews() {
        editTextReload = findViewById(R.id.edit_text_search_file);
        buttonReload = findViewById(R.id.button_reload_confirm);
        checkboxConvertToJson = findViewById(R.id.checkbox_json_database);
        checkboxConvertToCsv = findViewById(R.id.checkbox_csv_database);
        checkboxDatabase = findViewById(R.id.checkbox_database_invisible);
    }

    private void buttonReloadClicked() {
        buttonReload.setOnClickListener(v -> {
            if (!checkboxConvertToJson.isChecked() && !checkboxConvertToCsv.isChecked()) return;
            String userSelectedTable = editTextReload.getText().toString();
            getSpecificTable(userSelectedTable);
        });
    }

    private void getSpecificTable(String userSelectedTable) {
        Thread thread = new Thread(() -> {
            List<ParsedEntries> parsedEntriesList = parsedEntriesDao.getUserSpecifiedTable(userSelectedTable);
            runOnUiThread(() -> {
                this.parsedEntriesList = parsedEntriesList;
                if (this.parsedEntriesList != null) {
                    moveDataOutOfThread(this.parsedEntriesList, userSelectedTable);
                    //I have had a lot of issues with thread, which is why i send the variable around
                }
            });
        });
        thread.start();
    }

    //WeakReference<Activity> activityRef = new WeakReference<>(this); for context if getContext Doesn't work
    private void moveDataOutOfThread(List<ParsedEntries> specifiedData, String userSelectedTable) {
        int keyLength = getKeyLength(specifiedData);

        Map<String, Object> store = new HashMap<String, Object>();
        List<String[]> table = new ArrayList<>();
        String[] stored = new String[keyLength];
        int index =0;
        while (index < specifiedData.size()) {
            for (int column = 0; column < keyLength; column++) {
                stored[column] = specifiedData.get(column).getKey();
            }
            index++;
        }

//converter.convertSecondHalf(data, checkboxDatabase, checkboxConvertToJson, getContext(),userSelectedTable);

    }

    private int getKeyLength(List<ParsedEntries> specifiedData) {
        int boundary = specifiedData.get(0).getRowNumber();
        int keyLength = 0;
        while (boundary == specifiedData.get(keyLength).getRowNumber()) {
            keyLength++;
        }
        return keyLength;
    }

    //CAN Try in future move this to interface (used in main as well)
    private void convertButtons() {
        checkboxConvertToJson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxConvertToCsv.setChecked(false);
            }
        });
        checkboxConvertToCsv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxConvertToJson.setChecked(false);
            }
        });
    }

    private void performIdCheck(int id) {
        parsedEntriesDao = ParsedEntriesDatabase.getDatabase(this).parsedEntriesDao();
        switch (id) {
            case (-1): {
                break;
            }
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
        Thread thread = new Thread(() -> {
            List<String> tableNames = parsedEntriesDao.getTableNames();
            runOnUiThread(() -> {
                this.tableNames = tableNames;
                if (this.tableNames != null) {
                    List<ParsedEntries> adapterCompatibleArrayNames = new ArrayList<>();
                    for (int index = 0; index < this.tableNames.size(); index++) {
                        //Workaround for incompatible adapter type, without making new adapter
                        adapterCompatibleArrayNames.add(new ParsedEntries(0, "null", "null", this.tableNames.get(index)));
                    }
                    adapter.setParsedEntries(adapterCompatibleArrayNames);
                    attachAdapter(adapter);
                }
            });
        });
        thread.start();
    }

    private void getAllTable(ParsedEntriesDao parsedEntriesDao) {
        Thread thread = new Thread(() -> {
            List<ParsedEntries> parsedEntriesList = parsedEntriesDao.getAllTable();
            runOnUiThread(() -> {
                this.parsedEntriesList = parsedEntriesList;
                if (this.parsedEntriesList != null) {
                    adapter.setParsedEntries(this.parsedEntriesList);
                    attachAdapter(adapter);
                }
            });
        });
        thread.start();
    }

    public void attachAdapter(DataAdapter adapter) {
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    public void sendMeTableName(String name) {
    }

    @Override
    public void onFileCreate(String fileName) {

    }
}