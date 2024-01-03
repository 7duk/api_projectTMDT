package com.example.demo.controller;

import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.service.ItemDetailService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemdetails")
@RequiredArgsConstructor
public class ItemDetailController {
    private final ItemDetailService service;
    @PostMapping("")
    public ResponseEntity<?> saveItemDetails(@RequestBody List<ItemDetailDto> itemDetailDtos){
        return service.saveItemDetails(itemDetailDtos);
    }
    @PatchMapping("/itemdetail/{id}")
    public ResponseEntity<?> updateItemDetail(@PathVariable("id") String id ,@RequestBody ItemDetailDto itemDetailDto){
        return service.updateItemDetail(DataTypeUtils.ConvertStringToInt(id),itemDetailDto);
    }
}
