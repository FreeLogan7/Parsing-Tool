//package com.freedman.parsingtool;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import android.content.Context;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//public class JsonFileWriterTest {
//
//    private ObjectMapper mapper = mock(ObjectMapper.class);
//    private Context context = mock(Context.class);
//    private JsonFileWriter sut = new JsonFileWriter(mapper);
//
//    private List<Map<String, Object>> exampleData = List.of(
//            Map.of("first", "firstValue")
//    );
//    private List<String> exampleKeys = List.of("first");
//
//
//
////    @BeforeEach
////    public void setup(){
////
////    }
//
//    @Test
//    public void write_given_context_and_data_calls_object_mapper() throws IOException {
//        sut.write(context, exampleData, exampleKeys);
//        verify(context).getFilesDir();
//        verify(mapper).writeValue(any(File.class), eq(exampleData));
//        // assertEquals(expected, file.name)
//    }
//
//
//
//}
//
//
////put back in writer
////private ObjectMapper mapper;
////
////    public JsonFileWriter(ObjectMapper mapper){
////        this.mapper = mapper;
////    }
//
