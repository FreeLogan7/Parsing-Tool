package com.freedman.parsingtool;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.widget.CheckBox;

import com.freedman.parsingtool.filereader.CsvFileReader;
import com.freedman.parsingtool.filereader.FileReader;
import com.freedman.parsingtool.filereader.JsonFileReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileConverter {

    private JsonFileReader jsonReader = new JsonFileReader();
    private CsvFileReader csvFileReader = new CsvFileReader();

    private JsonFileWriter jsonWriter = new JsonFileWriter();
    private CsvFileWriter csvWriter = new CsvFileWriter();

    public void convert(Uri uri,
                        ContentResolver resolver,
                        Context context,
                        CheckBox saveFile,
                        CheckBox saveDatabase,
                        CheckBox convertToJson,
                        CheckBox convertToCsv) throws IOException, CsvValidationException {
        String mimeType = getMimeType(uri, resolver);
        InputStream inputStream = resolver.openInputStream(uri);
        FileReader reader = getFileReader(mimeType);
        List<Map<String, Object>> data = reader.read(inputStream);
//        FileWriterInterface writer = getFileWriter(convertToJson, convertToCsv);


        List<String> keys = getKeys(data);
        csvWriter.write(context, data, keys);
    }

//    private FileWriterInterface getFileWriter(CheckBox convertToJson, CheckBox convertToCsv) {
//    if (convertToJson.isChecked()){
//        csvWriter.write();
//    }
//    }


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

    private void storeInDatabase(List<Map<String, Object>> data) {
        List<String> keys = new ArrayList<>();
        int rowIndex = 0;
        for (Map<String, Object> row : data) {
            for (String key : row.keySet()) {
                Object value = row.get(key);
                //Database(rowIndex, key, value); //int, String, Object
            }
            rowIndex++;
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
}


//        Log.e("DATA-ISUS", "convert: "+data );
//        dataConversion(data);
//Choose to SAVE to DATABASE
//Data manipulation

//Choose CONVERSION
//IF TO JSON -> data converts!
//IF to CSV -> data manipulation first

//        jsonWriter.write(context,data,keys);


//storeInDatabase(data);



//    private void dataConversion(List<Map<String, Object>> data){
//
//
//    }