package com.example;

import com.example.exceptions.DeserializationException;
import com.example.exceptions.ParsingException;
import com.example.exceptions.UnmarshallingException;
import com.example.exceptions.ValidationException;
import com.example.parsers.JAXBParser;
import com.example.parsers.JSONParser;
import com.example.parsers.SAXParser;
import com.example.validators.XmlValidator;

import static com.example.utils.LoggerUtil.LOGGER;

public class Main {
    public static void main(String[] args){

        String xsdPath = "src/main/resources/company.xsd";
        String xmlPath = "src/main/resources/company.xml";
        String jsonPath = "src/main/resources/company.json";

        String xsdPathValidate = "company.xsd";
        String xmlPathValidate = "company.xml";

        XmlValidator validator = new XmlValidator(xsdPathValidate, xmlPathValidate);
        try{
            validator.validateXMLSchema();
        } catch (ValidationException e){
            LOGGER.error(e);
        }

        SAXParser saxParser = new SAXParser(xmlPath);
        try{
            saxParser.parseXmlWithHandler();
        } catch (ParsingException e){
            LOGGER.error(e);
        }

        JAXBParser jaxbParser = new JAXBParser(xmlPath);
        try{
            jaxbParser.parseXML();
        } catch (UnmarshallingException e){
            LOGGER.error(e);
        }

        JSONParser jsonParser = new JSONParser(jsonPath);
        try{
            jsonParser.parseJSON();
        } catch (DeserializationException e){
            LOGGER.error(e);
        }

    }
}