package com.example.demo.controller;

import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.response.Response;
import com.example.demo.service.GifcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gifcodes")
public class GifcodeController {
    private final GifcodeService service;
    //admin
    @GetMapping("/random")
    public ResponseEntity<?> randomGifcodeForEventPrivate(@RequestParam("amount")Integer amount,@RequestParam("percent")Double percent){
        return new ResponseEntity<>(service.generateGifcode(amount,percent), HttpStatus.OK);
    }
    //user
    @GetMapping("/percent")
    public ResponseEntity<?> getPercentByCode(@RequestParam("gifcode") String gifCode){
        Double percent = service.getPercentByGifcode(gifCode);
        if(percent!=null){
            return new ResponseEntity<>(percent,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(Response.builder().message("GIFCODE UNEXIST").build(),HttpStatus.NOT_FOUND);
        }
    }
    //user
    @PutMapping("/usaged")
    public ResponseEntity<?> updateRemaingUsage(@RequestParam("gifcode") String gifCode){
        if(service.updateRemainingUsage(gifCode)==1){
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.builder().message("GIFCODE UNEXIST OR NO MORE").build(),HttpStatus.BAD_REQUEST);
    }

}
