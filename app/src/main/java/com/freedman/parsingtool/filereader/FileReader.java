package com.freedman.parsingtool.filereader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FileReader {
    List<Map<String, Object>> read(InputStream inputStream) throws IOException;
}
