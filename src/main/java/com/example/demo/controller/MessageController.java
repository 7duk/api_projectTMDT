package com.example.demo.controller;

import com.example.demo.dto.message.MessageDto;
import com.example.demo.service.MessageService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final MessageService service;
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageDto message) {
        System.out.println("mess:" + message);
        try {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/api/messages")
    public ResponseEntity<?> saveMessages(@RequestBody List<MessageDto> request){
        return service.saveMessages(request);
    }
    @GetMapping("/api/messages/{chat-id}")
    public ResponseEntity<?> getMessagesFromChat(@PathVariable( "chat-id") String cId,
                                                 @RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                                 @RequestParam(name = "size", required = false, defaultValue = "20")  Integer size,
                                                 @RequestParam(name = "sort", required = false, defaultValue = "DESC")  String sort){
        return  service.getMessageFromChat(DataTypeUtils.ConvertStringToInt(cId),page,size,sort);
    }
}

