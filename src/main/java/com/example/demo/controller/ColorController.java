package com.example.demo.controller;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/colors")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService service;
    @PostMapping("")
    public ResponseEntity<?> saveColor(@RequestBody ColorDto dto){
        return service.saveColor(dto);
    }
    @GetMapping("/{code}")
    public ResponseEntity<?> getColorIdByCode(@PathVariable("code") String code){
        System.out.println("code:"+code);
        return service.getColorIdByCode(code);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllColor(){
        return service.getAllColor();
    }
}
