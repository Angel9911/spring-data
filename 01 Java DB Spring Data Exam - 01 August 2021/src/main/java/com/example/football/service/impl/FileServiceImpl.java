package com.example.football.service.impl;


import com.example.football.service.FileService;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String readString(String fileName) throws IOException {
        return null;
    }

    @Override
    public <T> T readJsonFile(String filePath, Class<T> clazz) throws IOException {
        return null;
    }

    @Override
    public <T> void writeToJsonFile(String filePath, T record) throws IOException {

    }

    @Override
    public <T> T readXmlFile(String filePath, Class<T> clazz) throws IOException, JAXBException {
        return null;
    }

    @Override
    public <T> void writeToXmlFile(String filePath, T record) throws IOException, JAXBException {

    }
}
