package com.mateuszosiak.jmsqueue.service;


import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class WriteToFileService {

    public void whenWriteStringUsingBufferedWritter(String fileName,String message)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(message);
        writer.close();
    }
}
