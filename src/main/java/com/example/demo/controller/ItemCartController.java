package com.example.demo.controller;

import com.example.demo.dto.itemcart.ItemCartSaveDto;
import com.example.demo.service.ImageItemService;
import com.example.demo.service.ItemCartService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itemcarts")
@RequiredArgsConstructor
public class ItemCartController {
    private final ItemCartService service;
    @GetMapping("/{userID}")
    public ResponseEntity<?> getItemsInCartByUser(@PathVariable("userID") String userID){
        return service.getItemsInCartByUser(DataTypeUtils.ConvertStringToInt(userID));
    }
    @PostMapping("")
    public ResponseEntity<?> saveItemInCart(@RequestBody ItemCartSaveDto itemCartSaveDto){
        return service.saveItemInCart(itemCartSaveDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeItemInCart(@PathVariable("id") String id){
        return service.removeItemInCart(DataTypeUtils.ConvertStringToInt(id));
    }
    @DeleteMapping("")
    public ResponseEntity<?> removeAllItemInCart(@RequestParam("userID")String userID){
        return service.removeAllItemInCart(DataTypeUtils.ConvertStringToInt(userID));
    }
}
