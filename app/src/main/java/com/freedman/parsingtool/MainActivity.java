package com.freedman.parsingtool;

import static androidx.activity.result.contract.ActivityResultContracts.GetContent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button importData;
    ActivityResultLauncher<String> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setupImport();
        //On Import Button Click SELECT any file!
        importData.setOnClickListener(v -> {
            mGetContent.launch("*/*");
        });

    }

    private void setViews() {
        importData = findViewById(R.id.import_data);
    }


    private void setupImport() {
        mGetContent = registerForActivityResult(new GetContent(), uri -> {
            if (uri != null) {
                //Display if file selected exists with name
                Toast.makeText(MainActivity.this, "Selected file: " + uri.toString(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", uri.toString());

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