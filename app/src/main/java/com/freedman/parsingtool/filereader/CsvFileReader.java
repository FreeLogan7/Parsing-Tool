package com.freedman.parsingtool.filereader;

import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFileReader implements FileReader {

    @Override
    public List<Map<String, Object>> read(InputStream inputStream) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        //Create same parsed data as JSON
        List<Map<String, Object>> content = new ArrayList<>();

        try {
             List<String[]> table = reader.readAll();
             String[] keys = table.get(0);
             for (int rowIndex=1; rowIndex < table.size(); rowIndex++){
                 Map<String, Object> entry = new HashMap<>();
                 String[] row = table.get(rowIndex);
                 for (int colIndex=0; colIndex < row.length; colIndex++){
                     String key = keys[colIndex];
                     String value = row[colIndex];
                     entry.put(key, value);
                 }
                 content.add(entry);
             }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

}