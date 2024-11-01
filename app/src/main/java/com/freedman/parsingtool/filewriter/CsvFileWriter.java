package com.freedman.parsingtool.filewriter;

import android.content.Context;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvFileWriter implements FileWriterInterface {


    @Override
    public void write(Context context, List<Map<String, Object>> data, List<String[]> table, String fileName) throws IOException {
        File file = new File(context.getFilesDir(), fileName+ ".csv");
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        writer.writeAll(table);
        writer.close();
    }
}