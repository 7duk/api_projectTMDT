package com.example.demo.controller;

import com.example.demo.dto.item.ItemSaveDto;
import com.example.demo.dto.item.ItemUpdateDto;
import com.example.demo.service.ItemService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService service;
    @GetMapping("/{igId}")
    public ResponseEntity<?> getItemsByIgId(@PathVariable("igId") String igId,
                                            @RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                            @RequestParam(name = "size", required = false, defaultValue = "10")  Integer size){
        return service.getItemsByIgId(DataTypeUtils.ConvertStringToInt(igId),page,size);
    }
    @PostMapping("")
    public ResponseEntity<?> saveItem(@RequestBody ItemSaveDto itemSaveDto){
        return service.saveItem(itemSaveDto);
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<?> updateItem(@RequestBody ItemSaveDto itemSaveDto, @PathVariable("itemId") String itemId ){
        return service.updateItem(itemSaveDto, DataTypeUtils.ConvertStringToInt(itemId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") String itemId){
        return service.deleteItem(DataTypeUtils.ConvertStringToInt(itemId));
    }
    @PatchMapping("/discount/{id}")
    public ResponseEntity<?> updateDiscountItem(@PathVariable("id") String itemId ,@RequestBody ItemUpdateDto itemUpdateDto){
        return service.updateDiscountItem(DataTypeUtils.ConvertStringToInt(itemId),itemUpdateDto);
    }
    @PatchMapping("/name/{id}")
    public ResponseEntity<?> updateNameItem(@PathVariable("id") String itemId ,@RequestBody ItemUpdateDto itemUpdateDto){
        return service.updateNameItem(DataTypeUtils.ConvertStringToInt(itemId),itemUpdateDto);
    }
    @PatchMapping("/description/{id}")
    public ResponseEntity<?> updateDescriptionItem(@PathVariable("id") String itemId ,@RequestBody ItemUpdateDto itemUpdateDto){
        return service.updateDescriptionItem(DataTypeUtils.ConvertStringToInt(itemId),itemUpdateDto);
    }

    @GetMapping("/item-id/{name}")
    public ResponseEntity<?> getItemIdByName(@PathVariable("name") String name){
        return service.getItemIdByName(name);
    }

    @GetMapping("/item-search/{search}")
    public ResponseEntity<?> getItemsBySearch(@PathVariable("search") String search){
        return service.getItemsBySearch(search);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> getAllItemsBySearch(@PathVariable("search") String search){
        return service.getAllItemsBySearch(search);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") String id){
        return service.getItemById(DataTypeUtils.ConvertStringToInt(id));
    }

    @GetMapping("/item/discounts")
    public ResponseEntity<?> getItemByDiscounts(){
        return service.getItemByDiscounts();
    }

    @GetMapping("/item/new")
    public ResponseEntity<?> getItemNewByDay(){
        return service.getItemNewByDay();
    }

    @GetMapping("/item/discountsN9")
    public ResponseEntity<?> getItemByDiscountsNo9(){
        return service.getItemByDiscountsNo9();
    }

    @GetMapping("/item/discounts9")
    public ResponseEntity<?> getItemByDiscounts9(){
        return service.getItemByDiscounts9();
    }
}
