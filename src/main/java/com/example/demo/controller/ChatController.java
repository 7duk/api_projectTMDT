package com.example.demo.controller;

import com.example.demo.dto.chat.ChatDto;
import com.example.demo.dto.chat.ChatIdDto;
import com.example.demo.service.ChatService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService service;
    @PostMapping("/room-chat")
    public ResponseEntity<?> saveChat(@RequestBody @Validated ChatDto request){
        return service.saveChat(request);
    }
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<ChatDto> chats = service.findAll();
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
    @GetMapping("/room-chat/{userid1}/{userid2}")
    public ResponseEntity<?> getChatById(@PathVariable("userid1") String userid1,@PathVariable("userid2") String userid2) {
        ChatDto request =  ChatDto.builder().participantId1(DataTypeUtils.ConvertStringToInt(userid1))
                .participantId2(DataTypeUtils.ConvertStringToInt(userid2)).build();
        System.out.println(request.toString());
        Integer chatId = service.findChatByPARTICIPANT(request);
        return new ResponseEntity<>(ChatIdDto.builder().idChat(chatId).build(), HttpStatus.OK);
    }
}
