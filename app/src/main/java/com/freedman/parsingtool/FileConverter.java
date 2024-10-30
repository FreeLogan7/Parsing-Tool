package com.freedman.parsingtool;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.documentfile.provider.DocumentFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Map;

public class FileConverter {

    public void convert(Uri uri, ContentResolver resolver) throws IOException {
        String mimeType = getMimeType(uri,resolver);
        getFileReader(mimeType);



        readFileWithContentResolver(uri, resolver);




        // This stuff below should go in a JsonFileReader
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map content = objectMapper.readValue(file, Map.class);
//        Log.e("yessir", content.toString() );

    }

    private String getMimeType(Uri uri, ContentResolver resolver) {
        if (resolver == null) throw new IllegalArgumentException("File Type is Null");
        return resolver.getType(uri);
    }

    private void getFileReader(String mimeType){
        if (mimeType.equals("text/csv") || mimeType.equals("text/comma-separated-values")) {
            //GET CSV Reader
        } else if (mimeType.equals("application/json")) {
            //GET JSON Reader
        } else throw new IllegalArgumentException("File Type Not Supported");

    }

    private void readFileWithContentResolver(Uri uri, ContentResolver resolver) throws FileNotFoundException {
        InputStream inputStream = resolver.openInputStream(uri);

    }





}
