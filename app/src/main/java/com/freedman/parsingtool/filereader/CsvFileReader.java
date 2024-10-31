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
        List<Map<String, Object>> content = new ArrayList<>();

        try {
             List<String[]> table = reader.readAll();
             String[] keys = table.get(0);
             for (int rowIndex=1; rowIndex < table.size(); rowIndex++){
                 HashMap<String, Object> entry = new HashMap<>();
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


//    private List<List<String>> getTable(InputStream inputStream) throws IOException {
//        List<List<String>> table = new ArrayList<>();
//        reader.readAll()
//        try {
//            String[] rowData;
//            while ((rowData = reader.readNext()) != null) {
//                //Read Each Row
//                Log.d("CSVReader", "Row: " + String.join(", ", rowData));
//
//                return null;
//            }
//        } catch (CsvValidationException e) {
//            throw new IOException(e);
//        }
//    }

//            ArrayList<String[]> csvData = new ArrayList<>();
//              Log.d("CSV Data", "Row: " + nextLine[0]);
//                csvData.add(rowData);
