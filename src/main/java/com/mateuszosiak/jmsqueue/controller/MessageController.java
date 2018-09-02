package com.mateuszosiak.jmsqueue.controller;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("request")
@AllArgsConstructor
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping(value = "/save")
    @ResponseBody
    public String save(@RequestBody String msg){
        System.out.println("Receiving new request");
        logger.debug("Receiving new request");

        Map<String, String> actionMap = new HashMap<>();
        actionMap.put("save",msg);

        jmsTemplate.convertAndSend("message-queue", actionMap);

        return "Message saved";
    }

    @PostMapping(value = "/file")
    @ResponseBody
    public String saveToFile(@RequestBody String msg){
        logger.debug("Receiving new request");

        Map<String, String> actionMap = new HashMap<>();
        actionMap.put("saveToFile",msg);

        jmsTemplate.convertAndSend("message-queue", actionMap);

        return "Message saved to file";
    }
    @PostMapping(value = "/reject")
    @ResponseBody
    public String reject(@RequestBody String msg){
        Map<String, String> actionMap = new HashMap<>();
        actionMap.put("reject",msg);

        jmsTemplate.convertAndSend("message-queue", actionMap);

        return "Message rejected";
    }

    @PostMapping(value = "/logToConsole")
    @ResponseBody
    public String log(@RequestBody String msg){
        Map<String, String> actionMap = new HashMap<>();
        actionMap.put("logToConsole",msg);

        jmsTemplate.convertAndSend("message-queue", actionMap);

        return "Message logged to console";
    }
}
