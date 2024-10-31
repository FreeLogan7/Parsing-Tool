package com.freedman.parsingtool;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedman.parsingtool.filereader.FileWriterInterface;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonFileWriter implements FileWriterInterface {

    @Override
    public void write(Context context, List<Map<String, Object>> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(new File(context.getFilesDir(),"output.json"), data);
    }
}
