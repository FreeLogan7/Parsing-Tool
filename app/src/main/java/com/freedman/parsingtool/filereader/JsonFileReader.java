package com.freedman.parsingtool.filereader;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonFileReader implements FileReader {

    @Override
    public List<Map<String, Object>> read(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //Handle single value as array to allow reader to function
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        return mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>(){});
    }
}

