package com.example.demo.controller;

import com.example.demo.dto.event.EventSaveDto;
import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.response.Response;
import com.example.demo.service.EventService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    //admin
    @GetMapping("")
    public ResponseEntity<?> getAllEvents() {
        return new ResponseEntity<>(service.getAllEvents(), HttpStatus.OK);
    }

    //user
    @GetMapping("/filtered")
    public ResponseEntity<?> getAllEventsFiltered() {
        return new ResponseEntity<>(service.getAllExpiredEvent(), HttpStatus.OK);
    }

    //admin
    //tạo sự kiện giảm giá các sản phẩm theo itemgroupid
    @PostMapping("/event/decrease-price")
    public ResponseEntity<?> createEventDecreasePrice(@RequestBody EventSaveDto dto) {
        try {
            Integer result = service.createEventDecreasePrice(dto);
            if (result == 1) {
                return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.CREATED);
            } else if (result == 2) {
                return new ResponseEntity<>(Response.builder().message("INVALID DATETIME").build(), HttpStatus.BAD_REQUEST);
                //status code 400
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    //admin
    //tạo sự kiện phát code public cho khách hàng
    @PostMapping("/event/giveaway/code-public")
    public ResponseEntity<?> createEventGiveawayGifcodePublic(@RequestBody EventSaveDto dto) {
        try {
            Integer result = service.createEventWithGifcodePublic(dto);
            if (result == 1) {
                return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.CREATED);
            } else if (result == 2) {
                return new ResponseEntity<>(Response.builder().message("INVALID DATETIME").build(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    //admin
    //tạo sự kiện phát code private cho top khách hàng quen thuộc
    @PostMapping("/event/giveaway/code-private")
    public ResponseEntity<?> createEventGiveawayGifcodePrivate(@RequestBody EventSaveDto dto) throws MessagingException, UnsupportedEncodingException {
        try {
            Integer result = service.createEventWithGifcodePrivate(dto);
            if (result == 1) {
                return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.CREATED);
            } else if (result == 2) {
                return new ResponseEntity<>(Response.builder().message("INVALID DATETIME").build(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    //admin
    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleleEvents(@PathVariable("id") Integer id) {
        if (service.deleleEventById(id) == 1) {
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
    }

}
