package com.example.parsers;

import com.example.exceptions.DeserializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.example.utils.LoggerUtil.LOGGER;

public class JSONParser {

    private final String jsonPath;
    private final ObjectMapper mapper = new ObjectMapper();

    public JSONParser(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public void parseJSON() throws DeserializationException {
        try (InputStream inputStream = new FileInputStream(jsonPath)) {
            Map<String, Object> companyData = mapper.readValue(inputStream, Map.class);

            if (companyData != null) {
                printNestedObjects(companyData);
            } else {
                throw new DeserializationException("Deserialization resulted in a null companyData");
            }
        } catch (IOException e) {
            LOGGER.error("Error parsing JSON file: ", e);
            throw new DeserializationException("Error parsing JSON file", e);
        }
    }

    public void printNestedObjects(Map<String, Object> data) throws DeserializationException {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            Object value = entry.getValue();
            try {
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
                LOGGER.info(json);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error printing JSON", e);
                throw new DeserializationException("Error printing JSON", e);
            }
        }
    }
}
