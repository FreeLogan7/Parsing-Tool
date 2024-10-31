package com.freedman.parsingtool.filereader;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileWriterInterface {
    void write(Context context, List<Map<String, Object>> data) throws IOException;
}
