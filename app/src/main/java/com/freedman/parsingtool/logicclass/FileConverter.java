package com.freedman.parsingtool.logicclass;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.widget.CheckBox;

import com.freedman.parsingtool.database.ParsedEntriesDao;
import com.freedman.parsingtool.database.ParsedEntriesDatabase;
import com.freedman.parsingtool.filereader.CsvFileReader;
import com.freedman.parsingtool.filereader.FileReader;
import com.freedman.parsingtool.filereader.JsonFileReader;
import com.freedman.parsingtool.filewriter.CsvFileWriter;
import com.freedman.parsingtool.filewriter.FileWriterInterface;
import com.freedman.parsingtool.filewriter.JsonFileWriter;
import com.freedman.parsingtool.tables.ParsedEntries;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileConverter {

    //READERS
    private final JsonFileReader jsonReader = new JsonFileReader();
    private final CsvFileReader csvFileReader = new CsvFileReader();

    //WRITERS
    private final JsonFileWriter jsonWriter = new JsonFileWriter();
    private final CsvFileWriter csvWriter = new CsvFileWriter();

    //INTERFACE
    private final DisplayFileCreated displayInterface;

    //DATABASE
    public ParsedEntriesDao parsedEntriesDao;

    //Constructor
    public FileConverter(DisplayFileCreated displayInterface) {
        this.displayInterface = displayInterface;
    }

    //MAIN METHOD
    public void convert(
            Uri uri,
            ContentResolver resolver,
            Context context,
            CheckBox checkboxDatabase,
            CheckBox convertToJson,
            String fileName) throws IOException, CsvValidationException {

        String mimeType = getMimeType(uri, resolver);
        InputStream inputStream = resolver.openInputStream(uri);
        FileReader reader = getFileReader(mimeType);
        List<Map<String, Object>> data = reader.read(inputStream);
        List<String> keys = getKeys(data);
        List<String[]> table = convertDataToTable(keys, data);

        if (!checkboxDatabase.isChecked()) {
            FileWriterInterface writer = getFileWriter(convertToJson);
            writer.write(context, data, table, fileName);
            displayInterface.onFileCreate(fileName);
        } else {
            saveToDatabase(table, context, fileName);
        }
    }

    private String getMimeType(Uri uri, ContentResolver resolver) {
        if (resolver == null) throw new IllegalArgumentException("Mime Type is Null");
        return resolver.getType(uri);
    }

    private FileReader getFileReader(String mimeType) {
        if (mimeType.equals("text/csv") || mimeType.equals("text/comma-separated-values")) {
            return csvFileReader;
        } else if (mimeType.equals("application/json")) {
            return jsonReader;
        }
        throw new IllegalArgumentException("File Type Not Supported");
    }

    private List<String> getKeys(List<Map<String, Object>> data) {
        List<String> keys = new ArrayList<>();
        for (Map<String, Object> row : data) {
            for (String colIndex : row.keySet()) {
                if (!keys.contains(colIndex)) {
                    keys.add(colIndex);
                }
            }
        }
        return keys;
    }

    private List<String[]> convertDataToTable(List<String> keys, List<Map<String, Object>> data) {
        List<String[]> table = new ArrayList<>();
        String[] keyData = keys.toArray(new String[keys.size()]);

        table.add(keyData);

        for (Map<String, Object> row : data) {
            String[] rowData = new String[keys.size()];
            for (int colIndex = 0; colIndex < keys.size(); colIndex++) {
                String key = keys.get(colIndex);
                Object value = row.get(key);
                if (value != null) {
                    rowData[colIndex] = value.toString();
                } else rowData[colIndex] = "";
            }
            table.add(rowData);
        }
        return table;
    }

    private void saveToDatabase(List<String[]> table, Context context, String fileName) {
        parsedEntriesDao = ParsedEntriesDatabase.getDatabase(context).parsedEntriesDao();

        Thread thread = new Thread(() -> {
            for (int rowIndex =1; rowIndex<table.size(); rowIndex++){
                for (int colIndex=0; colIndex<table.get(KEYROW).length; colIndex++){
                    String key = table.get(KEYROW)[colIndex];
                    String value = table.get(rowIndex)[colIndex];
                    parsedEntriesDao.createRow(new ParsedEntries(rowIndex, key, value, fileName));
                }}});
        thread.start();}

    private FileWriterInterface getFileWriter(CheckBox convertToJson) {
        if (convertToJson.isChecked()) {
            return jsonWriter;
        }
        return csvWriter;
    }

    public interface DisplayFileCreated {
        void onFileCreate(String fileName);
    }

    public static final int KEYROW = 0;
}