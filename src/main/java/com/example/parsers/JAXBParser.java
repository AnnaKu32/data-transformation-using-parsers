package com.example.parsers;

import com.example.exceptions.UnmarshallingException;
import com.example.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static com.example.utils.LoggerUtil.LOGGER;

public class JAXBParser {

    private String xmlPath;

    public JAXBParser(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public void parseXML() throws UnmarshallingException {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Company company = (Company) unmarshaller.unmarshal(new File(xmlPath));
            if (company != null) {
                printCompanyDetails(company);
            } else {
                throw new UnmarshallingException("Unmarshalling resulted in a null Company object");
            }
        } catch (JAXBException | UnmarshallingException e){
            LOGGER.error("Error during JAXB unmarshalling: ", e);
            throw new UnmarshallingException("Error during JAXB unmarshalling", e);
        }
    }

    private void printCompanyDetails(Company company) {
        for (Client client : company.getClients()) {
            LOGGER.info(client);
        }
        for (Department department : company.getDepartments()) {
            LOGGER.info(department);
        }
        for (Employee employee : company.getEmployees()) {
            LOGGER.info(employee);
        }
        for (Project project : company.getProjects()) {
            LOGGER.info(project);
        }
        for (Task task : company.getTasks()) {
            LOGGER.info(task);
        }
    }
}
