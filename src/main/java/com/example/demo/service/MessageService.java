package com.example.demo.service;

import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<?> saveMessages(List<MessageDto> messageDtos) {
        System.out.println(messageDtos.get(0));
        try {
            List<Message> messages = messageDtos.stream().map(e -> {
                Message mes =  Message.builder().time(LocalDateTime.parse(e.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
                mapper.map(e, mes);
                return mes;
            }).toList();
            repository.saveAll(messages);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( Response.builder().message("INSERT MESSAGES SUCCESS").build(), HttpStatus.OK);
    }

    public ResponseEntity<?> getMessageFromChat(Integer cId,Integer page,Integer size,String sort) {
        Integer offSet = (page-1)*size;
        List<Message> messages = repository.findAllByChatId(cId,size,offSet,sort);
        List<MessageDto> messageDtos = messages.stream().map(e -> {
            MessageDto messageDto = MessageDto.builder().chatId(e.getChatId()).senderId(e.getSenderId()).build();
            mapper.map(e, messageDto);
            return messageDto;
        }).toList();
        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
    }
}
