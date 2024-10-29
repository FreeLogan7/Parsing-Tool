package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

public class MainActivity extends AppCompatActivity {

    private Button importData;
    private Uri selectedUri;
    ActivityResultLauncher<String> mGetContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        importButtonClicked();
        setupImport();




    }

    private void setViews(){
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
            if (uri != null) {
                selectedUri = uri;
                //Display if file selected exists with name
                Toast.makeText(this, "Selected file: " + uri.toString(), Toast.LENGTH_SHORT).show();

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