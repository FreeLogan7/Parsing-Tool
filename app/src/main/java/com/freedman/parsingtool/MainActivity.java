package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button importData;
    ActivityResultLauncher<String> mGetContent;
    private FileConverter converter = new FileConverter();
    CheckBox checkboxFile;
    CheckBox checkboxDatabase;
    CheckBox checkboxConvertToJson;
    CheckBox checkboxConvertToCsv;


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
        checkboxConvertToJson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxConvertToCsv.setChecked(false);
            }
        });
        checkboxConvertToCsv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxConvertToJson.setChecked(false);
            }
        });
    }

    private void saveButtons() {
        checkboxFile.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxDatabase.setChecked(false);
                checkboxConvertToCsv.setVisibility(View.VISIBLE);
                checkboxConvertToJson.setVisibility(View.VISIBLE);
            } else if (!checkboxFile.isChecked()) {
                checkboxConvertToCsv.setVisibility(View.INVISIBLE);
                checkboxConvertToJson.setVisibility(View.INVISIBLE);
                clearConvertCheckbox();
            }
        });

        checkboxDatabase.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxFile.setChecked(false);
                checkboxConvertToCsv.setVisibility(View.INVISIBLE);
                checkboxConvertToJson.setVisibility(View.INVISIBLE);
                clearConvertCheckbox();
            }

        });
    }

    private void clearConvertCheckbox() {
        checkboxConvertToJson.setChecked(false);
        checkboxConvertToCsv.setChecked(false);
    }


    private void setViews() {
        importData = findViewById(R.id.import_data);
        checkboxFile = findViewById(R.id.checkbox_file);
        checkboxDatabase = findViewById(R.id.checkbox_database);
        checkboxConvertToJson = findViewById(R.id.checkbox_json);
        checkboxConvertToCsv = findViewById(R.id.checkbox_csv);
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

            converter.convert(uri, resolver, this, checkboxDatabase, checkboxConvertToJson);
        } catch (IllegalArgumentException | IOException | CsvValidationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("TOASTY-ERROR", e.getMessage(), e);
        }
    }

}

