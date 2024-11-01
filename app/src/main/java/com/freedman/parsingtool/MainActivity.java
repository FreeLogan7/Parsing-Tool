package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements FileConverter.DisplayFileCreated {

    private Button importData;
    private EditText userFileName;
    private CheckBox checkboxFile;
    private CheckBox checkboxDatabase;
    private CheckBox checkboxConvertToJson;
    private CheckBox checkboxConvertToCsv;

    ActivityResultLauncher<String> mGetContent;
    private FileConverter converter = new FileConverter(this);

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


    private void setViews() {
        importData = findViewById(R.id.import_data);
        checkboxFile = findViewById(R.id.checkbox_file);
        checkboxDatabase = findViewById(R.id.checkbox_database);
        checkboxConvertToJson = findViewById(R.id.checkbox_json);
        checkboxConvertToCsv = findViewById(R.id.checkbox_csv);
        userFileName = findViewById(R.id.edit_text_file_name);
    }

    //Consider Discussing Security validation for uri
    private void setupImport() {
        mGetContent = registerForActivityResult(new GetContent(), uri -> {
            if (uri == null) throw new IllegalArgumentException("uri is Null!");
            Toast.makeText(this, "Selected File!", Toast.LENGTH_SHORT).show();
            createContentResolver(uri);
        });
    }

    //On Import Button Click SELECT any file!
    private void importButtonClicked() {
        importData.setOnClickListener(v -> {
            if (userFileName == null) throw new IllegalArgumentException("File Name is Null");
            else if (userFileName.getText().toString().contains(" "))
                throw new IllegalArgumentException("File name cannot contain spaces");
            else if (conversionNotSelected()) throw new IllegalArgumentException("Neither JSON NOR CSV were selected");
            else mGetContent.launch("*/*");
        });
    }

    private boolean conversionNotSelected() {
        return !checkboxConvertToJson.isChecked() && !checkboxConvertToCsv.isChecked();
    }

    private void createContentResolver(Uri uri) {
        ContentResolver resolver = getContentResolver();
        WeakReference<Activity> activityRef = new WeakReference<>(this);
        try {
            converter.convert(
                    uri,
                    resolver,
                    activityRef.get(),
                    checkboxDatabase,
                    checkboxConvertToJson,
                    userFileName.getText().toString());

        } catch (IllegalArgumentException | IOException | CsvValidationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("TOASTY-ERROR", e.getMessage(), e);
        }
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

    private void clearConvertCheckbox() {
        checkboxConvertToJson.setChecked(false);
        checkboxConvertToCsv.setChecked(false);
    }


    @Override
    public void onFileCreate(String fileName) {
        Toast.makeText(this, "File has been Created!", Toast.LENGTH_LONG).show();
    }
}

