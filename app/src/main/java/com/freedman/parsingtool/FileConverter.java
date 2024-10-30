package com.freedman.parsingtool;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileConverter {

    public void convert(Uri uri) throws IOException {
        Log.e("HEEEEEEYY", uri.getPath());
        File file = new File(uri.getPath());
//        String fileType = getFileType(file);
//        getFileReader(fileType);

        // This stuff below should go in a JsonFileReader


    }


    private String getFileType(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("DocumentFile is Null!");
        }
        return Files.probeContentType(file.toPath());
    }

    private void getFileReader(String fileType) {
        if (fileType.equals("text/comma-separated-values")) {
            //DO THIS
        } else if (fileType.equals("application/json")) {
            //DO THAT
        } else {
            throw new IllegalArgumentException("File Type Not Supported");
        }
    }
}
