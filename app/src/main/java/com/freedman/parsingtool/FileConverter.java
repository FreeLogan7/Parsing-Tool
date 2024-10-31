package com.freedman.parsingtool;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

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

    public void convert(Uri uri, ContentResolver resolver) throws IOException, CsvValidationException {
        String mimeType = getMimeType(uri, resolver);
        InputStream inputStream = resolver.openInputStream(uri);
        FileReader reader = getFileReader(mimeType);
        List<Map<String, Object>> data = reader.read(inputStream);



//        getKeys(data);
        //storeInDatabase(data);

    }

    private List<String> getKeys(List<Map<String, Object>> data) {
        List<String> keys = new ArrayList<>();
        for (Map<String, Object> row : data) {
            for (String column: row.keySet()){
                if (!keys.contains(column)){keys.add(column);}
            }
        }
        return keys;
    }


    private void storeInDatabase(List<Map<String, Object>> data) {
        List<String> keys = new ArrayList<>();
        int rowNumber = 0;
        for (Map<String, Object> row : data) {
            for (String key : row.keySet()) {
                Object value = row.get(key);
                //Database(rowNumber, key, value); //int, String, Object
            }
            rowNumber++;
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
