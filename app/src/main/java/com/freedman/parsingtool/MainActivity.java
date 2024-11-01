package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Log;
import android.view.View;
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
    CheckBox saveFile;
    CheckBox saveDatabase;
    CheckBox convertToJson;
    CheckBox convertToCsv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setViews();
        setupImport();
        importButtonClicked();
        saveButtons();
        convertButtons();


    }

    private void convertButtons() {
        convertToJson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                convertToCsv.setChecked(false);
            }
        });
        convertToCsv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                convertToJson.setChecked(false);
            }
        });
    }

    private void saveButtons() {
        saveFile.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                saveDatabase.setChecked(false);
                convertToCsv.setVisibility(View.VISIBLE);
                convertToJson.setVisibility(View.VISIBLE);
            } else if (!saveFile.isChecked()) {
                convertToCsv.setVisibility(View.INVISIBLE);
                convertToJson.setVisibility(View.INVISIBLE);
                clearConvertCheckbox();
            }
        });

        saveDatabase.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                saveFile.setChecked(false);
                convertToCsv.setVisibility(View.INVISIBLE);
                convertToJson.setVisibility(View.INVISIBLE);
                clearConvertCheckbox();
            }

        });
    }

    private void clearConvertCheckbox() {
        convertToJson.setChecked(false);
        convertToCsv.setChecked(false);
    }


    private void setViews() {
        importData = findViewById(R.id.import_data);
        saveFile = findViewById(R.id.checkbox_file);
        saveDatabase = findViewById(R.id.checkbox_database);
        convertToJson = findViewById(R.id.checkbox_json);
        convertToCsv = findViewById(R.id.checkbox_csv);
    }

    //On Import Button Click SELECT any file!
    private void importButtonClicked() {
        importData.setOnClickListener(v -> {
            mGetContent.launch("*/*");
        });
    }

    //Consider Discussing Security validation for uri
    private void setupImport() {
        mGetContent = registerForActivityResult(new GetContent(), uri -> {
            if (uri == null) throw new IllegalArgumentException("uri is Null!");
            Toast.makeText(this, "Selected file: " + uri.toString(), Toast.LENGTH_SHORT).show();

            createContentResolver(uri);
        });
    }

    private void createContentResolver(Uri uri) {
        ContentResolver resolver = getContentResolver();
        try {
            //In Future Find solution to avoid sending 'this'

            converter.convert(uri, resolver, this, saveFile, saveDatabase, convertToJson, convertToCsv);
        } catch (IllegalArgumentException | IOException | CsvValidationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("TOASTY-ERROR", e.getMessage(), e);
        }
    }

}

