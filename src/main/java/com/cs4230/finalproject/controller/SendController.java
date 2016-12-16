package com.cs4230.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jthomann on 12/14/16.
 */
@Controller
public class SendController {
    private SimpMessagingTemplate template;

    @Autowired
    public SendController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @SendTo("/tweets/stream")
    public void tweetStream(String jsonString) {
        this.template.convertAndSend("/tweets/stream", jsonString);
    }
}
