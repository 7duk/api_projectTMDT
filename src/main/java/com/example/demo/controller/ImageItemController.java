package com.example.demo.controller;

import com.example.demo.dto.imageitem.ImageSaveDto;
import com.example.demo.service.ImageItemService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imageitems")
@RequiredArgsConstructor
public class ImageItemController {
    private final ImageItemService service;
    @PostMapping("")
    public ResponseEntity<?> saveImageItems(@RequestBody List<ImageSaveDto> itemSaveDtos){
        return service.saveImageItems(itemSaveDtos);
    }
    @DeleteMapping("/image/{id}")
    public ResponseEntity<?> removeImageItem(@PathVariable("id") String id){
        return service.removeImageItemById(DataTypeUtils.ConvertStringToInt(id));
    }
    @DeleteMapping("/imageitem/{itemId}")
    public ResponseEntity<?> removeImageItemByItemId(@PathVariable("itemId") String itemId){
        return service.removeImageItemByItemId(DataTypeUtils.ConvertStringToInt(itemId));
    }
}
