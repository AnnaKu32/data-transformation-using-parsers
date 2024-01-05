package com.example.parsers;

import com.example.utils.LoggerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JSONParser {

    private final String jsonPath;
    private final ObjectMapper mapper = new ObjectMapper();

    public JSONParser(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public void parseJSON() {
        try (InputStream inputStream = new FileInputStream(jsonPath)) {
            Map<String, Object> companyData = mapper.readValue(inputStream, Map.class);
            printNestedObjects(companyData);
        } catch (IOException e) {
            LoggerUtil.log.error("Error parsing JSON file: " + e);
        }
    }

    public void printNestedObjects(Map<String, Object> data){
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            Object value = entry.getValue();
            try {
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
                LoggerUtil.log.info(json);
            } catch (JsonProcessingException e) {
                LoggerUtil.log.error("Error printing JSON", e);
            }
        }
    }
}
