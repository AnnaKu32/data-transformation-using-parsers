package com.example.validators;

import com.example.exceptions.ValidationException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URL;

import static com.example.utils.LoggerUtil.LOGGER;

public class XmlValidator {

    private final String xsdPath;
    private final String xmlPath;

    public XmlValidator(String xsdPath, String xmlPath) {
        this.xsdPath = xsdPath;
        this.xmlPath = xmlPath;
    }

    public void validateXMLSchema() throws ValidationException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            URL xsdUrl = XmlValidator.class.getClassLoader().getResource(xsdPath);
            URL xmlUrl = XmlValidator.class.getClassLoader().getResource(xmlPath);
            if (xsdUrl == null || xmlUrl == null) {
                LOGGER.error("xsdUrl or xmlUrl is null");
                throw new ValidationException("xsdUrl or xmlUrl is null");
            }

            Source xsdSource = new StreamSource(xsdUrl.openStream());
            Schema schema = factory.newSchema(xsdSource);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlUrl.openStream()));
            LOGGER.info("XML document is valid against the schema");
        } catch (SAXException | IOException e) {
            LOGGER.error("XML document is not valid against the schema", e);
            throw new ValidationException("XML document is not valid against the schema", e);
        }
    }
}
