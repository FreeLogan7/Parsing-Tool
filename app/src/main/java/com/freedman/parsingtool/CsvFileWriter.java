package com.freedman.parsingtool;

import android.content.Context;

import com.freedman.parsingtool.filereader.FileWriterInterface;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvFileWriter implements FileWriterInterface {


    @Override
    public void write(Context context, List<Map<String, Object>> data, List<String> keys, String fileName) throws IOException {
        File file = new File(context.getFilesDir(), fileName+ ".csv");
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        List<String[]> table = new ArrayList<>();
        String[] keyData = keys.toArray(new String[keys.size()]);
        table.add(keyData);

        int rowIndex = 0;
        for (Map<String, Object> row : data) {
            String[] rowData = new String[data.get(rowIndex).size()];
            int colIndex = 0;
            for (String key : row.keySet()) {
                rowData[colIndex] = row.get(key).toString();
                colIndex++;
            }
            rowIndex++;
            table.add(rowData);
        }

        writer.writeAll(table);
        writer.close();


    }


}


//        for(int rowIndex=0; rowIndex<data.toArray().length; rowIndex++){
//
//            for (int colIndex =0; colIndex < data.get(rowIndex).size(); colIndex++){
//                    rowData[colIndex] =
//            }


/*
JSON
May have changing Keys
 */

//    private void storeInDatabase(List<Map<String, Object>> data) {
//        List<String> keys = new ArrayList<>();
//        int rowNumber = 0;
//        for (Map<String, Object> row : data) {
//            for (String key : row.keySet()) {
//                Object value = row.get(key);
//                //Database(rowNumber, key, value); //int, String, Object
//            }
//            rowNumber++;
//        }
//    }

//
//
//public class CsvFileReader implements FileReader {
//
//    @Override
//    public List<Map<String, Object>> read(InputStream inputStream) throws IOException {
//        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
//        //Create same parsed data as JSON
//        List<Map<String, Object>> content = new ArrayList<>();
//
//        try {
//            List<String[]> table = reader.readAll();
//            String[] keys = table.get(0);
//
//            for (int rowIndex=1; rowIndex < table.size(); rowIndex++){
//                String[] row = table.get(rowIndex);
//
//                isValidRow(row, keys, rowIndex);
//
//                Map<String, Object> entry = new HashMap<>();
//                for (int colIndex=0; colIndex < row.length; colIndex++){
//                    String key = keys[colIndex];
//                    String value = row[colIndex];
//                    entry.put(key, value);
//                }
//                content.add(entry);
//            }
//        } catch (CsvException e) {
//            throw new RuntimeException(e);
//        }
//        return content;
//    }
//
//    //Confirms Valid Row Length
//    private static void isValidRow(String[] row, String[] keys, int rowIndex) throws IOException {
//        if (row.length> keys.length){
//            throw new IOException("CSV format error: row length invalid! Row: " + rowIndex + "Column: Out of bounds: " + row.length);
//        }
//    }
//
//
//}