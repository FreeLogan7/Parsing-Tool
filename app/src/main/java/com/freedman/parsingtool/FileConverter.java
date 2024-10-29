package com.freedman.parsingtool;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;

public class FileConverter {

    public void convert(DocumentFile documentFile){
        String fileType = getFileType(documentFile);
        checkFileType(fileType);
    }

    private String getFileType(DocumentFile documentFile) {
        if (documentFile == null) {
            throw new IllegalArgumentException("DocumentFile is Null!");
        }
        return documentFile.getType();
    }

    private void checkFileType(String fileType) {
        if (fileType.equals("text/comma-separated-values")) {
            //DO THIS
        } else if (fileType.equals("application/json")) {
            //DO THAT
        } else {
            throw new IllegalArgumentException("File Type Not Supported");
        }
    }
}
