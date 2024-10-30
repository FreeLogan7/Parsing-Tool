package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.IOException;
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
            if (uri == null) return;
            //Display if file selected exists with name
            Toast.makeText(this, "Selected file: " + uri.toString(), Toast.LENGTH_SHORT).show();
            try {
                converter.convert(uri);
            } catch (IllegalArgumentException | IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }





}


/*
Sources:
https://developer.android.com/training/data-storage/shared/documents-files
- How to open a file
- Deprecated - > leads to registerForActivityResult use instead (seen below this link)
- https://developer.android.com/training/basics/intents/result


 */