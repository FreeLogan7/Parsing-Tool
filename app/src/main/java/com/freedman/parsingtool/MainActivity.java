package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private Button importData;
    ActivityResultLauncher<String> mGetContent;

    private FileConverter converter = new FileConverter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setupImport();
        importButtonClicked();

    }

    private void setViews() {
        importData = findViewById(R.id.import_data);
    }

    //On Import Button Click SELECT any file!
    private void importButtonClicked() {
        importData.setOnClickListener(v -> {
            CheckBox saveSelection = findViewById(R.id.checkbox_database);
            mGetContent.launch("*/*");
        });
    }

    private void setupImport() {
        mGetContent = registerForActivityResult(new GetContent(), uri -> {
            if (uri == null) throw new IllegalArgumentException("uri is Null!");
            //Display if file selected exists with name
            Toast.makeText(this, "Selected file: " + uri.toString(), Toast.LENGTH_SHORT).show();

            createContentResolver(uri);
        });
    }

    private void createContentResolver(Uri uri) {
        ContentResolver resolver = getContentResolver();
        try {
            converter.convert(uri, resolver);
        } catch (IllegalArgumentException | IOException | CsvValidationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("TOASTY-ERROR", e.getMessage(), e);
        }
    }


}


/*
Sources:
https://developer.android.com/training/data-storage/shared/documents-files
- How to open a file
- Deprecated - > leads to registerForActivityResult use instead (seen below this link)
- https://developer.android.com/training/basics/intents/result
https://www.baeldung.com/jackson-object-mapper-tutorial
- How to use Jackson

 */