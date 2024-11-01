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

        for (Map<String,Object> row : data){
            String[] rowData = new String[keys.size()];
            for (int colIndex = 0; colIndex<keys.size(); colIndex++){
                String key = keys.get(colIndex);
                Object value = row.get(key);
               if (value != null){
                   rowData[colIndex] = value.toString();
               }else rowData[colIndex] = "";
            }
            table.add(rowData);
        }
        writer.writeAll(table);
        writer.close();
    }
}