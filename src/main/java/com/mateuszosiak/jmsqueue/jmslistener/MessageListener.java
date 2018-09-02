package com.mateuszosiak.jmsqueue.jmslistener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateuszosiak.jmsqueue.model.Message;
import com.mateuszosiak.jmsqueue.model.repository.MessageRepository;
import com.mateuszosiak.jmsqueue.service.WriteToFileService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * This is the queue listener class, its receiveMessage() method ios invoked with the
 * message as the parameter.
 */

@Component
@AllArgsConstructor
public class MessageListener {

    private final MessageRepository messageRepository;
    private final WriteToFileService writeToFileService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = "message-queue", containerFactory = "jmsFactory")
    public void receiveMessage(Map<String,String> msg) throws IOException {
        System.out.println("Received message type: " +  msg);
        String messageContainer;
        ObjectMapper objectMapper = new ObjectMapper();
        Message message;

        if (msg.containsKey("save")){
            messageContainer = msg.get("save");
            objectMapper = new ObjectMapper();
            message = objectMapper.readValue(messageContainer, Message.class);

            messageRepository.save(message);
        }
        if (msg.containsKey("reject")){
            System.out.println("Message rejected");
            logger.debug("Message rejected");
            return;
        }
        if (msg.containsKey("saveToFile")){
            messageContainer = msg.get("saveToFile");
            objectMapper = new ObjectMapper();
            message = objectMapper.readValue(messageContainer, Message.class);

            writeToFileService.whenWriteStringUsingBufferedWritter("SaveToFile.txt",message.getMessage());
        }

        if (msg.containsKey("logToConsole")){
            messageContainer = msg.get("logToConsole");
            objectMapper = new ObjectMapper();
            message = objectMapper.readValue(messageContainer, Message.class);


            System.out.println("Message: " + message.getMessage());
            logger.info("Message: " +message.getMessage());
        }
    }
}
