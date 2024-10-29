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

public class MainActivity extends AppCompatActivity {

    private Button importData;
    private Uri selectedUri;
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
            selectedUri = uri;
            //Display if file selected exists with name
            Toast.makeText(this, "Selected file: " + uri.toString(), Toast.LENGTH_SHORT).show();
            try {
                String type = getFileDetails(selectedUri);
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileDetails(Uri uri) {
        DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
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
            Toast.makeText(this, "Not Supported File Type!", Toast.LENGTH_LONG).show();
        }
    }


}


//    private void dataHolderType() {
//        if (saveSelection.isChecked()){
//            //Save to Database
//        }
//        else{
//            //Save Locally
//        }
//    }

/*
Sources:
https://developer.android.com/training/data-storage/shared/documents-files
- How to open a file
- Deprecated - > leads to registerForActivityResult use instead (seen below this link)
- https://developer.android.com/training/basics/intents/result


 */